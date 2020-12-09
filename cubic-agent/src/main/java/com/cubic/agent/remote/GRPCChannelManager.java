package com.cubic.agent.remote;

import com.cubic.agent.boot.CommonService;
import com.cubic.agent.boot.DefaultService;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @Description: TODO
 * @Author qinqixuan
 * @Date 2020/12/08
 * @Version V1.0
 **/
@DefaultService
public class GRPCChannelManager implements CommonService, Runnable {

	private static final Logger logger = LoggerFactory.getLogger(GRPCChannelManager.class);
	private final List<GRPCChannelListener> channelListenerList = Lists.newArrayList();

	@Override
	public void prepare() {

	}

	@Override
	public void start() {

	}

	@Override
	public void shutdown() {

	}

	@Override
	public void complete() {

	}

	public void addChannelListener(GRPCChannelListener listener){
		channelListenerList.add(listener);
	}

	@Override
	public void run() {

	}
}
