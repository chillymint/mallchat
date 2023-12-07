package com.nb.mallchat.common.user.service.adapter;

import com.nb.mallchat.common.user.domain.entity.User;

/**
 * @author yunfu.ye
 * @date 2023/12/6 20:03
 */
public class UserAdapter {
    public static User buildUserSave(String openId) {
        return User.builder().openId(openId).build();
    }
}
