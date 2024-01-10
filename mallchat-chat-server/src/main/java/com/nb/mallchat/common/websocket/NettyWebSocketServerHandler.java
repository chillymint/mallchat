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
import com.nb.mallchat.common.websocket.service.WebSocketService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Sharable
public class NettyWebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    private WebSocketService webSocketService;
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        webSocketService = SpringUtil.getBean(WebSocketService.class);
        //进行连接channel的保存
        webSocketService.connect(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        userOffline(ctx.channel());
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof WebSocketServerProtocolHandler.HandshakeComplete){
            String token = NettyUtil.getAttr(ctx.channel(), NettyUtil.TOKEN);
            if(StrUtil.isNotBlank(token)){
                webSocketService.authorize(ctx.channel(), token);
            }
            System.out.println("握手完成");

        }else if(evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                System.out.println("目前读是空闲!");
                // todo 用户下线
                userOffline(ctx.channel());
            }
        }
        // }else if(evt == WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE){
        //     System.out.println("握手请求");
        //     Attribute<Object> token1 = ctx.channel().attr(AttributeKey.valueOf("token"));
        //     webSocketService.authorize(ctx.channel(), token1.get().toString());
        //
        // }
    }

    /**
     * 用户下线统一处理
     * @param channel
     */
    private void userOffline(Channel channel){
        webSocketService.remove(channel);
        channel.close();
    }

    // 读取客户端发送的请求报文
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        String text = msg.text();
        WSBaseReq wsBaseReq = JSONUtil.toBean(text, WSBaseReq.class);
        switch (WSReqTypeEnum.of(wsBaseReq.getType())) {
            case AUTHORIZE:
                webSocketService.authorize(ctx.channel(), wsBaseReq.getData());
                break;
            case HEARTBEAT:
                break;
            case LOGIN:
                // System.out.println("请求二维码");
                // ctx.channel().writeAndFlush(new TextWebSocketFrame("123"));
                webSocketService.handleLoginReq(ctx.channel());
        }
    }

}
