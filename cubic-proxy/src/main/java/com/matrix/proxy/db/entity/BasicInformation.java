package com.matrix.proxy.db.entity;

import lombok.*;
import lombok.experimental.Tolerate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
public class BasicInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /**
     * appId
     */
    private String appId;

    /**
     * 实例名称
     */
    private String instanceName;

    /**
     * 实例id
     */
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
    private Date lastHeartbeat;

    /**
     * 启动时间
     */
    private Date startDate;

    @Tolerate
    public BasicInformation() {
    }


}
