package com.nb.mallchat.common.websocket;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
//import com.nb.mallchat.common.user.domain.enums.WSReqTypeEnum;
//import com.nb.mallchat.common.user.domain.vo.request.ws.WSAuthorize;
//import com.nb.mallchat.common.user.domain.vo.request.ws.WSBaseReq;
//import com.nb.mallchat.common.user.service.WebSocketService;
import com.nb.mallchat.common.websocket.domain.enums.WSReqTypeEnum;
import com.nb.mallchat.common.websocket.domain.vo.req.WSBaseReq;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Sharable
public class NettyWebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof WebSocketServerProtocolHandler.HandshakeComplete){
            System.out.println("握手完成");
        }
    }

    // 读取客户端发送的请求报文
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        String text = msg.text();
        WSBaseReq wsBaseReq = JSONUtil.toBean(text, WSBaseReq.class);
        switch (WSReqTypeEnum.of(wsBaseReq.getType())) {
            case AUTHORIZE:
                break;
            case HEARTBEAT:
                break;
            case LOGIN:
                System.out.println("请求二维码");
                ctx.channel().writeAndFlush(new TextWebSocketFrame("123"));
        }
    }

}
