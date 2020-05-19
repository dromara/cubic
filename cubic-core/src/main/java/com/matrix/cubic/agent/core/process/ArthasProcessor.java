
package com.matrix.cubic.agent.core.process;


import com.google.common.collect.ImmutableList;
import com.matrix.cubic.agent.core.ResponseCode;
import com.matrix.cubic.agent.core.common.ArthasResponseHandler;
import com.matrix.cubic.agent.core.factory.Task;
import com.matrix.cubic.agent.core.factory.TaskFactory;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.util.List;

/**
 * 使用arthas进行处理
 */
public class ArthasProcessor implements Processor<String> {

    private static final Logger log = LoggerFactory.getLogger(ArthasProcessor.class);

    private TaskFactory factory;

    public ArthasProcessor(TaskFactory factory) {
        this.factory = factory;
    }

    @Override
    public List<Integer> types() {
        return ImmutableList.of(ResponseCode.ARTHAS.getCode());
    }

    @Override
    public void process(ChannelHandlerContext ctx, String id, String command) {

        String pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
        ArthasResponseHandler handler = new ArthasResponseHandler(ctx, id);
        Task task = factory.create(id, command, pid, handler);
        if (task == null) {
            return;
        }
        task.execute();
//        Futures.addCallback(future, new FutureCallback<Integer>() {
//            @Override
//            public void onSuccess(@Nullable Integer result) {
//
//
//            }
//
//            @Override
//            public void onFailure(Throwable throwable) {
//                if (throwable instanceof CancellationException) {
//                    log.info("{} command canceled, id [{}]", factory.name(), id);
//                    return;
//                }
//
//                handler.handleError(throwable);
//                log.error(" ommand error{}", throwable);
//            }
//        }, AgentRemotingExecutor.getExecutor());
    }


}
