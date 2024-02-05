package com.nb.mallchat.common.user.service;

import com.nb.mallchat.common.user.domain.entity.UserBackpack;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nb.mallchat.common.user.domain.enums.IdempotentEnum;

/**
 * <p>
 * 用户背包表 服务类
 * </p>   extends IService<UserBackpack>
 */
public interface IUserBackpackService  {
	/**
	 *
	 * @param uid              用户id
	 * @param itemId           物品id
	 * @param idempotentEnum   幂等类型
	 * @param businessId       幂等唯一标识
	 */

	void acquireItem(Long uid, Long itemId, IdempotentEnum idempotentEnum, String businessId);

}
