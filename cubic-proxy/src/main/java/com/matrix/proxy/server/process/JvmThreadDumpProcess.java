package com.matrix.proxy.server.process;

import com.cubic.proxy.common.constant.CommandCode;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 接受app线程栈信息并处理
 *
 * @Author qinqixuan
 * @Date 2020/12/11
 * @Version V1.0
 **/
@Slf4j
@Service
public class JvmThreadDumpProcess extends DefaultMessageProcess{

	public JvmThreadDumpProcess() {
	}

	@Override
	public Integer code() {
		return CommandCode.JVM_THREAD_DUMP.getCode();
	}

	@Override
	public void process(ChannelHandlerContext ctx, String datagram) {
//		log.info("data:{}", datagram);
		// TODO app线程栈信息持久化
	}
}
