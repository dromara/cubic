package com.cubic.proxy.common.enums;


/**
 * <p> 公共状态码</p>
 *
 * @author luqiang
 */
public enum StatusEnum {
    /**
     * 是
     */
    YES(1, "YES"),

    /**
     * 否
     */
    NO(0, "NO");


    private int code;
    private String desc;

    StatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
