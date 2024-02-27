package com.nb.mallchat.common.common.aspect;

import cn.hutool.core.util.StrUtil;
import com.nb.mallchat.common.common.annotation.RedissionLock;
import com.nb.mallchat.common.common.service.LockService;
import com.nb.mallchat.common.common.utils.SpElUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 */
@Slf4j
@Component
@Aspect
@Order(0)//确保比事务注解先执行， 分布式锁在事务外
public class RedissonLockAspect {
	@Autowired
	private LockService lockService;

	// @Around("@annotation(com.nb.mallchat.common.common.annotation.RedissionLock)")
	public Object around(ProceedingJoinPoint joinPoint, RedissionLock redissionLock) throws Throwable {
		Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
		String prefix = StrUtil.isBlank(redissionLock.prefixkey()) ? SpElUtils.getMethodKey(method) : redissionLock.prefixkey();//默认方法限定名+注解排名（可能多个）
		String key = SpElUtils.parseSpEl(method, joinPoint.getArgs(), redissionLock.key());
		// Object o = lockService.executeWithLock(prefix + ":" + key, redissionLock.waitTime(), redissionLock.unit(), joinPoint::proceed);
		// return o;
		return lockService.executeWithLock(prefix + ":" + key, redissionLock.waitTime(), redissionLock.unit(), joinPoint::getArgs);
	}
}
