package com.nb.mallchat.common.user.controller;


import com.nb.mallchat.common.common.domain.dto.RequestInfo;
import com.nb.mallchat.common.common.domain.vo.resp.ApiResult;
import com.nb.mallchat.common.common.intercepter.TokenInterceptor;
import com.nb.mallchat.common.common.utils.RequestHolder;
import com.nb.mallchat.common.user.domain.vo.req.ModifyNameReq;
import com.nb.mallchat.common.user.domain.vo.resp.UserInfoResp;
import com.nb.mallchat.common.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 *
 */
@RestController
@RequestMapping("/capi/user")
@Api(value = "用户相关接口")
public class UserController {
	@Autowired
	UserService userService;

	@GetMapping("/public/userInfo")
	@ApiOperation("获取用户个人信息")
	public ApiResult<UserInfoResp> getUserInfo(HttpServletRequest request){
		// System.out.println(request.getAttribute(TokenInterceptor.UID));
		RequestInfo requestInfo = RequestHolder.get();

		return ApiResult.success(userService.getUserInfo(RequestHolder.get().getUid()));
	}

	@GetMapping("/name")
	@ApiOperation("修改用户名")
	public ApiResult<Void> modifyName(@Valid @RequestBody ModifyNameReq req){
		userService.modifyName(RequestHolder.get().getUid(), req.getName());
		return ApiResult.success();
	}

}

