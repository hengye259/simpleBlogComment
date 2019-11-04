package org.hengsir.novel.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 章节model
 *
 * @author hengsir
 * @date 2019/11/4 1:47 下午
 */
@Data
public class Section {

    /**
     * 标题
     */
    @JSONField(name = "title")
    private String title;

    /**
     * 上一章
     */
    @JSONField(name = "preUrl")
    private String preUrl;

    /**
     * 下一章
     */
    @JSONField(name = "nextUrl")
    private String nextUrl;

    /**
     * 本章内容
     */
    @JSONField(name = "content")
    private String content;

    /**
     * 目录url
     */
    private String mulu;

}
