package com.matrix.proxy.module;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * app线程池采集数据对象
 *
 * @author zhanghao
 * @date 2021/4/74:25 下午
 */
@Data
@Builder
public class ThreadPoolVo implements Serializable {

    private Long id;

    private String instanceId;

    private String instanceName;

    private String threadPoolKey;

    private String coreSize;

    private String maximumPoolSize;

    private String poolSize;

    private String activeCount;

    private String keepAliveTime;

    private String taskCount;

    private String completedTaskCount;

    private String largestPoolSize;

    @JsonFormat
    private Date createTime;

    @Tolerate
    public ThreadPoolVo() {
    }
}
