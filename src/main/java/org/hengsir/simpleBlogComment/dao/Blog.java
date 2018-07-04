package org.hengsir.simpleBlogComment.dao;

/**
 * @author hengsir
 * @date 2018/7/3 下午4:40
 */
public class Blog {
    private int id;
    private String blogName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBlogName() {
        return blogName;
    }

    public void setBlogName(String blogName) {
        this.blogName = blogName;
    }
}
