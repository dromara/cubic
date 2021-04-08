package com.cubic.agent.module;

import java.util.*;

/**
 * JVM info
 * @author luqiang
 */
public class JvmInfo {

    /**
     * jvm版本
     */
    private String version;

    /**
     * jvm安装目录
     */
    private String jvmHome;

    /**
     * classpath
     */
    private String classPath;

    /**
     * libraryPath
     */
    private String libraryPath;

    /**
     * 临时目录
     */
    private String tmpdir;

    /**
     * 拓展目录
     */
    private String extdirs;

    /**
     * 当前用户的名称
     */
    private String userName;

    /**
     * 用户当前的工作目录
     */
    private String userDir;

    /**
     * 执行时间
     */
    private String exceTime;

    private Set<String> jarFileList = new HashSet<String>();

    /**
     * os cpu num
     */
    private Integer processorNum;

    /**
     * os version
     */
    private String osVersion;

    /**
     * os arch
     */
    private String osArch;


    /**
     * initMemory
     */
    private Long initMemory;

    /**
     * maxMemory
     */
    private Long maxMemory;

    /**
     * 启动参数
     */
    private List<String> inputArguments;

    /**
     * 宿主机Ip
     * @return
     */
    private String hostIp;

    public String getHostIp() {
        return hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    public String getOsArch() {
        return osArch;
    }

    public void setOsArch(String osArch) {
        this.osArch = osArch;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public List<String> getInputArguments() {
        return inputArguments;
    }

    public void setInputArguments(List<String> inputArguments) {
        this.inputArguments = inputArguments;
    }

    public Long getInitMemory() {
        return initMemory;
    }

    public void setInitMemory(Long initMemory) {
        this.initMemory = initMemory;
    }

    public Long getMaxMemory() {
        return maxMemory;
    }

    public void setMaxMemory(Long maxMemory) {
        this.maxMemory = maxMemory;
    }

    // 存放可能存在jar的路径呀
    private Map<String, String> jarPathMap = new HashMap<String, String>();


    public Integer getProcessorNum() {
        return processorNum;
    }

    public void setProcessorNum(Integer processorNum) {
        this.processorNum = processorNum;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getJvmHome() {
        return jvmHome;
    }

    public void setJvmHome(String jvmHome) {
        this.jvmHome = jvmHome;
    }

    public String getClassPath() {
        return classPath;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    public String getLibraryPath() {
        return libraryPath;
    }

    public void setLibraryPath(String libraryPath) {
        this.libraryPath = libraryPath;
    }

    public String getTmpdir() {
        return tmpdir;
    }

    public void setTmpdir(String tmpdir) {
        this.tmpdir = tmpdir;
    }

    public String getExtdirs() {
        return extdirs;
    }

    public void setExtdirs(String extdirs) {
        this.extdirs = extdirs;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserDir() {
        return userDir;
    }

    public void setUserDir(String userDir) {
        this.userDir = userDir;
    }

    public String getExceTime() {
        return exceTime;
    }

    public void setExceTime(String exceTime) {
        this.exceTime = exceTime;
    }

    public Set<String> getJarFileList() {
        return jarFileList;
    }

    public void setJarFileList(Set<String> jarFileList) {
        this.jarFileList = jarFileList;
    }

    public Map<String, String> getJarPathMap() {
        return jarPathMap;
    }

    public void setJarPathMap(Map<String, String> jarPathMap) {
        this.jarPathMap = jarPathMap;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("JvmInfo{");
        sb.append("version='").append(version).append('\'');
        sb.append(", jvmHome='").append(jvmHome).append('\'');
        sb.append(", classPath='").append(classPath).append('\'');
        sb.append(", libraryPath='").append(libraryPath).append('\'');
        sb.append(", tmpdir='").append(tmpdir).append('\'');
        sb.append(", extdirs='").append(extdirs).append('\'');
        sb.append(", userName='").append(userName).append('\'');
        sb.append(", userDir='").append(userDir).append('\'');
        sb.append(", exceTime='").append(exceTime).append('\'');
        sb.append(", jarFileList=").append(jarFileList);
        sb.append(", jarPathMap=").append(jarPathMap);
        sb.append('}');
        return sb.toString();
    }
}
