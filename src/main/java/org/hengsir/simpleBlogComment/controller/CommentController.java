package org.hengsir.simpleBlogComment.controller;

import net.sf.json.JSONObject;
import org.hengsir.simpleBlogComment.dao.Blog;
import org.hengsir.simpleBlogComment.dao.Comment;
import org.hengsir.simpleBlogComment.dao.CommentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author hengsir
 * @date 2018/7/3 下午4:55
 */
@Controller
@RequestMapping("/comm")
public class CommentController {
    @Autowired
    private CommentDao commentDao;

    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        return "test";
    }

    //@CrossOrigin("http://localhost:8081")
    @RequestMapping("/get-comments")
    @ResponseBody
    public List<Comment> getComments(String blogName) {
        return commentDao.getComment(blogName);
    }

    //@CrossOrigin("http://localhost:8081")
    @RequestMapping(value = "/to-comment", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object insertComment(String text, String blogName, String author) {
        JSONObject jsonObject = new JSONObject();
        Blog blog = commentDao.selectBlogByName(blogName);
        if (blog == null) {
            if (commentDao.createBlog(blogName)) {
                blog = commentDao.selectBlogByName(blogName);
            } else {
                jsonObject.accumulate("result", false);
                jsonObject.accumulate("detail", "数据库创建博客失败");
                return jsonObject;
            }
        }
        Comment comment = new Comment();
        comment.setBlogId(blog.getId());
        comment.setAuthor(author);
        comment.setText(text);
        if (commentDao.insertComment(comment)) {
            jsonObject.accumulate("result", true);
            jsonObject.accumulate("detail", "留言成功");
        } else {
            jsonObject.accumulate("result", false);
            jsonObject.accumulate("detail", "数据库写入留言失败");
        }
        return jsonObject;
    }

}
