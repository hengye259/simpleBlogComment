package org.hengsir.novel.model;

import lombok.Data;

import java.util.List;

/**
 * @author hengsir
 * @date 2019/11/4 11:47 上午
 */
@Data
public class NovelInfo {

    /**
     * 小说名
     */
    private String name;

    /**
     * 章节
     */
    private List<Section> sections;
}
