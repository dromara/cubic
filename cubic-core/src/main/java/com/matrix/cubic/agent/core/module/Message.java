package com.matrix.cubic.agent.core.module;


/**
 * @ClassName Message
 * @Author QIANGLU
 * @Date 2020/3/23 6:24 下午
 * @Version 1.0
 */
public class Message {

    private String id;

    private Integer code;

    private String body;

    private String command;

//    private String instanceUuid = ServiceAndEndpointRegisterClient.getInstanceUuid();
//    private String instanceName = ServiceAndEndpointRegisterClient.getInstanceName();
//    private String instanceVersion = ServiceAndEndpointRegisterClient.getInstanceVersion();

    private String instanceUuid;
    private String instanceName;
    private String instanceVersion;

    public Message(Integer code, String body, String id) {
        this.id = id;
        this.code = code;
        this.body = body;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getInstanceUuid() {
        return instanceUuid;
    }

    public void setInstanceUuid(String instanceUuid) {
        this.instanceUuid = instanceUuid;
    }


    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public String getInstanceVersion() {
        return instanceVersion;
    }

    public void setInstanceVersion(String instanceVersion) {
        this.instanceVersion = instanceVersion;
    }
}
