package com.cubic.agent.utils;


import com.cubic.agent.boot.ServiceManager;
import com.cubic.agent.module.JvmInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;
import java.lang.management.OperatingSystemMXBean;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author luqiang
 */
public class JvmInfoUtil {

    private static final Logger logger = LoggerFactory.getLogger(JvmInfoUtil.class);

    public static JvmInfo buildJvmInfo() {
        JvmInfo jvmInfo = new JvmInfo();
        initJvmInfo(jvmInfo);
        loadJarPath(jvmInfo);
        getJarFileList(jvmInfo);
        return jvmInfo;
    }


    private static void initJvmInfo(JvmInfo jvmInfo) {
        //获取整个虚拟机内存使用情况
        MemoryUsage heapMemoryUsage = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
        jvmInfo.setInitMemory(heapMemoryUsage.getInit());
        jvmInfo.setMaxMemory(heapMemoryUsage.getMax());


        //获取启动参数
        List<String> inputArguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
        jvmInfo.setInputArguments(inputArguments);
        //系统参数
        OperatingSystemMXBean osm = ManagementFactory.getOperatingSystemMXBean();
        jvmInfo.setOsArch(osm.getArch());
        jvmInfo.setOsVersion(osm.getVersion());
        jvmInfo.setProcessorNum(osm.getAvailableProcessors());
        jvmInfo.setVersion(System.getProperty("java.version"));
        jvmInfo.setHostIp(System.getProperty("HOST_IP"));
        jvmInfo.setJvmHome(System.getProperty("java.home"));
        jvmInfo.setLibraryPath(System.getProperty("java.library.path"));
        jvmInfo.setClassPath(System.getProperty("java.class.path"));
        jvmInfo.setTmpdir(System.getProperty("java.io.tmpdir"));
        jvmInfo.setExtdirs(System.getProperty("java.ext.dirs"));
        jvmInfo.setUserName(System.getProperty("user.name"));
        jvmInfo.setUserDir(System.getProperty("user.dir"));
        jvmInfo.setExceTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }

    private static void loadJarPath(JvmInfo jvmInfo) {
        Instrumentation instrumentation = ServiceManager.instrumentation;

        Class[] clzzs = instrumentation.getAllLoadedClasses();
        Set<ClassLoader> classLoaders = new HashSet<ClassLoader>();
        for (int i = 0; i < clzzs.length; i++) {
            ClassLoader classLoader = clzzs[i].getClassLoader();
            if (classLoader != null) {
                classLoaders.add(clzzs[i].getClassLoader());
            }
        }

        for (ClassLoader classLoader : classLoaders) {
            classResCalc(classLoader.getClass(), jvmInfo);
        }
    }

    private static void classResCalc(Class c, JvmInfo jvmInfo) {
        try {
            java.net.URL url = c.getResource("/");
            if (null != url) {
                jvmInfo.getJarPathMap().put(url.toString(), "");
            }
        } catch (Exception e) {
            logger.warn(e.getMessage());
        }
    }

    private static void getJarFileList(JvmInfo jvmInfo) {
        // 处理classpath路径上加载的 jar
        String[] classPathJar = jvmInfo.getClassPath().split(OSUtil.getPathSeparator());
        for (String s : classPathJar) {
            if (!s.endsWith(".jar")) {
                continue;
            }
            String path = new File(s).getAbsolutePath();
            logger.debug("find jar in classpath: {}", path);
            jvmInfo.getJarFileList().add(path);
        }
        // 处理文件方式加载的
        for (String s : jvmInfo.getJarPathMap().keySet()) {
            s = s.replace("file:", "");
            // 处理tomcat类型的 jar
            if (s.endsWith("WEB-INF/classes/")) {
                s = s.replace("WEB-INF/classes/", "WEB-INF/lib/");
            }
            File tempDir = new File(s);
            if (!tempDir.exists() || !tempDir.isDirectory()) {
                continue;
            }
            for (File file : tempDir.listFiles()) {
                if (!file.getName().endsWith(".jar")) {
                    continue;
                }
                logger.debug("find jar in WEB-INF/lib: {}", file.getAbsolutePath());
                jvmInfo.getJarFileList().add(file.getAbsolutePath());
            }
        }
        // 加载SpringBoot类型的
        loadSpringBootLib(jvmInfo);

    }

    private static void loadSpringBootLib(JvmInfo jvmInfo) {
        List<String> springBootJars = new ArrayList<String>();
        for (String s : jvmInfo.getJarFileList()) {
            JarFile jar = null;
            try {
                logger.debug("springboot judge, load jar: {}", s);
                jar = new JarFile(s);
                //SpringBoot 启动类标识
                if (jar.getManifest() == null || !"org.springframework.boot.loader.JarLauncher".equals(jar.getManifest().getMainAttributes().getValue("Main-Class"))) {
                    continue;
                }
                Enumeration<JarEntry> jarEntry = jar.entries();
                String jarPath = "";
                while (jarEntry.hasMoreElements()) {
                    jarPath = jarEntry.nextElement().getName();
                    if (jarPath.endsWith(".jar")) {
                        springBootJars.add(s + "!/" + jarPath);
                    }
                }

            } catch (Exception e) {
                logger.warn("error msg: {}", e.getMessage());
            } finally {
                try {
                    if (jar != null) {
                        jar.close();
                    }
                } catch (IOException e) {
                    logger.warn("error msg: {}", e.getMessage());
                }
            }
        }
        if (springBootJars.size() > 0) {
            logger.debug("finded springboot jar path: {}", springBootJars);
            jvmInfo.getJarFileList().addAll(springBootJars);
        }
    }

}
