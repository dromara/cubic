package com.matrix.proxy.server.process;

import com.alibaba.fastjson.JSON;
import com.matrix.proxy.module.Command;
import com.matrix.proxy.session.Session;
import com.matrix.proxy.session.SessionManager;
import com.matrix.proxy.util.ResponseCode;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ClassName TestMessgaeProcess
 * @Author QIANGLU
 * @Date 2020/3/19 4:12 下午
 * @Version 1.0
 */
@Slf4j
@Service
public class ArthasCommandProcess extends DefaultMessageProcess {

    @Resource
    private SessionManager sessionManager;

    @Override
    public Integer code() {
        return ResponseCode.ARTHAS.getCode();
    }

    @Override
    public void process(ChannelHandlerContext ctx, String data) {
        Command command = JSON.parseObject(data, Command.class);

        Session session = sessionManager.getSession(command.getId());
        if (session == null) {
            log.warn("receive message ,can not get websocket session ");
            this.ackSync(data);
            return;
        }

        log.info("接收到 arthas 返回数据，id:{}", command.getId());

        if (StringUtils.isNotEmpty(command.getBody())) {
            session.writeToWeb(command.getBody());
        }
    }

}
