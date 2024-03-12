package com.nb.mallchat.common.common.config;

import com.nb.mallchat.common.common.utils.DFAFilter;
import com.nb.mallchat.common.common.utils.MyWordFactory;
import com.nb.mallchat.common.common.utils.SensitiveWordBs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 */
@Configuration
public class SensitiveWordConfig {

	@Autowired
	private MyWordFactory myWordFactory;

	/**
	 * 初始化引导类
	 *
	 * @return 初始化引导类
	 * @since 1.0.0
	 */
	@Bean
	public SensitiveWordBs sensitiveWordBs() {
		return SensitiveWordBs.newInstance()
				.filterStrategy(DFAFilter.getInstance())
				.sensitiveWord(myWordFactory)
				.init();
	}

}
