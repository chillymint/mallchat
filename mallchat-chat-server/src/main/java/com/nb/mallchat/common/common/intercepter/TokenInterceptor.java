package com.nb.mallchat.common.common.intercepter;

import cn.hutool.http.ContentType;
import com.google.common.base.Charsets;
import com.nb.mallchat.common.common.domain.vo.resp.ApiResult;
import com.nb.mallchat.common.user.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.Optional;

/**
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {
	public static final String AUTHORIZATION = "Authorization";
	public static final String AUTHORIZATION_SCHEMA = "Bearer";
	public static final String UID = "UID";
	@Autowired
	private LoginService loginService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String token = getToken(request);
		Long validUid = loginService.getValidUid(token);
		if(Objects.nonNull(validUid)){
			request.setAttribute("uid", validUid);
		}else {//用户未登录
			String requestURI = request.getRequestURI();
			String[] split = requestURI.split("/");
			boolean isPublicURI = split.length>3 && "public".equals(split[3]);
			if(!isPublicURI){
				//401
				response.setStatus(401);
				response.setContentType(ContentType.JSON.toString(Charsets.UTF_8));
				response.getWriter().write("");
				return false;
			}
		}
		return true;
	}

	private String getToken(HttpServletRequest request) {
		String header = request.getHeader(AUTHORIZATION);
		return Optional.ofNullable(header)
				.filter(h -> h.startsWith(AUTHORIZATION_SCHEMA))
				.map(h -> h.replaceFirst(AUTHORIZATION_SCHEMA, ""))
				.orElse(null);

	}
}
