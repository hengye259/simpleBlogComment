package org.hengsir.simpleBlogComment.model;

/**
 * @author hengsir
 * @date 2019/3/13 上午9:45
 */


import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 连接队列管理
 */
public class SocketGroup extends DefaultChannelGroup {

    private static final Logger logger = LoggerFactory.getLogger(SocketGroup.class);

    /**
     * 保存客户对客服的映射
     */
    public static final Map<String,SocketContext> SOCKETGROUP = new ConcurrentHashMap<>();

    /**
     * 客服Map
     */
    public static final Map<String,DefaultContext> servers = new ConcurrentHashMap<>();

    /**
     * 客户Map
     */
    public static final Map<String,DefaultContext> clients = new ConcurrentHashMap<>();

    // Channel关闭，更新BUSINESS_MAP
    private final ChannelFutureListener remover = new ChannelFutureListener() {
        @Override
        public void operationComplete(ChannelFuture future) throws Exception {
            logger.debug("Channel关闭前，更新BUSINESS_MAP。");
            SocketGroup.this.removeChannel(future.channel());
        }
    };

    /**
     * @param groupName
     */
    public SocketGroup(String groupName) {
        super(groupName, GlobalEventExecutor.INSTANCE);
    }

    /**
     * @param groupName
     * @param executor
     */
    public SocketGroup(String groupName, EventExecutor executor) {
        super(groupName, executor);
    }

    /**
     * 添加映射保存
     * @param groupId
     * @param socketContext
     */
    public void add(String groupId,SocketContext socketContext){
        SocketContext oldSocketContext = SOCKETGROUP.get(groupId);
        if (oldSocketContext != null && oldSocketContext.getClientChannel().compareTo(socketContext.getClientChannel()) != 0
                && oldSocketContext.getServerChannel().compareTo(socketContext.getServerChannel()) != 0){
            //旧的信道还在，先关闭
            logger.warn("旧的SocketContext还存在，先关闭。groupId={}", groupId);
            oldSocketContext.getServerChannel().close();
            oldSocketContext.getClientChannel().close();
        }
        //如果原先该服务端没有
        boolean add = false;
        if (super.find(socketContext.getServerChannel().id()) == null) {
            add = (super.add(socketContext.getClientChannel()) && super.add(socketContext.getServerChannel()));
            if (add){
                socketContext.getServerChannel().closeFuture().addListener(remover);
            }
        } else {
            add = super.add(socketContext.getClientChannel());
        }
        if (add){
            SOCKETGROUP.put(groupId,socketContext);
            socketContext.getClientChannel().closeFuture().addListener(remover);
            logger.info("添加客户与客服映射成功，groupid={}",groupId);
        }
    }

    /**
     * 获取映射关系
     * @param groupId
     * @return
     */
    public SocketContext get(String groupId){
        return null;
    }

    /**
     * 删除映射关系
     * @param groupId
     */
    public void remove(String groupId){
        SocketContext socketContext = SOCKETGROUP.remove(groupId);

        removeChannel(socketContext.getClientChannel());
    }

    /**
     * 删除连接
     * @param channel
     */
    public void removeChannel(Channel channel){
        channel.closeFuture().removeListener(remover);
    }

    public void removeGroup(String groupId){
        String[] groupIds = groupId.split("-");
        String clientId = groupIds[0];
        String serverId = groupIds[1];
        clients.remove(clientId);
    }
}
