package com.nb.mallchat.common.user.dao;

import com.nb.mallchat.common.user.domain.entity.User;
import com.nb.mallchat.common.user.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author <a href="https://github.com/zongzibinbin">cc</a>
 * @since 2023-11-13
 */
@Service
public class UserDao extends ServiceImpl<UserMapper, User>  {

    public User getByOpenId(String openId) {
        return lambdaQuery()
                .eq(User::getOpenId, openId)
                .one();
    }
}
