package com.matrix.proxy.webscoket.process;

import com.alibaba.fastjson.JSON;
import com.matrix.proxy.server.ServerConnection;
import com.matrix.proxy.server.ServerConnectionStore;
import com.matrix.proxy.webscoket.WebProcess;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName SearchConnectionProcess
 * @Author QIANGLU
 * @Date 2020/4/27 4:00 下午
 * @Version 1.0
 */
@Service
public class SearchWebProcess implements WebProcess {

    @Resource
    private ServerConnectionStore serverConnectionStore;

    @Override
    public String command() {
        return "search";
    }

    /**
     * 查询相关agentId 信息
     *
     * @param cmd
     * @return
     */
    @Override
    public String process(String cmd) {

        Map<String, ServerConnection> connectionMap = serverConnectionStore.getAgentConnection();
        List<String> rs = new ArrayList<>(connectionMap.size());
        connectionMap.entrySet().forEach(entry -> {
            if (entry.getKey().contains(cmd)) {
                rs.add(entry.getKey());
            }
        });
        return JSON.toJSONString(rs);
    }
}
