
package com.cubic.agent.utils;

import com.cubic.agent.conf.AgentConfig;
import com.google.gson.Gson;

import java.lang.management.ManagementFactory;
import java.net.*;
import java.util.*;

/**
 * 系统工具方法
 * @author luqiang
 */
public class OSUtil {

    private static volatile String OS_NAME;
    private static volatile String HOST_NAME;
    private static volatile List<String> IPV4_LIST;
    private static volatile int PROCESS_NO = 0;

    public static String getOsName() {
        if (OS_NAME == null) {
            OS_NAME = System.getProperty("os.name");
        }
        return OS_NAME;
    }

    public static String getHostName() {
        if (HOST_NAME == null) {
            try {
                InetAddress host = InetAddress.getLocalHost();
                HOST_NAME = host.getHostName();
            } catch (UnknownHostException e) {
                HOST_NAME = "unknown";
            }
        }
        return HOST_NAME;
    }

    /**
     * 获取系统环境变量分隔符
     *
     * @return
     */
    public static String getPathSeparator() {
        return System.getProperty("path.separator");
    }

    public static List<String> getAllIPV4() {
        if (IPV4_LIST == null) {
            IPV4_LIST = new LinkedList<String>();
            try {
                Enumeration<NetworkInterface> interfs = NetworkInterface.getNetworkInterfaces();
                while (interfs.hasMoreElements()) {
                    NetworkInterface networkInterface = interfs.nextElement();
                    Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                    while (inetAddresses.hasMoreElements()) {
                        InetAddress address = inetAddresses.nextElement();
                        if (address instanceof Inet4Address) {
                            String addressStr = address.getHostAddress();
                            if ("127.0.0.1".equals(addressStr)) {
                                continue;
                            }
                            IPV4_LIST.add(addressStr);
                        }
                    }
                }
            } catch (SocketException e) {

            }
        }
        return IPV4_LIST;
    }

    public static int getProcessNo() {
        if (PROCESS_NO == 0) {
            try {
                PROCESS_NO = Integer.parseInt(ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);
            } catch (Exception e) {
                PROCESS_NO = -1;
            }
        }
        return PROCESS_NO;
    }


    public static Map<String, String> buildOSInfo() {
        Map<String, String> osInfo = new HashMap<>(16);

        String osName = getOsName();
        if (osName != null) {
            osInfo.put("os_name", osName);
        }
        String hostName = getHostName();
        if (hostName != null) {
            osInfo.put("host_name", hostName);

        }
        List<String> allIPV4 = getAllIPV4();
        if (allIPV4.size() > 0) {
            for (String ipv4 : allIPV4) {
                osInfo.put("ipv4", ipv4);

            }
        }

        osInfo.put("process_no", getProcessNo() + "");

        osInfo.put("language", "java");

        osInfo.put("version", AgentConfig.Agent.VERSION);

        osInfo.put("jvm_info", new Gson().toJson(JvmInfoUtil.buildJvmInfo()));

        return osInfo;
    }
}
