package com.matrix.proxy.server.process;

import com.matrix.proxy.util.ResponseCode;
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
public class CommandProcess extends DefaultMessageProcess {


    public CommandProcess() {
    }

    @Override
    public Integer code() {
        return ResponseCode.COMMAND.getCode();
    }

    @Override
    public void process(ChannelHandlerContext ctx, String data) {
        boolean rs = this.ackSync(data);
    }

}
