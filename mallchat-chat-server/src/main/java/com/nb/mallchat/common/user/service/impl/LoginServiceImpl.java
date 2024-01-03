package com.nb.mallchat.common.user.service.impl;

import com.nb.mallchat.common.common.constant.RedisKey;
import com.nb.mallchat.common.common.utils.JwtUtils;
import com.nb.mallchat.common.common.utils.RedisUtils;
import com.nb.mallchat.common.user.LoginService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


import java.util.Objects;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 */
@Service
public class LoginServiceImpl implements LoginService {
    public static final long TOKEN_EXPIRE_DAYS = 3;
    @Autowired
    private JwtUtils jwtUtils;
    ThreadPoolExecutor executor;

    @Override
    public boolean verify(String token) {
        return false;
    }

    @Override
    @Async
    public void renewalTokenIfNecessary(String token) {
        //异步刷新token
        // executor.execute(() -> {
        //     Long uid = getValidUid(token);
        //     String userTokenKey = getUserTokenKey(uid);
        //     Long expireDays = RedisUtils.getExpire(userTokenKey, TimeUnit.DAYS);
        //     if(expireDays == -2){
        //         return;
        //     }
        //     if(expireDays < 1){
        //         RedisUtils.expire(getUserTokenKey(uid), TOKEN_EXPIRE_DAYS, TimeUnit.DAYS);
        //
        //     }
        // });
        Long uid = getValidUid(token);
        String userTokenKey = getUserTokenKey(uid);
        Long expireDays = RedisUtils.getExpire(userTokenKey, TimeUnit.DAYS);
        if(expireDays == -2){
            return;
        }
        if(expireDays < 1){
            RedisUtils.expire(getUserTokenKey(uid), TOKEN_EXPIRE_DAYS, TimeUnit.DAYS);

        }




    }

    @Override
    public String login(Long uid) {
        String token = jwtUtils.createToken(uid);
        //将token存入redis中
        RedisUtils.set(getUserTokenKey(uid), token, TOKEN_EXPIRE_DAYS, TimeUnit.DAYS);

        return token;
    }

    @Override
    public Long getValidUid(String token) {
        String s = "";
        Long uid = jwtUtils.getUidOrNull(token);
        if(Objects.isNull(uid)){
            return null;
        }
        String oldToken = RedisUtils.get(getUserTokenKey(uid));
        if(StringUtils.isBlank(oldToken)){
            return null;
        }
        return Objects.equals(oldToken, token) ? uid : null;
    }

    private String getUserTokenKey(Long uid){
        return RedisKey.getKey(RedisKey.USER_TOKEN_STRING, uid);
    }
}
