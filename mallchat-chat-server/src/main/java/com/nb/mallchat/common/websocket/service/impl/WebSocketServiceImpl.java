package com.nb.mallchat.common.websocket.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.nb.mallchat.common.websocket.domain.dto.WSChannelExtraDTO;
import com.nb.mallchat.common.websocket.service.WebSocketService;
import io.netty.channel.Channel;

import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 专门管理websocket的逻辑，包括推拉
 * @author yunfu.ye
 * @date 2023/12/5 17:35
 */
public class WebSocketServiceImpl implements WebSocketService {
    /**
     * 管理所有用户的连接(登录态/游客)
     */
    private static final ConcurrentHashMap<Channel, WSChannelExtraDTO> ONLINE_WS_MAP = new ConcurrentHashMap<>();

    /**
     * ctrl+alt+c10000、Duration.ofHours(1)变成大写常量
     */
    public static final Duration DURATION = Duration.ofHours(1);
    public static final int MAXIMUM_SIZE = 10000;
    private static final Cache<Integer, Channel> WAIT_LOGIN_MAP = Caffeine.newBuilder()
            .maximumSize(MAXIMUM_SIZE)
            .expireAfterWrite(DURATION)
            .build();
    @Override
    public void connect(Channel channel) {
        //保存连接
        ONLINE_WS_MAP.put(channel, new WSChannelExtraDTO());

    }

    @Override
    public void handleLoginReq(Channel channel) {
        //生成随机码
        Integer code = generateLoginCode(channel);
        //找微信申请带参二维码
        //把码推送给前端

    }

    private Integer generateLoginCode(Channel channel) {
        return 1;
    }
}
