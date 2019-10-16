package org.hengsir.simpleBlogComment.webSocket.coder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;

/**
 * @author hengsir
 * @date 2018/6/20 下午5:08
 */
public class MessageEncoder extends MessageToByteEncoder<String> {

    private static final Logger logger = LoggerFactory.getLogger(MessageEncoder.class);

    private Charset charset;

    public MessageEncoder() {
        this.charset = Charset.forName("UTF-8");
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, String s, ByteBuf byteBuf) throws Exception {
        if (s != null && s.length() != 0) {
            byteBuf.writeBytes(MessageLengthUtil.toAsicii(s.getBytes(this.charset).length));
            byteBuf.writeBytes(s.getBytes(this.charset));

        }    
    }

    
}
