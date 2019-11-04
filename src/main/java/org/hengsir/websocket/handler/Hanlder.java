package org.hengsir.websocket.handler;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author hengsir
 * @date 2019/3/13 上午11:11
 */
public interface Hanlder {

    void produceChat(
        WebSocketHandler webSocketHandler, ChannelHandlerContext ctx, String requestJson);

}
