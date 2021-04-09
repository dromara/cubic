package com.matrix.proxy.server.process;


import com.cubic.proxy.common.constant.CommandCode;
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
public class ThreadDumpProcess extends DefaultMessageProcess {

    public ThreadDumpProcess() {
    }

    @Override
    public Integer code() {
        return CommandCode.THREAD_DUMP.getCode();
    }

    @Override
    public void process(ChannelHandlerContext ctx, String datagram) {
        this.ackSync(datagram);
    }

}
