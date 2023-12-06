package com.nb.mallchat.common.websocket.service.adapter;

import com.nb.mallchat.common.websocket.domain.enums.WSRespTypeEnum;
import com.nb.mallchat.common.websocket.domain.vo.resp.WSBaseResp;
import com.nb.mallchat.common.websocket.domain.vo.resp.WSLoginUrl;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;

/**
 */
public class WebSocketAdapter {
    public static WSBaseResp<WSLoginUrl> buildResp(WxMpQrCodeTicket wxMpQrCodeTicket) {
        WSBaseResp<WSLoginUrl> resp = new WSBaseResp<>();
        resp.setType(WSRespTypeEnum.LOGIN_URL.getType());
        resp.setData(new WSLoginUrl(wxMpQrCodeTicket.getUrl()));
        return resp;
    }
}
