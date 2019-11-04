package org.hengsir.novel.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.hengsir.novel.model.RequestBean;
import org.hengsir.novel.model.ResultBean;
import org.hengsir.novel.model.Section;
import org.hengsir.novel.service.WuJiNovelService;
import org.hengsir.simpleBlogComment.model.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author hengsir
 * @date 2019/11/4 3:03 下午
 */
@Service
public class WuJiNovelController {

    @Autowired
    private WuJiNovelService wuJiNovelService;

    public ResultBean readPre(String url) {
        return new ResultBean<Section>(10,wuJiNovelService.choosePre(url));
    }

    public ResultBean readNext(String url) {
        return new ResultBean<Section>(10,wuJiNovelService.choosePre(url));
    }

    public ResultBean readGoOn() {
        return new ResultBean<Section>(10,wuJiNovelService.goOn());
    }

    public ResultBean readNewSection(String url) {
        if (url != null && url.length() > 0) {
            return new ResultBean<Section>(10,wuJiNovelService.findSectionNovelByUrl(url));
        }
        return null;
    }

    public ResultBean parse(Request req) {
        String method = req.getMethod();
        String url = req.getUrl();
        switch (method) {
            case "readNewSection":
                return readNewSection(url);
            case "readGoOn":
                return readGoOn();
            case "readNext":
                return readNext(url);
            case "readPre":
                return readPre(url);
            default:
                break;
        }
            return null;
    }


}
