
package com.cubic.agent.core.process;


import com.cubic.agent.core.boot.DefaultProcess;
import com.cubic.agent.core.remote.CommandCode;
import com.google.common.collect.ImmutableList;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 处理心跳数据
 * @author luqiang
 */
@DefaultProcess
public class HeartbeatProcessor implements Processor  {

    private static final Logger log = LoggerFactory.getLogger(HeartbeatProcessor.class);

    @Override
    public List<Integer> types() {
        return ImmutableList.of(CommandCode.HEARTBEAT.getCode());
    }

    @Override
    public void process(ChannelHandlerContext ctx, String id, String command) {
        log.debug("receive heartbeat response, id {}", id);


    }
}
