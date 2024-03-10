package com.nb.mallchat.common.common.transaction.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SecureInvokeDTO {
	private String className;
	private String methodName;
	private String parameterTypes;
	private String args;
}
