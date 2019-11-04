package org.hengsir.novel.service;

import com.alibaba.fastjson.JSON;
import org.hengsir.novel.model.Section;
import org.hengsir.novel.utils.HttpUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * 网络小说爬取。
 *
 * @author hengsir
 * @date 2019/11/4 11:45 上午
 */
@Service
public class WuJiNovelService {

    //记录上次读取的url，默认从这开始
    private String lastReadUrl;

    //戊戟小说网
    private String parentUrl = "https://m.wujixiaoshuo.com";

    /**
     * 选择上一篇
     *
     * @param url
     * @return
     */
    public Section choosePre(String url) {
        Section section = findSectionNovelByUrl(parentUrl.concat(url));
        this.lastReadUrl = parentUrl.concat(url);
        return section;
    }

    /**
     * 选择下一篇
     *
     * @param url
     * @return
     */
    public Section chooseNext(String url) {
        Section section = findSectionNovelByUrl(parentUrl.concat(url));
        this.lastReadUrl = parentUrl.concat(url);
        return section;
    }

    /**
     * 根据url进行获取章节内容
     *
     * @param url
     * @return
     */
    public Section findSectionNovelByUrl(String url) {
        Document document = null;
        try {
            document = Jsoup.parse(HttpUtils.doGet(url));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        Section section = new Section();
        String title = document.select("title").text();
        String preUrl = document.select("#pt_prev").attr("href");
        String muluUrl = document.select("#pt_mulu").attr("href");
        String nextUrl = document.select("#pt_next").attr("href");
        String content = document.select("#chaptercontent").html();
        String contentText = document.select("#chaptercontent").text();
        section.setContent(content);
        section.setNextUrl(nextUrl);
        section.setPreUrl(preUrl);
        section.setTitle(title);
        section.setMulu(muluUrl);
        this.lastReadUrl = url;
        return section;
    }

    /**
     * 继续阅读
     *
     * @return
     */
    public Section goOn() {
        return findSectionNovelByUrl(this.lastReadUrl);
    }
}
