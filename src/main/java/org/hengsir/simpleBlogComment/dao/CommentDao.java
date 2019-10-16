package org.hengsir.simpleBlogComment.dao;


import org.hengsir.simpleBlogComment.model.Blog;
import org.hengsir.simpleBlogComment.model.Comment;

import java.util.List;

/**
 * @author hengsir
 * @date 2018/7/3 下午4:03
 */
public interface CommentDao {
    List<Comment> getComment(String blogName);

    boolean insertComment(Comment comment);

    boolean createBlog(String blogName);

    Blog selectBlogByName(String blogName);

    Blog selectBlogById(int id);

    List<Comment> selectToday();

    void insertToday(Comment comment);

    void clearToday();
}
