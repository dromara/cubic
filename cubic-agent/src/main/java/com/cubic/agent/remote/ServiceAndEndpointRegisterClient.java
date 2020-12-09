package com.cubic.agent.remote;

import com.cubic.agent.boot.CommonService;
import com.cubic.agent.boot.DefaultNamedThreadFactory;
import com.cubic.agent.boot.DefaultService;
import com.cubic.agent.boot.ServiceManager;
import com.cubic.agent.conf.AgentConfig;
import com.cubic.agent.jvm.thread.JVMThreadService;
import com.cubic.agent.module.KeyStringValuePair;
import com.cubic.proxy.common.thread.RunnableWithExceptionProtection;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @Description: TODO
 * @Author qinqixuan
 * @Date 2020/12/08
 * @Version V1.0
 **/
@DefaultService
public class ServiceAndEndpointRegisterClient implements CommonService, Runnable, AgentChannelListener{

	private static final Logger logger = LoggerFactory.getLogger(ServiceAndEndpointRegisterClient.class);

	private static String INSTANCE_UUID;
	private static String INSTANCE_NAME;
	private static String INSTANCE_VERSION;
	private static List<KeyStringValuePair> SERVICE_INSTANCE_PROPERTIES;

	private volatile ScheduledFuture<?> applicationRegisterFuture;

	@Override
	public void prepare() {
		ServiceManager.INSTANCE.findService(AgentClientService.class).addListener(this);
		INSTANCE_UUID = StringUtils.isBlank(AgentConfig.Agent.INSTANCE_UUID)
				? UUID.randomUUID().toString().replaceAll("-", "")
				: AgentConfig.Agent.INSTANCE_UUID;
		INSTANCE_NAME = AgentConfig.Agent.SERVICE_NAME;
		INSTANCE_VERSION = AgentConfig.Agent.VERSION;
		for (String key : AgentConfig.Agent.INSTANCE_PROPERTIES.keySet()) {
			SERVICE_INSTANCE_PROPERTIES.add(
					KeyStringValuePair.newBuilder()
							.setKey(key)
							.setValue(AgentConfig.Agent.INSTANCE_PROPERTIES.get(key))
							.build());
		}
	}

	@Override
	public void start() {
		applicationRegisterFuture = Executors.newSingleThreadScheduledExecutor(
				new DefaultNamedThreadFactory("ServiceAndEndpointRegisterClient"))
		.scheduleAtFixedRate(new RunnableWithExceptionProtection(this, t -> logger.error("unexpected exception.", t)),
				0, AgentConfig.Collector.APP_AND_SERVICE_REGISTER_CHECK_INTERVAL, TimeUnit.SECONDS);
	}

	@Override
	public void shutdown() {
		applicationRegisterFuture.cancel(true);
	}

	@Override
	public void complete() {

	}

	@Override
	public void statusChanged(ChannelStatus status) {

	}

	@Override
	public void run() {

	}

	public static String getInstanceUuid() {
		return INSTANCE_UUID;
	}

	public static String getInstanceName() {
		return INSTANCE_NAME;
	}

	public static String getInstanceVersion() {
		return INSTANCE_VERSION;
	}
}
