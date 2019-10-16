package org.hengsir.simpleBlogComment.model;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * @author hengsir
 * @date 2019/10/12 10:50 上午
 */
public class Response {

    @JSONField(name = "type")
    private int type;

    @JSONField(name = "message")
    private String message;

    @JSONField(name = "code")
    private String code;

    @JSONField(name = "comments")
    private List<Comment> comments;

    @JSONField(name = "count")
    private int count;


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
