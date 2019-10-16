package org.hengsir.simpleBlogComment.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author hengsir
 * @date 2019/10/12 3:34 下午
 */

@Configuration
@ConditionalOnWebApplication
public class WebSocketConfig {

    /**
     * ws端口
     */
    public static final int PORT = 8087;

    //使用boot内置tomcat时需要注入此bean
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }


    @Bean
    public MySpringConfigurator mySpringConfigurator() {
        return new MySpringConfigurator();
    }

}