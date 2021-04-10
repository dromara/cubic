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
 * app线程池采集数据对象
 *
 * @author zhanghao
 * @date 2021/4/74:25 下午
 */
@Data
@Entity
@Builder
@TableName("thread_pool")
public class ThreadPoolEntity {
    @Id
    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField(value = "instance_id")
    private String instanceId;

    @TableField(value = "instance_name")
    private String instanceName;

    @TableField(value = "thread_pool_key")
    private String threadPoolKey;

    @TableField(value = "thread_pool_params")
    private String threadPoolParams;

    @TableField(value = "create_time")
    private Date createTime;

    @Tolerate
    public ThreadPoolEntity() {
    }
}
