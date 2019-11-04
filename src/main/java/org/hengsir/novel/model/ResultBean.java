package org.hengsir.novel.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hengsir
 * @date 2019/11/4 3:00 下午
 */
@Data
public class ResultBean<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String SUCCESS = "00";

    public static final String FAIL = "01";

    @JSONField(name = "msg")
    private String msg = "success";

    @JSONField(name = "code")
    private String code = SUCCESS;

    @JSONField(name = "type")
    private int type;

    @JSONField(name = "data")
    private T data;

    public ResultBean() {
        super();
    }

    public ResultBean(int type,T data) {
        super();
        this.type = type;
        this.data = data;
    }

    public ResultBean(Throwable e) {
        super();
        this.msg = e.toString();
        this.code = FAIL;
    }
}