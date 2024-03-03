package com.nb.mallchat.common.user.service;

import com.nb.mallchat.common.user.domain.entity.Role;
import com.nb.mallchat.common.user.domain.enums.RoleEnum;

public interface IRoleService extends IpService<Role>{
    /**
     *  是否拥有某个权限
     */
    boolean hasPower(Long uid, RoleEnum roleEnum);
}
