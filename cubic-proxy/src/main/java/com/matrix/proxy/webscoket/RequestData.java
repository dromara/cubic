package com.matrix.proxy.webscoket;

import lombok.Builder;
import lombok.Data;

/**
 * @ClassName RequestData
 * @Author QIANGLU
 * @Date 2020/4/6 1:59 下午
 * @Version 1.0
 */
@Data
@Builder
public class RequestData<T> {

    private int type;

    private T command;

    private String token;

    private String user;

}
