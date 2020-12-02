
package com.cubic.agent.jstack;


import com.google.common.base.Charsets;
import com.google.common.io.ByteStreams;
import com.cubic.agent.utils.VmUtils;
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
public class CommandExecutor implements PidExecutor {

    private static final Logger log = LoggerFactory.getLogger(CommandExecutor.class);

    @Override
    public String execute(String pid, String command) {


        VirtualMachine virtualMachine = null;
        InputStream inputStream = null;

        try {
            virtualMachine = VmUtils.getVirtualMachine(pid);
            HotSpotVirtualMachine hotSpotVirtualMachine = (HotSpotVirtualMachine) virtualMachine;
            switch (command) {
                //执行jcmd命令，具体能干啥见jcmd $PID help
                case "jcmd":
                    inputStream = hotSpotVirtualMachine.executeJCmd(command);
                    break;
                case "printflag":
//                    inputStream = hotSpotVirtualMachine.printFlag(cmd);
                    break;
                //jmap -histo效果
                case "inspectheap":
//                    inputStream = hotSpotVirtualMachine.heapHisto("-live");
                    break;
                //jstack效果
                case "threadDump":
                    inputStream = hotSpotVirtualMachine.remoteDataDump(new String[0]);
                    break;
                default:
                    break;

            }
            return inputStream == null ? "Command unsupported" : readVmOutput(inputStream);
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


    private String readVmOutput(InputStream inputStream) throws IOException {
        try {
            byte[] bytes = ByteStreams.toByteArray(inputStream);
            return new String(bytes, Charsets.UTF_8);
        } catch (Exception e) {
            log.error("readVmOutput  error pid:", e);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return "";
    }
}
