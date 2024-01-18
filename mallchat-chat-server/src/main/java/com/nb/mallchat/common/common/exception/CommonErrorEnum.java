package com.nb.mallchat.common.common.exception;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**

 */
@AllArgsConstructor
@Getter
public enum CommonErrorEnum  implements ErrorEnum{
	BUSINESS_ERROR(0, "{}	"),
	SYSTEM_ERROR(-1, "请重试"),
	PARAM_INVALID(-2, "参数校验失败");
	private final Integer code;
	private final String msg;

	@Override
	public Integer getErrorCode() {
		return null;
	}

	@Override
	public String getErrorMsg() {
		return null;
	}
}
