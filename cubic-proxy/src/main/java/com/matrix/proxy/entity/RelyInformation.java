package com.matrix.proxy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * 依赖数据
 * @ClassName RelyInformation
 * @Author QIANGLU
 * @Date 2019/11/14 9:38 上午
 * @Version 1.0
 */
@Data
@Builder
@Entity
@TableName("cubic_rely_information")
public class RelyInformation {

    @Id
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 实例唯一标识
     */
    @TableField(value = "app_id")
    private String appId;


    /**
     * 应用名称
     */
    @TableField(value = "service_name")
    private String serviceName;


    /**
     * jar name
     */
    @TableField(value = "jar_name")
    private String jarName;

    @TableField(value = "create_date")
    private Date createDate;


    @Tolerate
    public RelyInformation(){}


}
