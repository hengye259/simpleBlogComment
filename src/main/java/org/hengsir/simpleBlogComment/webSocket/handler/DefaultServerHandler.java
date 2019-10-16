package org.hengsir.simpleBlogComment.webSocket.handler;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

@Sharable
public class DefaultServerHandler extends SimpleChannelInboundHandler<String>
    implements ApplicationContextAware {

    private static final Logger log = LoggerFactory.getLogger(DefaultServerHandler.class);


    public static ApplicationContext springContext = null;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        springContext = applicationContext;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.debug("与客户端建立链接:{}", ctx.channel().remoteAddress().toString());
        super.channelActive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext context, String requestJson)
        throws Exception {
        //dothings

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);

    }

}

