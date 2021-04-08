package com.cubic.proxy.common.module;

import java.io.Serializable;

/**
 * 接口响应体
 *
 * @Author qinqixuan
 * @Date 2021/04/06
 * @Version V1.0
 **/
public class Response<T> implements Serializable {

	private Integer code;
	private String msg;
	private T data;

	public Response() {}

	public Response(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public Integer getCode() {
		return this.code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return this.data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Response{code=" + this.code + ", msg='" + this.msg + ", data=" + this.data + '}';
	}
}
