package com.matrix.proxy.server.process;

import com.alibaba.fastjson.JSON;
import com.cubic.proxy.common.constant.CommandCode;
import com.cubic.proxy.common.server.ServerConnectionStore;
import com.cubic.serialization.agent.v1.CommonMessage;
import com.cubic.serialization.agent.v1.JVMThreadMetric;
import com.google.protobuf.InvalidProtocolBufferException;
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
	public void process(ChannelHandlerContext ctx, CommonMessage message) {
		JVMThreadMetric threadMetric;
		try {
			threadMetric = JVMThreadMetric.parseFrom(message.getMsgContent());
		} catch (InvalidProtocolBufferException e) {
			logger.error("CommonMessage 反序列化失败：", e);
			return;
		}
		String appId = message.getInstanceName() + "_" + message.getInstanceUuid();
		connectionStore.register(appId, ctx.channel());
		// 数据持久化
		insertThreadInfo(threadMetric, appId);
		logger.debug("保存成功！实例id ：{} ,channel :{}", appId, ctx.channel());
	}

	private void insertThreadInfo(JVMThreadMetric threadMetric, String appId) {
		ThreadDump threadDump = ThreadDump.builder()
				.appId(appId)
				.instanceId(threadMetric.getInstanceUUID())
				.instanceName(threadMetric.getServiceName())
				.threadDump(GzipUtils.compress(threadMetric.getThreadDump()))
				.createTime(new Date())
				.build();
		threadDumpMapper.insert(threadDump);
	}
}
