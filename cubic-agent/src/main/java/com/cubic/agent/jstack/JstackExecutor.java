
package com.cubic.agent.jstack;


import com.cubic.agent.utils.VmUtils;
import com.google.common.base.Charsets;
import com.google.common.io.ByteStreams;
import com.sun.tools.attach.VirtualMachine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.tools.attach.HotSpotVirtualMachine;

import java.io.IOException;
import java.io.InputStream;

/**
 * 执行jstack 命令
 *
 * @author luqiang
 */
public class JstackExecutor implements PidExecutor {

    private static final Logger log = LoggerFactory.getLogger(JstackExecutor.class);

    @Override
    public String execute(String pid,String command) {
        VirtualMachine virtualMachine = null;
        try {
            virtualMachine = VmUtils.getVirtualMachine(pid);
            HotSpotVirtualMachine hotSpotVirtualMachine = (HotSpotVirtualMachine) virtualMachine;
            return readJStackOutput(hotSpotVirtualMachine);
        } catch (Exception e) {
            log.error("run JStackPidExecutor error pid:" + pid, e);
        } finally {
            if (virtualMachine != null) {
                try {
                    virtualMachine.detach();
                } catch (IOException e) {
                    log.error("virtualMachine detach error pid:" + pid, e);
                }
            }

        }
        return "";
    }

    private String readJStackOutput(HotSpotVirtualMachine hotSpotVirtualMachine) throws IOException {
        InputStream inputStream = null;
        try {
            inputStream = hotSpotVirtualMachine.remoteDataDump(new String[0]);
            byte[] bytes = ByteStreams.toByteArray(inputStream);
            return new String(bytes, Charsets.UTF_8);
        } catch (Exception e) {
            log.error("readJStackOutput  error pid:", e);

        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return "";
    }
}
