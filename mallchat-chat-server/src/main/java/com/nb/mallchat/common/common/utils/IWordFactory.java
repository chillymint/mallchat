package com.nb.mallchat.common.common.utils;

import java.util.List;

/**
 * 敏感词
 *
 */
public interface IWordFactory {
	/**
	 * 返回敏感词数据源
	 *
	 * @return 结果
	 * @since 0.0.13
	 */
	List<String> getWordList();
}
