package com.matrix.cubic.agent.core.conf;

import com.matrix.cubic.agent.core.boot.AgentPackageNotFoundException;
import com.matrix.cubic.agent.core.boot.AgentPackagePath;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * 初始化配置
 *
 * @ClassName CubicConfInitalizer
 * @Author QIANGLU
 * @Date 2020/5/19 9:57 上午
 * @Version 1.0
 */
public class CubicConfInitalizer {

    private static final Logger logger = LoggerFactory.getLogger(CubicConfInitalizer.class);

    private final static String SYSTEM_CONFIG_PATH = "cubic_config";
    private final static String DEFAULT_CONFIG_FILE_NAME = "/conf/agent.conf";

    public static void initConfig() {
        try ( InputStreamReader reader = loadConfig()){
            Properties properties = new Properties();
            properties.load(reader);
            CubicConfigConvert.initialize(properties, AgentConfig.class);
        }catch (Exception e){
            logger.error("Failed to read the config file, agent will is going to run in default config.",e);
        }

    }

    public static InputStreamReader loadConfig() throws CubicConfigNotFoundException, AgentPackageNotFoundException {

        String sysPath = System.getProperty(SYSTEM_CONFIG_PATH);
        File path = StringUtils.isEmpty(sysPath) ? new File(AgentPackagePath.getPath(), DEFAULT_CONFIG_FILE_NAME) : new File(sysPath);

        if (path.exists() && path.isFile()) {
            try {
                logger.info("Config file found in {}.", path);

                InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8);
                return inputStreamReader;
            } catch (FileNotFoundException e) {
                throw new CubicConfigNotFoundException("load agent agent.config fail", e);
            }
        }
        throw new CubicConfigNotFoundException("Failed to load agent.config");

    }
}
