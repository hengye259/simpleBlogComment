package org.hengsir.websocket;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.Data;
import org.hengsir.websocket.handler.WebSocketHandler;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

@Sharable
@Data
public class DefaultChannelInitializer extends ChannelInitializer<SocketChannel> {

    private int port;

    private String sslPwd;

    private WebSocketHandler webSocketHandler;

    public void initChannel(SocketChannel channel)
        throws KeyStoreException, NoSuchAlgorithmException, IOException, KeyManagementException,
               CertificateException, UnrecoverableKeyException {
        ChannelPipeline pipeline = channel.pipeline();
        // 以下为要支持wss所需处理
        /*KeyStore ks = KeyStore.getInstance("JKS");
        ClassPathResource cpr = new ClassPathResource("www.hengsir.cn.jks");
        InputStream ksInputStream = cpr.getInputStream();
        ks.load(ksInputStream, sslPwd.toCharArray());
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(ks, sslPwd.toCharArray());
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(kmf.getKeyManagers(), null, null);
        SSLEngine sslEngine = sslContext.createSSLEngine();
        sslEngine.setUseClientMode(false);
        sslEngine.setNeedClientAuth(false);
        // 需把SslHandler添加在第一位
        pipeline.addLast("ssl", new SslHandler(sslEngine));*/
        //websocket协议本身是基于http协议的，所以这边也要使用http解编码器
        pipeline.addLast("http-codec",new HttpServerCodec());
        //netty是基于分段请求的，HttpObjectAggregator的作用是将请求分段再聚合,参数是聚合字节的最大长度
        pipeline.addLast("aggregator",new HttpObjectAggregator(1024*1024*1024));
        //以块的方式来写的处理器
        pipeline.addLast("http-chunked",new ChunkedWriteHandler());
        //添加自定义的websocketHandler
        pipeline.addLast("handler",webSocketHandler);

    }
}
