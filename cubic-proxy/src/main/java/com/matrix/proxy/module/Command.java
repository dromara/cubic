package com.matrix.proxy.module;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

/**
 * @ClassName Command
 * @Author QIANGLU
 * @Date 2020/3/23 4:43 下午
 * @Version 1.0
 */
@Data
@Builder
public class Command {

    private String id;

    private String instanceUuid;

    private String instanceName ;

    private String instanceVersion;

    private String command;

    private Integer code;

    private String body;

    @Tolerate
    Command(){}

}
