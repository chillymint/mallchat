package com.nb.mallchat.common.user.controller;


import com.nb.mallchat.common.common.domain.dto.RequestInfo;
import com.nb.mallchat.common.common.domain.vo.resp.ApiResult;
import com.nb.mallchat.common.common.utils.AssertUtil;
import com.nb.mallchat.common.common.utils.RequestHolder;
import com.nb.mallchat.common.user.domain.enums.RoleEnum;
import com.nb.mallchat.common.user.domain.vo.req.BlackReq;
import com.nb.mallchat.common.user.domain.vo.req.ModifyNameReq;
import com.nb.mallchat.common.user.domain.vo.req.WearingBadgeReq;
import com.nb.mallchat.common.user.domain.vo.resp.BadgeResp;
import com.nb.mallchat.common.user.domain.vo.resp.UserInfoResp;
import com.nb.mallchat.common.user.service.IRoleService;
import com.nb.mallchat.common.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

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
	@Autowired
	IRoleService iRoleService;

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

	@GetMapping("/badges")
	@ApiOperation("可选徽章预览")
	@Cacheable
	public ApiResult<List<BadgeResp>> badges(){

		return ApiResult.success(userService.badges(RequestHolder.get().getUid()));
	}

	@PutMapping("/badge")
	@ApiOperation("佩戴徽章预览")
	public ApiResult<Void> wearingBadge(@Valid @RequestBody WearingBadgeReq req){
		userService.wearingBadge(RequestHolder.get().getUid(), req.getBadgeId());
		return ApiResult.success();
	}

	@PutMapping("/black")
	@ApiOperation("拉黑用户")
	public ApiResult<Void> black(@Valid @RequestBody BlackReq req){
		Long uid = RequestHolder.get().getUid();
		boolean hasPower = iRoleService.hasPower(uid, RoleEnum.ADMIN);
		AssertUtil.isTrue(hasPower, "无权限");
		userService.black(req);
		return ApiResult.success();
	}
}

