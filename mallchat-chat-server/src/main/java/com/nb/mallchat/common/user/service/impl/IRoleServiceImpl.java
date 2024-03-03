package com.nb.mallchat.common.user.service.impl;

import com.nb.mallchat.common.user.domain.enums.RoleEnum;
import com.nb.mallchat.common.user.service.IRoleService;
import com.nb.mallchat.common.user.service.cache.UserCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class IRoleServiceImpl implements IRoleService {
    @Autowired
    private UserCache userCache;
    @Override
    public boolean hasPower(Long uid, RoleEnum roleEnum) {
        Set<Long> roleSet = userCache.getRoleSet(uid);

        return roleSet.contains(roleEnum.getId());
    }

    private boolean isAdmin(Set<Long> roleSet){
        return roleSet.contains(RoleEnum.ADMIN.getId());
    }


    @Override
    public void refreshIpDetailAsync(Long id) {

    }
}
