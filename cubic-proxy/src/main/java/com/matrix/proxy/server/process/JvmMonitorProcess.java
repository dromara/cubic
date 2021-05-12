package com.matrix.proxy.server.process;

import com.cubic.proxy.common.constant.CommandCode;
import com.cubic.serialization.agent.v1.CommonMessage;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @ClassName TestMessgaeProcess
 * @Author QIANGLU
 * @Date 2020/3/19 4:12 下午
 * @Version 1.0
 */
@Slf4j
@Service
public class JvmMonitorProcess extends DefaultMessageProcess {

    public JvmMonitorProcess() {
    }

    @Override
    public Integer code() {
        return CommandCode.JVM.getCode();
    }

    @Override
    public void process(ChannelHandlerContext ctx, CommonMessage datagram) {

        log.info("data:{}", datagram);
    }

}
