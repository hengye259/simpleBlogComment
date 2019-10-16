package org.hengsir.simpleBlogComment.webSocket;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.hengsir.simpleBlogComment.webSocket.handler.WebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Sharable
@Component
public class DefaultChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Autowired
    private WebSocketHandler webSocketHandler;

    public void initChannel(SocketChannel channel) {
        ChannelPipeline pipeline = channel.pipeline();
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
