package org.hengsir.simpleBlogComment.model;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * @author hengsir
 * @date 2019/10/12 10:50 上午
 */
@Data
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

}
