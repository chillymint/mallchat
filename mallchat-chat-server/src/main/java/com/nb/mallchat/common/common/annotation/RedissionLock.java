package com.nb.mallchat.common.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 */
@Retention(RetentionPolicy.RUNTIME)//运行时生效
@Target(ElementType.METHOD)//作用在方法上
public @interface RedissionLock {

	/**
	 * key的前缀 ，默认取方法全限定名，可以自己指定
	 */
	String prefixkey() default "";

	/**
	 * 支持springEL表达式的key
	 */
	String key();

	/**
	 * 等待锁的排队时间，默认快速失败
	 */
	int waitTime() default -1;

	/**
	 *  时间单位默认毫秒
	 * @return
	 */
	TimeUnit unit() default TimeUnit.MILLISECONDS;
}
