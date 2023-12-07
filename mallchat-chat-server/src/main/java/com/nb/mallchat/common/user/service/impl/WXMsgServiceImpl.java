package com.nb.mallchat.common.user.service.impl;

import cn.hutool.core.util.StrUtil;
import com.nb.mallchat.common.user.dao.UserDao;
import com.nb.mallchat.common.user.domain.entity.User;
import com.nb.mallchat.common.user.service.UserService;
import com.nb.mallchat.common.user.service.WXMsgService;
import com.nb.mallchat.common.user.service.adapter.UserAdapter;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 */
@Service
@Slf4j
public class WXMsgServiceImpl implements WXMsgService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserService userService;
    @Override
    public WxMpXmlOutMessage scan(WxMpXmlMessage wxMpXmlMessage) {
        String openId = wxMpXmlMessage.getFromUser();
        Integer code = getEventKey(wxMpXmlMessage);
        if(Objects.isNull(code)){
            return null;
        }
        User user = userDao.getByOpenId(openId);
        boolean registered = Objects.nonNull(user);
        boolean authorized = StrUtil.isNotBlank(user.getAvatar());
        //头像不为空已经注册成功(用户已经注册并授权)
        if(registered && authorized ){
            //走登录成功的逻辑 通过code找到给channel推送消息todo
        }
        if(!registered){
            User insert = UserAdapter.buildUserSave(openId);
            userService.register(insert);
        }

        return null;
    }

    private Integer getEventKey(WxMpXmlMessage wxMpXmlMessage) {
        try{
            String eventKey = wxMpXmlMessage.getEventKey();
            String code = eventKey.replace("qrscene_", "");
            return Integer.parseInt(code);
        }catch (Exception e) {
            log.error("getEventKey error eventKey:{}",wxMpXmlMessage.getEventKey(), e);
            return null;
        }

    }
}
