package com.nb.mallchat.common.common.exception;

import com.nb.mallchat.common.common.domain.vo.resp.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ApiResult<?> MethodArgumentNotValidException(MethodArgumentNotValidException e){
		System.out.println();
		StringBuilder errMsg = new StringBuilder();
		e.getBindingResult().getFieldErrors().forEach(
				x-> errMsg.append(x.getField()).append(x.getDefaultMessage()).append(","));
		String message = errMsg.toString();
		return ApiResult.fail(CommonErrorEnum.PARAM_INVALID.getCode(), message.substring(0, message.length() - 1));
	}

	/**
	 * 业务异常
	 */
	@ExceptionHandler(value = BusinessException.class)
	public ApiResult<?> businessException(BusinessException e){
		log.info("business exception! The reason is:{}", e.getMessage());
		return ApiResult.fail(e.getErrorCode(), e.getErrorMsg());
	}



	/**
	 * 最后一道防线
	 * @param e
	 * @return
	 */
	// @ExceptionHandler(value = BusinessException.class)
	// public ApiResult<?> businessException(BusinessException e){
	// 	log.error("businessException exception! the reason is：{}", e.getMessage());
	// 	return ApiResult.fail(CommonErrorEnum.SYSTEM_ERROR);
	// }


}
