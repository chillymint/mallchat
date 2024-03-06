package com.nb.mallchat.common.user.dao;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.nb.mallchat.common.common.domain.vo.req.CursorPageBaseReq;
import com.nb.mallchat.common.common.domain.vo.resp.CursorPageBaseResp;
import com.nb.mallchat.common.common.utils.CursorUtils;
import com.nb.mallchat.common.user.domain.entity.User;
import com.nb.mallchat.common.user.domain.enums.ChatActiveStatusEnum;
import com.nb.mallchat.common.user.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nb.mallchat.common.user.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 */
@Service
public class UserDao extends ServiceImpl<UserMapper, User>   {

    public User getByOpenId(String openId) {
        return lambdaQuery()
                .eq(User::getOpenId, openId)
                .one();
    }

    public User getByName(String name){
        return new User();
    }


    public boolean modifyName(Long uid, String name) {
        return lambdaUpdate()
                .eq(User::getId, uid)
                .set(User::getName, name)
                .update();
    }

    public void wearingBadge(Long uid, Long itemId) {
        lambdaUpdate()
                .eq(User::getId, uid)
                .set(User::getItemId, itemId)
                .update();
    }

    public List<User> getFriendList(List<Long> uids) {
        return lambdaQuery()
                .in(User::getId, uids)
                .select(User::getId, User::getActiveStatus, User::getName, User::getAvatar)
                .list();

    }

    public Integer getOnlineCount() {
        return getOnlineCount(null);
    }

    public Integer getOnlineCount(List<Long> memberUidList) {
        return lambdaQuery()
                .eq(User::getActiveStatus, ChatActiveStatusEnum.ONLINE.getStatus())
                .in(CollectionUtil.isNotEmpty(memberUidList), User::getId, memberUidList)
                .count();
    }

    public CursorPageBaseResp<User> getCursorPage(List<Long> memberUidList, CursorPageBaseReq request, ChatActiveStatusEnum online) {
        return CursorUtils.getCursorPageByMysql(this, request, wrapper -> {
            wrapper.eq(User::getActiveStatus, online.getStatus());//筛选上线或者离线的
            wrapper.in(CollectionUtils.isNotEmpty(memberUidList), User::getId, memberUidList);//普通群对uid列表做限制
        }, User::getLastOptTime);
    }
}
