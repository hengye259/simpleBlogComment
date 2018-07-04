package org.hengsir.simpleBlogComment.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.hengsir.simpleBlogComment.dao.Blog;
import org.hengsir.simpleBlogComment.dao.Comment;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author hengsir
 * @date 2018/7/3 下午4:09
 */
@Component
public interface CommentMapper {
    List<Comment> getComment(@Param("blogName") String blogName);
    void insertComment(Comment comment);
    void createBlog(@Param("blogName") String blogName);
    Blog selectBlogByName(@Param("blogName") String blogName);
}
