package org.hengsir.simpleBlogComment.model;

/**
 * @author hengsir
 * @date 2019/10/12 10:34 上午
 */
public enum TypeEnum {
    //报文类型
    CONNECT(0),GET_COMMENTS(1),TO_COMMENTS(2),NOTIFY(3);

    TypeEnum(int type){
        this.type = type;
    }

    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
