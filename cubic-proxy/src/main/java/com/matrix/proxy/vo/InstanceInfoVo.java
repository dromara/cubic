package com.matrix.proxy.vo;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.util.List;

/**
 * 用于展现实例详细数据
 *
 * @ClassName InstanceInfoVo
 * @Author QIANGLU
 * @Date 2019/11/8 10:55 上午
 * @Version 1.0
 */
@Data
@Builder
public class InstanceInfoVo {


    /**
     * 应用实例唯一标识
     */
    private String appId;

    private String instanceUid;

    private String instanceName;

    private String hostname;

    private String ips;

    private String os;

    //JVM参数

    private String progress;

    private String jdkVersion;

    private String jdkDir;

    private String userDir;

    private String jvmXmx;

    private String startArgs;

    //LIB 列表

    private List<String> libs;

    /**
     * 最大最小内存
     */
    private Integer initMemory;

    /**
     * 最大最小内存
     */
    private Integer maxMemory;

    /**
     * cpu内核数量
     */
    private Integer processorNum;

    /**
     * 启动参数
     */
    private List<String> arguments;

    /**
     * 操作系统架构
     */
    private String osArch;

    /**
     * 操作系统版本
     */
    private String osVersion;

    /**
     * 宿主机Ip
     */
    private String hostIp;
    @Tolerate
    InstanceInfoVo() {
    }
}
