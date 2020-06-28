
package com.matrix.cubic.agent.core.process;


import com.google.common.collect.ImmutableList;
import com.matrix.cubic.agent.core.remote.CommandCode;
import com.matrix.cubic.agent.core.remote.ServiceRegisterClient;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 处理心跳数据
 *
 * @author luqiang
 */
public class RegisterProcessor implements Processor<String> {

    private static final Logger log = LoggerFactory.getLogger(RegisterProcessor.class);

    @Override
    public List<Integer> types() {
        return ImmutableList.of(CommandCode.REGIST.getCode());
    }

    @Override
    public void process(ChannelHandlerContext ctx, String id, String command) {
        if (StringUtils.isNotEmpty(command)) {
            ServiceRegisterClient.registed = Boolean.valueOf(command);
            log.info("Register Succ id:{}", id);
        }
    }
}
