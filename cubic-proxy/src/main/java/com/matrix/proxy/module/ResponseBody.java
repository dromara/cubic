package com.matrix.proxy.module;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName ResponseBody
 * @Author QIANGLU
 * @Date 2020/6/12 4:16 下午
 * @Version 1.0
 */
public class ResponseBody<T> implements Serializable {

    private Integer code;

    private T data;

    public  ResponseBody(Integer code,T data){
        this.code = code;
        this.data = data;

    }

    public  ResponseBody(){

    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
