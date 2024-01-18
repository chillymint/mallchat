package com.nb.mallchat.common.user.dao;

import com.nb.mallchat.common.user.domain.entity.UserBackpack;
import com.nb.mallchat.common.user.domain.enums.YesOrNoEnum;
import com.nb.mallchat.common.user.mapper.UserBackpackMapper;
import com.nb.mallchat.common.user.service.IUserBackpackService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户背包表 服务实现类
 * </p>
 *
 */
@Service
public class UserBackpackDao extends ServiceImpl<UserBackpackMapper, UserBackpack> implements IUserBackpackService {

	public Integer getCountByValidItemId(Long uid, Long itemId) {
		return lambdaQuery()
				.eq(UserBackpack::getUid, uid)
				.eq(UserBackpack::getItemId, itemId)
				.eq(UserBackpack::getStatus, YesOrNoEnum.NO.getStatus())
				.count();
	}
}
