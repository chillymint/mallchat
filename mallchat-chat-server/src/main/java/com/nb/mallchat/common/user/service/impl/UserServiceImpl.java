package com.nb.mallchat.common.user.service.impl;

import com.nb.mallchat.common.common.exception.BusinessException;
import com.nb.mallchat.common.user.dao.UserBackpackDao;
import com.nb.mallchat.common.user.dao.UserDao;
import com.nb.mallchat.common.user.domain.entity.User;
import com.nb.mallchat.common.user.domain.enums.ItemEnum;
import com.nb.mallchat.common.user.domain.vo.req.ModifyNameReq;
import com.nb.mallchat.common.user.domain.vo.resp.UserInfoResp;
import com.nb.mallchat.common.user.service.UserService;
import com.nb.mallchat.common.user.service.adapter.UserAdapter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void modifyName(Long uid, String name) {
        User oldUser = userDao.getByName(name);
        if(Objects.nonNull(oldUser)){
            throw new BusinessException("重名请换名!");
        }
    }
}
