package org.hengsir.simpleBlogComment.webSocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.hengsir.simpleBlogComment.config.WebSocketConfig;
import org.hengsir.simpleBlogComment.model.SocketGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Socket服务端
 *
 * @version 1.0-SNAPSHOT
 */
@Component
public class DefaultSocketServer implements InitializingBean, DisposableBean{

    private static final Logger log = LoggerFactory.getLogger(DefaultSocketServer.class);

    public static final SocketGroup socketGroup = new SocketGroup("AllChannel");

    private EventLoopGroup bossGroup;
    private EventLoopGroup workGroup;

    //@Value("${netty.server.port}")
    private Integer port;

    @Autowired
    private DefaultChannelInitializer myChannelInitializer;

    /**
     * 初始化完，启动Server
     */
    public void afterPropertiesSet() throws Exception {
        port = WebSocketConfig.PORT;
        Assert.notNull(port, "port must not be null");
        new Thread(() -> {
            try {
                ServerBootstrap serverBootstrap = new ServerBootstrap();
                bossGroup = new NioEventLoopGroup();
                workGroup = new NioEventLoopGroup();
                serverBootstrap.group(bossGroup, workGroup);
                serverBootstrap.channel(NioServerSocketChannel.class);
                serverBootstrap.childHandler(myChannelInitializer);
                serverBootstrap.option(ChannelOption.SO_BACKLOG, 1024); // 存放已完成三次握手的请求的队列的最大长度
                serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);// 是否启用心跳保活机制
                ChannelFuture future = serverBootstrap.bind(port).sync();
                if (future.isSuccess()) {
                    log.info("---------------netty server1 start，port:{}---------------",port);
                }
                // 使用sync方法进行阻塞，等待服务端链路关闭之后Main函数才退出
                socketGroup.add(future.channel());
                future.channel().closeFuture().sync();
            } catch (Exception exception) {
                log.error("NettyServer init throws exception", exception);
            } finally {
                bossGroup.shutdownGracefully();
                workGroup.shutdownGracefully();
            }
        }).start();
    }

    /**
     * 关闭netty WebSocket服务资源。
     */
    public void destroy() {
        socketGroup.close();
        bossGroup.shutdownGracefully();
        workGroup.shutdownGracefully();
        log.info("NettyServer destroy success.");
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public DefaultChannelInitializer getMyChannelInitializer() {
        return myChannelInitializer;
    }

    public void setMyChannelInitializer(DefaultChannelInitializer myChannelInitializer) {
        this.myChannelInitializer = myChannelInitializer;
    }
}
