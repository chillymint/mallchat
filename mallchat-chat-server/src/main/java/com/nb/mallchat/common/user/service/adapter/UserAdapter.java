package com.nb.mallchat.common.user.service.adapter;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.nb.mallchat.common.user.domain.entity.ItemConfig;
import com.nb.mallchat.common.user.domain.entity.User;
import com.nb.mallchat.common.user.domain.entity.UserBackpack;
import com.nb.mallchat.common.user.domain.enums.YesOrNoEnum;
import com.nb.mallchat.common.user.domain.vo.resp.BadgeResp;
import com.nb.mallchat.common.user.domain.vo.resp.UserInfoResp;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    public static List<BadgeResp> buildBadgeResp(List<ItemConfig> itemConfigs, List<UserBackpack> backpacks, User user) {
        if(ObjectUtil.isNull(user)){
            // 校验入参为空 防止NPE
            return Collections.emptyList();
        }

        Set<Long> obtainItemSet = backpacks.stream()
                .map(UserBackpack::getItemId)
                .collect(Collectors.toSet());

        return itemConfigs.stream().map(a ->{
            BadgeResp resp = new BadgeResp();
            BeanUtil.copyProperties(a, resp);
            resp.setObtain(obtainItemSet.contains(a.getId()) ? YesOrNoEnum.YES.getStatus() : YesOrNoEnum.NO.getStatus());
            resp.setWearing(ObjectUtil.equal(a.getId(), user.getItemId()) ? YesOrNoEnum.YES.getStatus() : YesOrNoEnum.NO.getStatus());
            return resp;
                }).sorted(Comparator.comparing(BadgeResp::getWearing, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }
}
