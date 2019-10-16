package org.hengsir.simpleBlogComment.model;

/**
 * 评论实体
 *
 * @author hengsir
 * @date 2018/7/3 下午4:07
 */
public class Comment {
    private int id;
    private String text;
    private int blogId;
    private String author;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getBlogId() {
        return blogId;
    }

    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
