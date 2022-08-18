package com.cubic.proxy.common.module;


import com.cubic.proxy.common.enums.ResultCode;

/**
 * @author luqiang
 */
public class DataResult {

    /**
     * 消息内容
     */
    private String message;

    /**
     * 响应码
     */
    private Integer code;

    /**
     * 响应中的数据
     */
    private Object data;

    /***
     * 过期
     *
     * @param message:
     */
    public static DataResult expired(String message) {
        return new DataResult(ResultCode.UN_LOGIN.getCode(), message, null);
    }

    public static DataResult fail(String message) {
        return new DataResult(ResultCode.FAILURE.getCode(), message, null);
    }

    /***
     * 自定义错误返回码
     *
     */
    public static DataResult fail(Integer code, String message) {
        return new DataResult(code, message, null);
    }

    public static DataResult success(String message) {
        return new DataResult(ResultCode.SUCCESS.getCode(), message, null);
    }

    public static DataResult success() {
        return new DataResult(ResultCode.SUCCESS.getCode(), "OK", null);
    }

    public static DataResult build(Integer code, String msg, Object data) {
        return new DataResult(ResultCode.SUCCESS.getCode(), msg, data);
    }

    public static DataResult success(String message, Object data) {
        return new DataResult(ResultCode.SUCCESS.getCode(), message, data);
    }

    /**
     * 自定义返回码
     */
    public static DataResult success(Integer code, String message) {
        return new DataResult(code, message);
    }

    /**
     * 自定义
     *
     * @param code：验证码
     * @param message：返回消息内容
     * @param data：返回数据
     */
    public static DataResult success(Integer code, String message, Object data) {
        return new DataResult(code, message, data);
    }

    public DataResult() { }

    public static DataResult build(Integer code, String msg) {
        return new DataResult(code, msg, null);
    }

    public DataResult(Integer code, String msg, Object data) {
        this.code = code;
        this.message = msg;
        this.data = data;
    }

    public DataResult(Object data) {
        this.code = ResultCode.SUCCESS.getCode();
        this.message = "OK";
        this.data = data;
    }

    public DataResult(String message) {
        this(ResultCode.SUCCESS.getCode(), message, null);
    }

    public DataResult(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    public DataResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
