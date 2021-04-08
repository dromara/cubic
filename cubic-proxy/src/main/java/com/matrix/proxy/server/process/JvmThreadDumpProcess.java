package com.matrix.proxy.server.process;

import com.alibaba.fastjson.JSON;
import com.cubic.proxy.common.constant.CommandCode;
import com.cubic.proxy.common.module.ThreadMetricCollection.Builder;
import com.cubic.proxy.common.server.ServerConnectionStore;
import com.matrix.proxy.entity.ThreadDump;
import com.matrix.proxy.mapper.ThreadDumpMapper;
import com.matrix.proxy.util.GzipUtils;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 接受app线程栈信息并处理
 *
 * @Author qinqixuan
 * @Date 2020/12/11
 * @Version V1.0
 **/
@Service
public class JvmThreadDumpProcess extends DefaultMessageProcess{

	private static final Logger logger = LoggerFactory.getLogger(JvmThreadDumpProcess.class);

	@Resource
	private ServerConnectionStore connectionStore;
	@Resource
	private ThreadDumpMapper threadDumpMapper;

	public JvmThreadDumpProcess() {
	}

	@Override
	public Integer code() {
		return CommandCode.JVM_THREAD_DUMP.getCode();
	}

	@Override
	public void process(ChannelHandlerContext ctx, String message) {
		Builder threadBuild = JSON.parseObject(JSON.parseObject(message).getString("builder"), Builder.class);
		String appId = threadBuild.getServiceName() + "_" + threadBuild.getInstanceUUID();
		connectionStore.register(appId, ctx.channel());
		// 数据持久化
		insertThreadInfo(threadBuild, appId);
		logger.debug("保存成功！实例id ：{} ,channel :{}", appId, ctx.channel());
		ctx.channel().writeAndFlush(initRegisterResponse(appId));
	}

	private void insertThreadInfo(Builder threadBuild, String appId) {
		ThreadDump threadDump = ThreadDump.builder()
				.appId(appId)
				.instanceId(threadBuild.getInstanceUUID())
				.instanceName(threadBuild.getServiceName())
				.threadDump(GzipUtils.compress(threadBuild.getThreadDump()))
				.createTime(new Date())
				.build();
		threadDumpMapper.insert(threadDump);
	}

	private String initRegisterResponse(String id) {
		Map<String, Object> result = new HashMap<>(8);
		result.put("code", CommandCode.JVM_THREAD_DUMP.getCode());
		result.put("id", id);
		result.put("command", true);
		return JSON.toJSONString(result);
	}
}
