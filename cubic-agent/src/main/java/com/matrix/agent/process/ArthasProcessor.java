
package com.matrix.agent.process;


import com.google.common.collect.ImmutableList;
import com.matrix.agent.ResponseCode;
import com.matrix.agent.common.ArthasResponseHandler;
import com.matrix.agent.factory.Task;
import com.matrix.agent.factory.TaskFactory;
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
