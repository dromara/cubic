package com.cubic.agent.telnet;


import com.cubic.agent.remote.ResponseHandler;
import org.apache.commons.net.telnet.TelnetClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName ArthasTelnet
 * @Author QIANGLU
 * @Date 2020/4/21 10:53 上午
 * @Version 1.0
 */
public class ArthasTelnet extends Telnet {
    private static final Logger log = LoggerFactory.getLogger(ArthasTelnet.class);

    public ArthasTelnet(TelnetClient client) {
        super(client);
    }

    @Override
    public void read(String command, ResponseHandler handler) throws Exception {
        Handler hand = new Handler(command, handler);
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        Thread.sleep(200L);
        StringBuffer buf = new StringBuffer();
        int len = 0;
        long start = System.currentTimeMillis();
        while (true) {
            if (System.currentTimeMillis() - start > 5000) {
                log.info("ArthasTelnet read 超时退出");
                break;
            }
            int size = in.read(buffer);
            if (size != -1) {
                String data = new String(buffer, 0, size, charset);
                hand.handler(data);
                if (data.trim().endsWith(END_PROMPT)) {
                    break;
                }
            }
        }

    }

    private static class Handler {

        private boolean firstFlag = true;

        private final String command;

        private final ResponseHandler h;

        public Handler(String command, ResponseHandler h) {
            this.command = command;
            this.h = h;
        }

        public void handler(String data) {
            int index = data.indexOf(PROMPT);
            if (index > -1 && firstFlag) {
                doHandler(data.substring(index == 0 ? index : index - 1));
                firstFlag = false;
            } else if (!firstFlag) {
                doHandler(data);
            }
        }

        private void doHandler(String data) {
            h.handle(data);
        }

    }

}
