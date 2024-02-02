package com.nb.mallchat.common.user.service.impl;

import com.nb.mallchat.common.common.exception.BusinessException;
import com.nb.mallchat.common.common.utils.AssertUtil;
import com.nb.mallchat.common.user.dao.ItemConfigDao;
import com.nb.mallchat.common.user.dao.UserBackpackDao;
import com.nb.mallchat.common.user.dao.UserDao;
import com.nb.mallchat.common.user.domain.entity.ItemConfig;
import com.nb.mallchat.common.user.domain.entity.User;
import com.nb.mallchat.common.user.domain.entity.UserBackpack;
import com.nb.mallchat.common.user.domain.enums.ItemEnum;
import com.nb.mallchat.common.user.domain.vo.req.ModifyNameReq;
import com.nb.mallchat.common.user.domain.vo.resp.UserInfoResp;
import com.nb.mallchat.common.user.service.UserService;
import com.nb.mallchat.common.user.service.adapter.UserAdapter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.lang.model.element.Name;
import java.util.Objects;

/**
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserBackpackDao userBackpackDao;
    @Autowired
    private ItemCache itemCache;
    @Autowired
    private ItemConfigDao itemConfigDao;

    @Override
    @Transactional
    public Long register(User insert) {
        userDao.save(insert);
        //todo 用户注册
        return insert.getId();
    }

    @Override
    public UserInfoResp getUserInfo(Long uid) {
        User user = userDao.getById(uid);
        Integer modifyNameCount = userBackpackDao.getCountByValidItemId(uid, ItemEnum.MODIFY_NAME_CARD.getId());
        return UserAdapter.buildUserInfo(user, modifyNameCount);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modifyName(Long uid, String name) {
        User oldUser = userDao.getByName(name);
        AssertUtil.isEmpty(oldUser, "名称重复请更换！");
        UserBackpack modifyNameItem = userBackpackDao.getFirstValidItem(uid, ItemEnum.MODIFY_NAME_CARD.getId());
        AssertUtil.isNotEmpty(modifyNameItem, "改名卡不够了,请等待后续活动赠送!");
        // 使用改名卡
        boolean success = userBackpackDao.useItem(modifyNameItem);
        if (success) {
            // 改名
            userDao.modifyName(uid, name);
        }
    }
}
