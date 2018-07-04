package org.hengsir.simpleBlogComment.dao;


import java.util.List;

/**
 * @author hengsir
 * @date 2018/7/3 下午4:03
 */
public interface CommentDao {
    List<Comment> getComment( String blogName);
    boolean insertComment(Comment comment);
    boolean createBlog(String blogName);
    Blog selectBlogByName(String blogName);
}
