package org.hengsir.simpleBlogComment.dao;

import org.hengsir.simpleBlogComment.dao.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hengsir
 * @date 2018/7/3 下午4:03
 */
@Service
public class CommentDaoImpl implements CommentDao{
    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<Comment> getComment(String blogName) {
        return commentMapper.getComment(blogName);
    }

    @Override
    public boolean insertComment(Comment comment) {
        try {
            commentMapper.insertComment(comment);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean createBlog(String blogName) {
        try {
            commentMapper.createBlog(blogName);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Blog selectBlogByName(String blogName) {
        return commentMapper.selectBlogByName(blogName);
    }
}
