package com.nb.mallchat.common.common.intercepter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 */

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
	@Autowired
	TokenInterceptor tokenInterceptor;
	@Autowired
	CollectorInterceptor collectorInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(tokenInterceptor)
				.addPathPatterns("/capi/**");
		registry.addInterceptor(collectorInterceptor)
				.addPathPatterns("/capi/**");

	}
}
