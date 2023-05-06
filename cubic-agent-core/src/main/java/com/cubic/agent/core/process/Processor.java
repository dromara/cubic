
package com.cubic.agent.core.process;


import io.netty.channel.ChannelHandlerContext;

import java.util.List;

/**
 * @author luqiang
 */
public interface Processor {

    List<Integer> types();

    void process(ChannelHandlerContext ctx, String id, String command) throws ClassNotFoundException;
}
