package com.nb.mallchat.common.user.service.impl;

import com.nb.mallchat.common.common.annotation.RedissionLock;
import com.nb.mallchat.common.common.event.ItemReceiveEvent;
import com.nb.mallchat.common.common.service.LockService;
import com.nb.mallchat.common.common.utils.AssertUtil;
import com.nb.mallchat.common.user.dao.UserBackpackDao;
import com.nb.mallchat.common.user.domain.entity.ItemConfig;
import com.nb.mallchat.common.user.domain.entity.UserBackpack;
import com.nb.mallchat.common.user.domain.enums.IdempotentEnum;
import com.nb.mallchat.common.user.domain.enums.ItemTypeEnum;
import com.nb.mallchat.common.user.domain.enums.YesOrNoEnum;
import com.nb.mallchat.common.user.service.IUserBackpackService;
import com.nb.mallchat.common.user.service.cache.ItemCache;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
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
	@Autowired
	@Lazy
	private UserBackpackServiceImpl userBackpackService;
	@Autowired
	private ItemCache itemCache;
	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;
	@Override
	@Cacheable(key = "#itemId")
	public void acquireItem(Long uid, Long itemId, IdempotentEnum idempotentEnum, String businessId) {
		//组装幂等号
		String idempotent = getIdempotent(itemId, idempotentEnum, businessId);
		userBackpackService.doAcquireItem(uid, itemId, idempotent);
	}
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


	private String getIdempotent(Long itemId, IdempotentEnum idempotentEnum, String businessId) {
		return String.format("%d_%d_%s", itemId, idempotentEnum.getType(), businessId);
	}
	@RedissionLock(key = "#idempotent", waitTime = 5000)//相同幂等如果同时发奖，需要排队等上一个执行完，取出之前数据返回
	public void doAcquireItem(Long uid, Long itemId, String idempotent) {
		UserBackpack userBackpack = userBackpackdao.getById(idempotent);
		//幂等检查
		if (Objects.nonNull(userBackpack)) {
			return;
		}
		//业务检查
		ItemConfig itemConfig = itemCache.getById(itemId);
		if (ItemTypeEnum.BADGE.getType().equals(itemConfig.getType())) {//徽章类型做唯一性检查
			Integer countByValidItemId = userBackpackdao.getCountByValidItemId(uid, itemId);
			if (countByValidItemId > 0) {//已经有徽章了不发
				return;
			}
		}
		//发物品
		UserBackpack insert = UserBackpack.builder()
				.uid(uid)
				.itemId(itemId)
				.status(YesOrNoEnum.NO.getStatus())
				.idempotent(idempotent)
				.build();
		userBackpackdao.save(insert);
		//用户收到物品的事件
		applicationEventPublisher.publishEvent(new ItemReceiveEvent(this, insert));
	}

}
