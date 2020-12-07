package com.cubic.proxy.common.module;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.util.Map;

/**
 * @ClassName Message
 * @Author QIANGLU
 * @Date 2020/3/23 6:24 下午
 * @Version 1.0
 */
@Data
@Builder
public class Message {

    private String id;

    private Integer code;

    private String body;

    private String command;

    private String instanceUuid ;

    private String instanceName ;

    private String instanceVersion ;

    private Map<String,String> osInfo;

    @Tolerate
    public Message(){}
}
