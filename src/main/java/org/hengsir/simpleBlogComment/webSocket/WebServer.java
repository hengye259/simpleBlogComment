package org.hengsir.simpleBlogComment.webSocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.hengsir.simpleBlogComment.dao.Blog;
import org.hengsir.simpleBlogComment.dao.Comment;
import org.hengsir.simpleBlogComment.dao.CommentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author hengsir
 * @date 2018/7/11 下午3:30
 */
@Component
public class WebServer  implements WebSocketHandler{
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<WebSocketSession> webSessionSet;

    @Autowired
    private CommentDao commentDao;
    private WebSocketMessage<String> message;


    static{
        webSessionSet = new CopyOnWriteArraySet<>();
    }
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private WebSocketSession session;


    /**
     * 群发自定义消息
     *
     * @param message
     * @throws IOException
     */
    public static void sendInfo(String message) throws IOException {
        for (WebSocketSession item : webSessionSet) {
            try {
                item.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                continue;
            }
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        this.session = webSocketSession;
        //连接成功
        webSessionSet.add(webSocketSession);
        //给连接进来的客户端发送今天留言
        /*List<Comment> todays = commentDao.selectToday();
        List<Blog> blogs = new ArrayList<>();
        for (Comment c : todays){
            Blog b = commentDao.selectBlogById(c.getBlogId());
            blogs.add(b);
        }
        sendInfo(JSON.toJSONString(blogs));*/
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {

    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        webSessionSet.remove(webSocketSession);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
