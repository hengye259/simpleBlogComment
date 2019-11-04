package org.hengsir.simpleBlogComment.model;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author hengsir
 * @date 2019/10/12 10:50 上午
 */
@Data
public class Request {

    @JSONField(name = "method")
    private String method;
    @JSONField(name = "url")
    private String url;

    @JSONField(name = "type")
    private int type;

    @JSONField(name = "blogName")
    private String blogName;

    @JSONField(name = "text")
    private String text;

    @JSONField(name = "author")
    private String author;

}
