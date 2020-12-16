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
 * @Author qinqixuan
 * @Date 2020/12/16
 * @Version V1.0
 **/
@Data
@Entity
@Builder
public class ThreadDump {

	@Id
	@TableId(type = IdType.AUTO)
	private Long id;

	@TableField(value = "app_id")
	private String appId;

	@TableField(value = "instance_name")
	private String instanceName;

	@TableField(value = "instance_id")
	private String instanceId;

	@TableField(value = "thread_dump")
	private String threadDump;

	@TableField(value = "create_time")
	private Date createTime;

	@Tolerate
	public ThreadDump() {
	}
}
