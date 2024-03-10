package com.nb.mallchat.common.user.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nb.mallchat.common.user.domain.entity.Role;
import com.nb.mallchat.common.user.mapper.RoleMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 */
@Service
public class RoleDao extends ServiceImpl<RoleMapper, Role> {

}
