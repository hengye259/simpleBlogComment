package org.hengsir.simpleBlogComment.webSocket.coder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.ChannelInputShutdownEvent;
import io.netty.handler.codec.DecoderException;

import java.nio.charset.Charset;

/**
 * @author hengsir
 * @date 2018/6/20 下午5:15
 */
public class MessageDecoder extends ChannelInboundHandlerAdapter {
    public static final MessageDecoder.Cumulator MERGE_CUMULATOR = new MessageDecoder.Cumulator() {
        public ByteBuf cumulate(ByteBufAllocator alloc, ByteBuf cumulation, ByteBuf in) {
            ByteBuf buffer;
            if (cumulation.writerIndex() <= cumulation.maxCapacity() - in.readableBytes() && cumulation.refCnt() <= 1) {
                buffer = cumulation;
            } else {
                buffer = MessageDecoder.expandCumulation(alloc, cumulation, in.readableBytes());
            }

            buffer.writeBytes(in);
            in.release();
            return buffer;
        }
    };
    private Charset charset;
    ByteBuf cumulation;
    private MessageDecoder.Cumulator cumulator;
    private boolean first;
    private int numReads;
    private int dataLen;

    public MessageDecoder() {
        this.cumulator = MERGE_CUMULATOR;
        this.charset = Charset.forName("UTF-8");
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof ByteBuf) {
            try {
                ByteBuf data = (ByteBuf)msg;
                this.first = this.cumulation == null;
                if (this.first) {
                    byte[] lenBytes = new byte[4];
                    data.getBytes(0, lenBytes);
                    this.dataLen = MessageLengthUtil.toInt(lenBytes);
                    this.cumulation = data.copy(4, data.readableBytes() - 4);
                    this.numReads = this.cumulation.readableBytes();
                } else {
                    this.cumulation = this.cumulator.cumulate(ctx.alloc(), this.cumulation, data);
                }

                if (this.cumulation.readableBytes() == this.dataLen) {
                    ctx.fireChannelRead(this.cumulation.toString(this.charset));
                    this.resetCumulation();
                }
            } catch (Exception var5) {
                throw new DecoderException(var5);
            }
        } else {
            ctx.fireChannelRead(msg);
        }

    }

    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof ChannelInputShutdownEvent) {
            this.resetCumulation();
        }

        super.userEventTriggered(ctx, evt);
    }

    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        this.resetCumulation();
        super.channelReadComplete(ctx);
    }

    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        this.resetCumulation();
        super.channelInactive(ctx);
    }

    private void resetCumulation() {
        if (this.cumulation != null) {
            this.cumulation.release();
            this.cumulation = null;
            this.dataLen = -1;
        }

    }

    static ByteBuf expandCumulation(ByteBufAllocator alloc, ByteBuf cumulation, int readable) {
        ByteBuf oldCumulation = cumulation;
        cumulation = alloc.buffer(cumulation.readableBytes() + readable);
        cumulation.writeBytes(oldCumulation);
        oldCumulation.release();
        return cumulation;
    }

    public interface Cumulator {
        ByteBuf cumulate(ByteBufAllocator var1, ByteBuf var2, ByteBuf var3);
    }
}
