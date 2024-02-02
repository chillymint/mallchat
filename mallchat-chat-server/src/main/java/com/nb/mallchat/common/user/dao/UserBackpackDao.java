package com.nb.mallchat.common.user.dao;

import com.nb.mallchat.common.user.domain.entity.UserBackpack;
import com.nb.mallchat.common.user.domain.enums.YesOrNoEnum;
import com.nb.mallchat.common.user.mapper.UserBackpackMapper;
import com.nb.mallchat.common.user.service.IUserBackpackService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户背包表 服务实现类
 * </p>
 *   implements IUserBackpackService
 */
@Service
public class UserBackpackDao extends ServiceImpl<UserBackpackMapper, UserBackpack>  {

	public Integer getCountByValidItemId(Long uid, Long itemId) {
		return lambdaQuery()
				.eq(UserBackpack::getUid, uid)
				.eq(UserBackpack::getItemId, itemId)
				.eq(UserBackpack::getStatus, YesOrNoEnum.NO.getStatus())
				.count();
	}

	public UserBackpack getFirstValidItem(Long uid, Long itemId) {
		// 当查询结果为1条时，limit1查询到结果后不会再向下扫描来提升效率
		UserBackpack one = lambdaQuery()
				.eq(UserBackpack::getUid, uid)
				.eq(UserBackpack::getItemId, itemId)
				.eq(UserBackpack::getStatus, YesOrNoEnum.NO.getStatus())
				.orderByAsc(UserBackpack::getId)
				.last("limit 1")
				.one();
		return one;
	}

	public boolean useItem(UserBackpack item) {
		return lambdaUpdate()
				.eq(UserBackpack::getId, item.getId())
				.eq(UserBackpack::getStatus, YesOrNoEnum.NO.getStatus())
				.set(UserBackpack::getStatus, YesOrNoEnum.YES.getStatus())
				.update();

	}


	public List<UserBackpack> getByItemIds(Long uid, List<Long> itemId) {
		return lambdaQuery()
				.eq(UserBackpack::getUid, uid)
				.eq(UserBackpack::getStatus, YesOrNoEnum.NO.getStatus())
				.in(UserBackpack::getItemId, itemId)
				.list();
	}
}
