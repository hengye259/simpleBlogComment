package org.hengsir.novel.test;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.hengsir.novel.model.Section;
import org.hengsir.novel.utils.HttpUtils;
import org.hengsir.Main;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * @author hengsir
 * @date 2019/11/4 2:00 下午
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Main.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class NovelTest {

    @Test
    public void httpTest() throws IOException {
        Document document = Jsoup.parse(HttpUtils.doGet("https://m.wujixiaoshuo.com/19_19063/18012734.html"));
        Section section = new Section();
        String title = document.select("title").text();
        String preUrl = document.select("#pt_prev").attr("href");
        String muluUrl = document.select("#pt_mulu").attr("href");
        String nextUrl = document.select("#pt_next").attr("href");
        String content = document.select("#chaptercontent").html();
        String contentText = document.select("#chaptercontent").text();
        /*System.out.println("muluUrl: "+preUrl);
        System.out.println("muluUrl: "+muluUrl);
        System.out.println("nextUrl: "+nextUrl);
        System.out.println("content: "+content);
        System.out.println("contentText: "+contentText);*/
        section.setContent(content);
        section.setNextUrl(nextUrl);
        section.setPreUrl(preUrl);
        section.setTitle(title);
        System.out.println(JSON.toJSONString(section));

    }
}
