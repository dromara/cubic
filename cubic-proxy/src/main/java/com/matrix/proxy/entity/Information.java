package com.matrix.proxy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * 应用注册信息
 *
 * @ClassName BasicInformation
 * @Author QIANGLU
 * @Date 2020/6/10 11:10 上午
 * @Version 1.0
 */
@Entity
@Data
@Builder
public class Information {

    @Id
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * appId
     */
    @TableField(value = "app_id")
    private String appId;

    /**
     * 实例名称
     */
    @TableField(value = "instance_name")
    private String instanceName;

    /**
     * 实例id
     */
    @TableField(value = "instance_id")
    private String instanceId;

    /**
     * 应用ip
     */
    private String ip;

    /**
     * 应用progress
     */
    private String progress;

    /**
     * 应用host
     */
    private String host;

    /**
     * 应用部署系统
     */
    private String os;

    /**
     * language
     */
    private String language;

    /**
     * agent version
     */
    private String version;

    /**
     * 最后心跳
     */
    @TableField(value = "last_heartbeat")
    private Date lastHeartbeat;

    /**
     * 启动时间
     */
    @TableField(value = "start_date")
    private Date startDate;

    @TableField(value = "jdk_version")
    private String jdkVersion;

    @TableField(value = "jdk_dir")
    private String jdkDir;

    @TableField(value = "user_dir")
    private String userDir;

    @TableField(value = "init_memory")
    private Integer initMemory;

    @TableField(value = "max_memory")
    private Integer maxMemory;


    @TableField(value = "processor_num")
    private Integer processorNum;

    @TableField(value = "arguments")
    private String arguments;

    @TableField(value = "os_version")
    private String osVersion;

    @TableField(value = "jars")
    private String jars;

    @Tolerate
    public Information() {
    }


}
