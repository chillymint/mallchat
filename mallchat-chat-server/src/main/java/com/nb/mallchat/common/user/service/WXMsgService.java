package com.nb.mallchat.common.user.service;

import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Service;

/**
 */
@Service
public interface WXMsgService {
    /**
     * 用户扫码成功
     * @param wxMpXmlMessage
     */
    WxMpXmlOutMessage scan(WxMpXmlMessage wxMpXmlMessage);

    /**
     * 用户授权
     * @param userInfo
     */
    void authorize(WxOAuth2UserInfo userInfo);
}
