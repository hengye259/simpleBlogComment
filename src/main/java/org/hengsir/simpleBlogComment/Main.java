package org.hengsir.simpleBlogComment;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.websocket.server.WsSci;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author hengsir
 * @date 2018/7/3 下午5:18
 */
@SpringBootApplication
@MapperScan("org.hengsir.simpleBlogComment.dao.mapper")
@EnableWebSocket
public class Main{

    @Value("${http.port}")
    private int httpPort;

    @Value("${server.port}")
    private int sslPort;
   /* @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Main.class);
    }*/

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        System.out.println("-----------------嘤嘤嘤嘤嘤~~~~启动成功！！！！！------------------" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
    }

    /*@Bean
    public ServletWebServerFactory servletContainer(){
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.addAdditionalTomcatConnectors(createStandardConnector());
        return tomcat;
    }

    // 配置http
    private Connector createStandardConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setPort(httpPort);
        connector.setSecure(false);
        connector.setRedirectPort(sslPort);
        return connector;
    }*/

    /**
     * 配置wss。
     *
     * @return
     */
    /*@Bean
    public TomcatContextCustomizer tomcatContextCustomizer() {
        System.out.println("TOMCATCONTEXTCUSTOMIZER INITILIZED");
        return context -> context.addServletContainerInitializer(new WsSci(), null);
    }*/

}
