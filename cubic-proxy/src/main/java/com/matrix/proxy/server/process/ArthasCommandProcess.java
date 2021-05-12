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
public class ArthasCommandProcess extends DefaultMessageProcess {


    public ArthasCommandProcess() {
    }

    @Override
    public Integer code() {
        return CommandCode.ARTHAS.getCode();
    }

    @Override
    public void process(ChannelHandlerContext ctx, CommonMessage data) {
        this.ackSync(data);
    }

}
