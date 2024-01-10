package com.nb.mallchat.common.user.dao;

import com.nb.mallchat.common.user.domain.entity.UserBackpack;
import com.nb.mallchat.common.user.mapper.UserBackpackMapper;
import com.nb.mallchat.common.user.service.IUserBackpackService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户背包表 服务实现类
 * </p>
 *
 * @author <a href="https://github.com/zongzibinbin">cc</a>
 * @since 2024-01-10
 */
@Service
public class UserBackpackDao extends ServiceImpl<UserBackpackMapper, UserBackpack> implements IUserBackpackService {

}
