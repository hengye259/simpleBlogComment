package org.hengsir.simpleBlogComment.webSocket.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.hengsir.simpleBlogComment.dao.CommentDao;
import org.hengsir.simpleBlogComment.model.Blog;
import org.hengsir.simpleBlogComment.model.Comment;
import org.hengsir.simpleBlogComment.model.DefaultContext;
import org.hengsir.simpleBlogComment.model.Request;
import org.hengsir.simpleBlogComment.model.Response;
import org.hengsir.simpleBlogComment.model.TypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 处理客服与客户之间的逻辑
 *
 * @author hengsir
 * @date 2019/3/13 上午10:59
 */
@Component
public class CommentHandler implements Hanlder {
    private static final Logger logger   = LoggerFactory.getLogger(Hanlder.class);
    //处理聊天的线程池
    protected static final ExecutorService chatExec = Executors.newFixedThreadPool(2);

    //聊天请求队列
    private ArrayBlockingQueue<DefaultContext> chatQueue = new ArrayBlockingQueue<>(500);


    private static AtomicBoolean isInitThread = new AtomicBoolean(false);

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    public static CopyOnWriteArraySet<DefaultContext> contexts;

    //连接数
    private static volatile int count = 0;

    @Autowired
    private CommentDao commentDao;


    static {
        contexts = new CopyOnWriteArraySet<>();
    }

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;


    @Override
    public void produceChat(
        WebSocketHandler webSocketHandler, ChannelHandlerContext ctx, String requestJson) {
        DefaultContext context = new DefaultContext(ctx, webSocketHandler, requestJson);
        chatQueue.add(context);
        contexts.add(context);
        //没有初始化，则初始化4个线程任务(4个任务，最大4个线程)
        if (!isInitThread.get()) {
            for (int i = 0; i < 2; i++) {
                chatExec.submit(new ChatProcessor());
            }
            isInitThread.set(true);
        }
    }

    public void sendToWeb(DefaultContext context, String respJson) {
        try {
            context.getWebSocketHandler().sendRespon(
                context.getChannelHandlerContext(), new TextWebSocketFrame(respJson));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 评论
     *
     * @param req
     * @return
     */
    private Response toComment(Request req, DefaultContext context) throws IOException {
        System.out.println("评论请求来了" + JSON.toJSONString(req));
        String code = "00";
        String message = null;
        Blog blog = commentDao.selectBlogByName(req.getBlogName());
        if (blog == null) {
            if (commentDao.createBlog(req.getBlogName())) {
                blog = commentDao.selectBlogByName(req.getBlogName());
                code = "00";
            } else {
                code = "FB";
                message = "系统创建博客失败";
            }
        }
        if ("00".equalsIgnoreCase(code)) {
            Comment comment = new Comment();
            comment.setBlogId(blog.getId());
            comment.setAuthor(req.getAuthor());
            comment.setText(req.getText());
            if (commentDao.insertComment(comment)) {
                message = "留言成功";
                //写入今天留言表
                commentDao.insertToday(comment);
                //通知
                String finalCode = code;
                Blog finalBlog = blog;
                new Thread(() -> {
                    logger.info("通知在线用户有新评论");
                    Response notify = new Response();
                    notify.setCode(finalCode);
                    notify.setMessage(finalBlog.getBlogName() + "有新留言");
                    notify.setType(TypeEnum.NOTIFY.getType());
                    CommentHandler.this.notifyAll(context, JSON.toJSONString(notify));
                }).start();
            } else {
                code = "FC";
                message = "留言失败";
            }
        }
        Response response = new Response();
        response.setType(TypeEnum.TO_COMMENTS.getType());
        response.setCode(code);
        response.setMessage(message);
        response.setCount(CommentHandler.count);
        System.out.println("返回" + JSON.toJSONString(response));
        return response;
    }

    /**
     * 通知所有除自己外
     *
     * @param ignore  排除的
     * @param message 消息
     */
    public void notifyAll(DefaultContext ignore, String message) {
        for (DefaultContext c : contexts) {
            if (!c.equals(ignore)) {
                sendToWeb(c, message);
            }
        }
    }

    /**
     * 获取评论
     *
     * @param req
     * @return
     */
    private Response getComments(Request req) {
        System.out.println("获取评论请求来了" + JSON.toJSONString(req));
        String blogName = req.getBlogName();
        String code = null;
        String message = null;
        List<Comment> list = null;
        try {
            list = commentDao.getComment(blogName);
            JSONArray commonts = JSON.parseArray(JSON.toJSONString(list));
            code = "00";
            message = "获取评论成功";
        } catch (Exception e) {
            code = "FF";
            message = "获取评论失败：" + e.getMessage();
        }

        Response response = new Response();
        response.setType(TypeEnum.GET_COMMENTS.getType());
        response.setCode(code);
        response.setComments(list);
        response.setMessage(message);
        response.setCount(CommentHandler.count);
        System.out.println("返回" + JSON.toJSONString(response));
        return response;
    }

    public Response handle(DefaultContext context) throws IOException {
        Request req = JSON.parseObject(context.getRequestJson(), Request.class);
        System.out.println(JSON.toJSONString(req));
        int type = req.getType();
        Response resp = null;
        switch (type) {
            case 2:
                resp = toComment(req, context);
                break;
            case 1:
                resp = getComments(req);
                break;
            default:
                break;
        }
        return resp;
    }

    /**
     * 聊天逻辑
     */
    class ChatProcessor implements Runnable {
        @Override
        public void run() {
            while (true) {
                DefaultContext context = null;
                try {
                    context = chatQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    logger.error("请求出队错误：{}", e.getMessage());
                    continue;
                }
                logger.info("req={}", context.getRequestJson());
                //处理并得到结果
                try {
                    Response resp = handle(context);
                    sendToWeb(context, JSON.toJSONString(resp));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
