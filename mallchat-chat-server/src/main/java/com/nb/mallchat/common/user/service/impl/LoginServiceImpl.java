package com.nb.mallchat.common.user.service.impl;

import com.nb.mallchat.common.common.utils.JwtUtils;
import com.nb.mallchat.common.user.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public boolean verify(String token) {
        return false;
    }

    @Override
    public void renewalTokenIfNecessary(String token) {

    }

    @Override
    public String login(Long uid) {
        String token = jwtUtils.createToken(uid);

        return token;
    }

    @Override
    public Long getValidUid(String token) {
        return null;
    }
}
