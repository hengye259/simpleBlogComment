package org.hengsir.simpleBlogComment.model;

import io.netty.channel.Channel;

/**
 * @author hengsir
 * @date 2019/3/13 上午9:51
 */
public class SocketContext {

    /**
     * 映射id
     */
    private String groupId;

    /**
     * 客户channel
     */
    private Channel clientChannel;

    /**
     * 客服channel
     */
    private Channel serverChannel;

    public Channel getClientChannel() {
        return clientChannel;
    }

    public void setClientChannel(Channel clientChannel) {
        this.clientChannel = clientChannel;
    }

    public Channel getServerChannel() {
        return serverChannel;
    }

    public void setServerChannel(Channel serverChannel) {
        this.serverChannel = serverChannel;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
