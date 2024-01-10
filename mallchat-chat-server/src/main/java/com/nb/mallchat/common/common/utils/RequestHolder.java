package com.nb.mallchat.common.common.utils;

import com.nb.mallchat.common.common.domain.dto.RequestInfo;

/**
 */
public class RequestHolder {
	private static final ThreadLocal<RequestInfo> threadLocal = new ThreadLocal<>();
	public static void set(RequestInfo info){
		threadLocal.set(info);
	}

	public static RequestInfo get(){
		return threadLocal.get();
	}

	public static void remove(){
		threadLocal.remove();
	}

}
