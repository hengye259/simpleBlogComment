package org.hengsir.simpleBlogComment.model;


import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author hengsir
 * @date 2019/10/12 10:50 上午
 */
public class Request {

    @JSONField(name = "type")
    private int type;

    @JSONField(name = "blogName")
    private String blogName;

    @JSONField(name = "text")
    private String text;

    @JSONField(name = "author")
    private String author;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getBlogName() {
        return blogName;
    }

    public void setBlogName(String blogName) {
        this.blogName = blogName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
