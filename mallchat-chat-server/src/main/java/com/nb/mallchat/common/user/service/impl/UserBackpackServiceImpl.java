package com.nb.mallchat.common.user.service.impl;

import com.nb.mallchat.common.user.domain.enums.IdempotentEnum;
import com.nb.mallchat.common.user.service.IUserBackpackService;
import org.springframework.stereotype.Service;



/**
 */
@Service
public class UserBackpackServiceImpl implements IUserBackpackService {
	@Override
	public void acquireItem(Long uid, Long itemId, IdempotentEnum idempotentEnum, String businessId) {
		getIdempotent(itemId, idempotentEnum, businessId);
	}

	private void getIdempotent(Long itemId, IdempotentEnum idempotentEnum, String businessId) {
	}

}
