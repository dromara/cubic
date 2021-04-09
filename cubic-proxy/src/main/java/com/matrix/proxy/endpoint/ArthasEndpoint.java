package com.matrix.proxy.endpoint;

import com.cubic.proxy.websocket.MatrixNettyWebServer;
import com.matrix.proxy.config.ServerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;

import java.util.HashMap;
import java.util.Map;

@Endpoint(id = "arthas")
public class ArthasEndpoint {

    @Autowired
    ServerProperties serverProperties;
    @Autowired
    MatrixNettyWebServer matrixNettyWebServer;

    @ReadOperation
    public Map<String, Object> invoke() {
        Map<String, Object> result = new HashMap<String, Object>(16);

        result.put("version", this.getClass().getPackage().getImplementationVersion());
        result.put("properties", serverProperties);


        return result;
    }

}
