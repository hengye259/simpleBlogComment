package org.hengsir.simpleBlogComment.model;

import io.netty.channel.ChannelHandlerContext;
import org.hengsir.simpleBlogComment.webSocket.handler.WebSocketHandler;

/**
 * @author hengsir
 * @date 2019/3/13 上午11:21
 */
public class DefaultContext {
    /**
     * 客户id
     */
    private String Id;

    /**
     * 客户连接上下文
     */
    private ChannelHandlerContext channelHandlerContext;

    private WebSocketHandler webSocketHandler;

    public WebSocketHandler getWebSocketHandler() {
        return webSocketHandler;
    }

    public void setWebSocketHandler(WebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
    }

    /**
     * 客户发送的消息
     * 例如：{"type":"01","user":"sr","data":....}
     */
    private String requestJson;

    public DefaultContext() {
    }

    public DefaultContext(ChannelHandlerContext channelHandlerContext, WebSocketHandler webSocketHandler, String requestJson) {
        this.channelHandlerContext = channelHandlerContext;
        this.webSocketHandler = webSocketHandler;
        this.requestJson = requestJson;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public ChannelHandlerContext getChannelHandlerContext() {
        return channelHandlerContext;
    }

    public void setChannelHandlerContext(ChannelHandlerContext channelHandlerContext) {
        this.channelHandlerContext = channelHandlerContext;
    }

    public String getRequestJson() {
        return requestJson;
    }

    public void setRequestJson(String requestJson) {
        this.requestJson = requestJson;
    }
}
