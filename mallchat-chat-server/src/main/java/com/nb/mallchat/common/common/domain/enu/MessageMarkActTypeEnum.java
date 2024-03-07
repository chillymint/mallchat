package com.nb.mallchat.common.common.domain.enu;

/**
 * @author yunfu.ye
 * @date 2024/3/7 15:16
 */

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Description: 消息标记动作类型
 */
@AllArgsConstructor
@Getter
public enum MessageMarkActTypeEnum {
	MARK(1, "确认标记"),
	UN_MARK(2, "取消标记"),
	;

	private final Integer type;
	private final String desc;

	private static Map<Integer, MessageMarkActTypeEnum> cache;

	static {
		cache = Arrays.stream(MessageMarkActTypeEnum.values()).collect(Collectors.toMap(MessageMarkActTypeEnum::getType, Function.identity()));
	}

	public static MessageMarkActTypeEnum of(Integer type) {
		return cache.get(type);
	}
}
