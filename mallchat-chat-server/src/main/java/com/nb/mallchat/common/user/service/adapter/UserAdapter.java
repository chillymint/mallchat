package com.nb.mallchat.common.user.service.adapter;

import cn.hutool.core.bean.BeanUtil;
import com.nb.mallchat.common.user.domain.entity.User;
import com.nb.mallchat.common.user.domain.vo.resp.UserInfoResp;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;

/**
 * @date 2023/12/6 20:03
 */
public class UserAdapter {
    public static User buildUserSave(String openId) {
        return User.builder().openId(openId).build();
    }

    public static User buildAuthorizeUser(Long uid, WxOAuth2UserInfo userInfo) {
        User user = new User();
        user.setId(uid);
        user.setName(userInfo.getNickname());
        user.setAvatar(userInfo.getHeadImgUrl());
        return user;
    }

	public static UserInfoResp buildUserInfo(User user, Integer modifyNameCount) {
        UserInfoResp result = new UserInfoResp();
        BeanUtil.copyProperties(user, result);
        result.setModifyNameChance(modifyNameCount);
        return result;
	}
}
