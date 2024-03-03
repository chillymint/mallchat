package com.nb.mallchat.common.user.dao;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nb.mallchat.common.user.domain.entity.UserRole;
import com.nb.mallchat.common.user.mapper.UserRoleMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleDao extends ServiceImpl <UserRoleMapper, UserRole>{
    public List<UserRole> listByUid(Long uid) {
        return lambdaQuery()
                .eq(UserRole::getUid, uid)
                .list();
    }
}
