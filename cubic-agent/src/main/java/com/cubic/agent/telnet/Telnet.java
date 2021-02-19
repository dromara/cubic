package com.cubic.agent.telnet;


import com.google.common.base.Charsets;
import com.cubic.agent.remote.ResponseHandler;
import org.apache.commons.net.telnet.TelnetClient;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

/**
 * @ClassName Telnet
 * @Author QIANGLU
 * @Date 2020/4/21 10:48 上午
 * @Version 1.0
 */
public abstract class Telnet {
    protected static final int DEFAULT_BUFFER_SIZE = 4 * 1024;

    protected static final Charset charset = Charsets.UTF_8;

    protected static final String PROMPT = "[arthas@";
    protected static final String END_PROMPT = "$";

    protected final InputStream in;

    private final BufferedWriter out;

    private final TelnetClient client;


    public Telnet(TelnetClient client) {
        this.client = client;
        this.in = client.getInputStream();
        this.out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream(), charset));
    }

    public void write(String command) throws Exception {
        out.write(command);
        out.newLine();
        out.flush();
    }

    public abstract void read(String command, ResponseHandler handler) throws Exception;


    public void close() {
        try {
            client.disconnect();
        } catch (Exception e) {
            // TODO
        }
    }

    public boolean sendAYT() throws IOException, InterruptedException {
        return client.sendAYT(500);
    }


}
