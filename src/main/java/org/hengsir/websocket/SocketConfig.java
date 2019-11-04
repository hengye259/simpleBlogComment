package org.hengsir.websocket;

import org.hengsir.websocket.handler.SocketHandler;
import org.hengsir.websocket.handler.WebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author hengsir
 * @date 2019/11/4 3:56 下午
 */
@Configuration
public class SocketConfig {

    @Value("${ws.port}")
    private int port;

    @Value("${server.ssl.key-store-password}")
    private String sslPwd;

    @Autowired
    private SocketHandler socketHandler;

    @Bean
    public WebSocketHandler novelWebSocketHandler() {
        WebSocketHandler webSocketHandler = new WebSocketHandler();
        webSocketHandler.setPort(port);
        webSocketHandler.setHandler(socketHandler);
        return webSocketHandler;
    }

    @Bean
    public DefaultChannelInitializer novelChannelInitializer() {
        DefaultChannelInitializer defaultChannelInitializer = new DefaultChannelInitializer();
        defaultChannelInitializer.setPort(port);
        defaultChannelInitializer.setSslPwd(sslPwd);
        defaultChannelInitializer.setWebSocketHandler(novelWebSocketHandler());
        return defaultChannelInitializer;
    }

    @Bean
    public DefaultSocketServer novelSocketServer(){
        DefaultSocketServer defaultSocketServer = new DefaultSocketServer();
        defaultSocketServer.setPort(port);
        defaultSocketServer.setMyChannelInitializer(novelChannelInitializer());
        return defaultSocketServer;
    }

}
