package com.nb.mallchat.common.websocket.service;

import io.netty.channel.Channel;
import org.springframework.stereotype.Service;

/**
 * @Service加入spring容器管理
 * @author yunfu.ye
 * @date 2023/12/5 17:35
 */
@Service
public interface WebSocketService {
    void connect(Channel channel);

    void handleLoginReq(Channel channel);
}
