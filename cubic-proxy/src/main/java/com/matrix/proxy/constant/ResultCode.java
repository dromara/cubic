package com.matrix.proxy.constant;


/**
 *  <p> 响应码枚举 - 可参考HTTP状态码的语义 </p>
 *
 * @author luqiang
 */
public enum ResultCode {
    /**
     * 成功
     */
    SUCCESS( 200, "SUCCESS" ),

    /**
     * 失败
     */
    FAILURE( 400, "FAILURE" ),

    /**
     * 未登录
     */
    UN_LOGIN( 401, "NOT LOGIN" ),


    /**
     * 未认证（签名错误、token错误）
     */
    UNAUTHORIZED( 403, "未认证或Token失效" ),


    /**
     * 未通过认证
     */
    USER_UNAUTHORIZED( 402, "用户名或密码不正确" ),


    /**
     * 接口不存在
     */
    NOT_FOUND( 404, "接口不存在" ),


    /**
     * 服务器内部错误
     */
    INTERNAL_SERVER_ERROR( 500, "服务器内部错误" );

    private int code;
    private String desc;

    ResultCode(int code, String desc) {
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
