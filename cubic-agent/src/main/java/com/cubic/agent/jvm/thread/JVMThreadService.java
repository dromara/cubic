package com.cubic.agent.jvm.thread;

import com.cubic.agent.boot.CommonService;
import com.cubic.agent.boot.DefaultNamedThreadFactory;
import com.cubic.agent.boot.DefaultService;
import com.cubic.agent.boot.ServiceManager;
import com.cubic.agent.conf.RemoteDownstreamConfig;
import com.cubic.agent.dictionary.DictionaryUtil;
import com.cubic.agent.module.ThreadMetricCollection;
import com.cubic.agent.remote.*;
import com.cubic.proxy.common.thread.RunnableWithExceptionProtection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @Description: jvm线程栈采集
 * @Author qinqixuan
 * @Date 2020/12/08
 * @Version V1.0
 **/
@DefaultService
public class JVMThreadService implements CommonService {

	private static final Logger logger = LoggerFactory.getLogger(JVMThreadService.class);

	private volatile ScheduledFuture<?> sendMetricFuture;
	private Sender sender;

	@Override
	public void prepare() {
		sender = new Sender();
		ServiceManager.INSTANCE.findService(AgentClientService.class).addListener(sender);
	}

	@Override
	public void start() {
		sendMetricFuture = Executors
				.newSingleThreadScheduledExecutor(new DefaultNamedThreadFactory("JVMService-consume"))
				.scheduleAtFixedRate(new RunnableWithExceptionProtection(sender, th -> {
					logger.error("JVMService consumes and upload failure.", th);
				}), 0, 1, TimeUnit.MINUTES);
	}

	@Override
	public void shutdown() {
		sendMetricFuture.cancel(true);
	}

	@Override
	public void complete() {

	}

	class Sender implements Runnable, AgentChannelListener {
		private volatile ChannelStatus status = ChannelStatus.DISCONNECT;

		@Override
		public void run() {
			if (RemoteDownstreamConfig.Agent.SERVICE_ID != DictionaryUtil.nullValue()
				&& RemoteDownstreamConfig.Agent.SERVICE_INSTANCE_ID != DictionaryUtil.nullValue()) {
				if (status == ChannelStatus.CONNECTION) {
					try {
						long currentTimeMillis = System.currentTimeMillis();
						ThreadMetricCollection.Builder builder = new ThreadMetricCollection.Builder();
						builder.setThreadDump(ThreadProvider.INSTANCE.getThreadDump());
						builder.setAllThreadPools(ThreadPoolProvider.INSTANCE.getDubboThreadPool());
						builder.setServiceInstanceId(RemoteDownstreamConfig.Agent.SERVICE_INSTANCE_ID);
						builder.setTime(currentTimeMillis);
						builder.setInstanceUUID(ServiceAndEndpointRegisterClient.getInstanceUuid());
						builder.build();

					} catch (Throwable t) {
						logger.error("send JVM thread metrics to Collector fail.", t);
					}
				}
			}
		}

		@Override
		public void statusChanged(ChannelStatus status) {
			if (ChannelStatus.CONNECTION.equals(status)) {
//				Channel channel = ServiceManager.INSTANCE.findService(GRPCChannelManager.class).getChannel();
//				stub = ThreadMetricReportServiceGrpc.newBlockingStub(channel);
				logger.info("jvm info connection.");
			}
			this.status = status;
		}
	}
}
