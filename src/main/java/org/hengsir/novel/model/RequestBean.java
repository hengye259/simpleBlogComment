package org.hengsir.novel.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author hengsir
 * @date 2019/11/4 3:44 下午
 */
@Data
public class RequestBean {
    @JSONField(name = "method")
    private String method;
    @JSONField(name = "url")
    private String url;
}
