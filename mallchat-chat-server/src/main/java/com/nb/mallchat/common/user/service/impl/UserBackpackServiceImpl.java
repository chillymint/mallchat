package com.nb.mallchat.common.user.service.impl;

import com.nb.mallchat.common.common.service.LockService;
import com.nb.mallchat.common.common.utils.AssertUtil;
import com.nb.mallchat.common.user.dao.UserBackpackDao;
import com.nb.mallchat.common.user.domain.entity.UserBackpack;
import com.nb.mallchat.common.user.domain.enums.IdempotentEnum;
import com.nb.mallchat.common.user.domain.enums.YesOrNoEnum;
import com.nb.mallchat.common.user.service.IUserBackpackService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;


/**
 */
@Service
public class UserBackpackServiceImpl implements IUserBackpackService {
	@Autowired
	private LockService lockService;
	@Autowired
	private RedissonClient redissonClient;
	@Autowired
	private UserBackpackDao userBackpackdao;
	@Override
	@Cacheable(key = "#itemId")
	public void acquireItem(Long uid, Long itemId, IdempotentEnum idempotentEnum, String businessId) {
		String idempotent = getIdempotent(itemId, idempotentEnum, businessId);
		lockService.executeWithLock("acquireItem" + idempotent, ()->{
			UserBackpack userBackpack = userBackpackdao.getByIdempotent(idempotent);
			if(Objects.nonNull(userBackpack)){
					return;
				}
				// 发放物品
				UserBackpack insert = UserBackpack.builder()
						.uid(uid)
						.itemId(itemId)
						.status(YesOrNoEnum.NO.getStatus())
						.idempotent(idempotent)
						.build();
				userBackpackdao.save(insert);
		});
		// RLock lock = redissonClient.getLock("acquireItem" + idempotent);
		// boolean b = lock.tryLock();
		// lock.lock(1000, TimeUnit.MILLISECONDS);
		// AssertUtil.isTrue(b, "请求太频繁了");
		// try {
		// 	UserBackpack userBackpack = userBackpackdao.getByIdempotent(idempotent);
		// 	if(Objects.nonNull(userBackpack)){
		// 		return;
		// 	}
		// 	// 发放物品
		// 	UserBackpack insert = UserBackpack.builder()
		// 			.uid(uid)
		// 			.itemId(itemId)
		// 			.status(YesOrNoEnum.NO.getStatus())
		// 			.idempotent(idempotent)
		// 			.build();
		// 	userBackpackdao.save(insert);
		//
		//
		// } finally {
		// 	lock.unlock();
		// }

	}

	private String getIdempotent(Long itemId, IdempotentEnum idempotentEnum, String businessId) {
		return String.format("%d_%d_%s", itemId, idempotentEnum.getType(), businessId);
	}

}
