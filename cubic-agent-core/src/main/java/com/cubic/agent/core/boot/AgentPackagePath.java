package com.cubic.agent.core.boot;

import com.cubic.agent.core.conf.AgentConfig;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @ClassName AgentPackage
 * @Author QIANGLU
 * @Date 2020/5/19 10:21 上午
 * @Version 1.0
 */
public class AgentPackagePath {
    private static final Logger logger = LoggerFactory.getLogger(AgentPackagePath.class);

    private static File AGENT_PACKAGE_PATH;

    public static File getPath() throws AgentPackageNotFoundException {

        if (AGENT_PACKAGE_PATH == null) {
            AGENT_PACKAGE_PATH = findPath();
        }
        return AGENT_PACKAGE_PATH;
    }


    private static File findPath() throws AgentPackageNotFoundException {
        String classResourcePath = AgentPackagePath.class.getName().replaceAll("\\.", "/") + ".class";

        //URL resource = ClassLoader.getSystemClassLoader().getResource(classResourcePath);
        URL resource = AgentPackagePath.class.getClassLoader().getResource(classResourcePath);
        if (resource != null) {
            String urlString = resource.toString();

            logger.debug("The beacon class location is {}.", urlString);

            int insidePathIndex = urlString.indexOf('!');
            boolean isInJar = insidePathIndex > -1;

            if (isInJar) {
                urlString = urlString.substring(urlString.indexOf("file:"), insidePathIndex);
                File agentJarFile = null;
                try {
                    agentJarFile = new File(new URL(urlString).toURI());
                } catch (MalformedURLException | URISyntaxException e) {
                    logger.error("Can not locate agent jar file by url:{}", urlString);
                }
                if (agentJarFile != null && agentJarFile.exists()) {
                    initArthasPath(agentJarFile.getPath());
                    return agentJarFile.getParentFile();
                }
            } else {
                int prefixLength = "file:".length();
                String classLocation = urlString.substring(
                        prefixLength, urlString.length() - classResourcePath.length());
                return new File(classLocation);
            }
        }

        logger.error("Can not locate agent jar file.");
        throw new AgentPackageNotFoundException("Can not locate agent jar file.");
    }


    /**
     * 如果未设置arthas path就使用默认的
     * @param agentPath
     */
    private static void initArthasPath(String agentPath) {

        if (StringUtils.isEmpty(agentPath)) {
            return;
        }

        int index = agentPath.lastIndexOf("/");
        boolean isInJar = index > -1;
        if (isInJar) {
            String arthasUrl = agentPath.substring(0, index).concat("/arthas/arthas-agent.jar");
            if (StringUtils.isBlank(AgentConfig.Agent.ARTHAS_PATH)) {
                AgentConfig.Agent.ARTHAS_PATH = arthasUrl;
            }
        }

    }

}
