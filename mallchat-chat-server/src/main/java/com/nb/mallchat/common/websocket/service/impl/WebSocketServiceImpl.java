package com.nb.mallchat.common.websocket.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.nb.mallchat.common.websocket.domain.dto.WSChannelExtraDTO;
import com.nb.mallchat.common.websocket.domain.enums.WSRespTypeEnum;
import com.nb.mallchat.common.websocket.domain.vo.resp.WSBaseResp;
import com.nb.mallchat.common.websocket.domain.vo.resp.WSLoginUrl;
import com.nb.mallchat.common.websocket.service.WebSocketService;
import com.nb.mallchat.common.websocket.service.adapter.WebSocketAdapter;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 专门管理websocket的逻辑，包括推拉
 */

@Component
@Slf4j
public class WebSocketServiceImpl implements WebSocketService {
    @Autowired
    WxMpService wxMpService;
    /**
     * 管理所有用户的连接(登录态/游客)
     */
    private static final ConcurrentHashMap<Channel, WSChannelExtraDTO> ONLINE_WS_MAP = new ConcurrentHashMap<>();

    /**
     * ctrl+alt+c10000、Duration.ofHours(1)变成大写常量
     */
    public static final Duration DURATION = Duration.ofHours(1);
    public static final int MAXIMUM_SIZE = 10000;

    /**
     * 临时保存登录code和channel的映射关系
     */
    private static final Cache<Integer, Channel> WAIT_LOGIN_MAP = Caffeine.newBuilder()
            .maximumSize(MAXIMUM_SIZE)
            .expireAfterWrite(DURATION)
            .build();

    @Override
    public void offline(Channel channel) {
        ONLINE_WS_MAP.remove(channel);
        //todo 用户下线

    }

    @Override
    public void connect(Channel channel) {
        //保存连接
        ONLINE_WS_MAP.put(channel, new WSChannelExtraDTO());

    }

    @SneakyThrows
    @Override
    public void handleLoginReq(Channel channel) {
        //生成随机码
        Integer code = generateLoginCode(channel);
        //找微信申请带参二维码
        WxMpQrCodeTicket wxMpQrCodeTicket = wxMpService.getQrcodeService().qrCodeCreateTmpTicket(code, (int) DURATION.getSeconds());
        //把码推送给前端
        sendMsg(channel, WebSocketAdapter.buildResp(wxMpQrCodeTicket));
    }

    @Override
    public void remove(Channel channel) {
        ONLINE_WS_MAP.remove(channel);
        //todo 用户下线


    }

    private void sendMsg(Channel channel, WSBaseResp<WSLoginUrl> resp) {
        channel.writeAndFlush(new TextWebSocketFrame(JSONUtil.toJsonStr(resp)));
    }

    private Integer generateLoginCode(Channel channel) {
        Integer code;
        //必须不重复才设置成功
        do{
            code = RandomUtil.randomInt(Integer.MAX_VALUE);
        }while (Objects.nonNull(WAIT_LOGIN_MAP.asMap().putIfAbsent(code, channel)));
        return code;
    }
}
