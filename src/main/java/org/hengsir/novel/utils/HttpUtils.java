package org.hengsir.novel.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * @author hengsir
 * @date 2019/11/4 1:53 下午
 */
public class HttpUtils {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    /**
     * GET请求。
     *
     * @param url         地址
     * @return 结果页面html
     * @throws IOException 异常
     */
    public static String doGet(String url) throws IOException {
        logger.info("请求地址：{}", url);
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Content-Type", "text/html");
        httpGet.setURI(URI.create(url));
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse response = httpClient.execute(httpGet);
        String responseMessage = EntityUtils.toString(response.getEntity(),"utf-8");
        logger.info("响应结果：{}", responseMessage);
        return responseMessage;
    }
}
