package com.nb.mallchat.common.common.exception;

import io.swagger.models.auth.In;
import lombok.Data;

/**
 */
@Data
public class BusinessException extends RuntimeException{
	protected Integer errorCode;

	protected String errorMsg;

	public BusinessException(String errorMsg){
		super(errorMsg);
		this.errorCode = CommonErrorEnum.BUSINESS_ERROR.getCode();
		this.errorMsg = errorMsg;
	}

	public BusinessException(Integer errorCode, String errorMsg){
		super(errorMsg);
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}
}
