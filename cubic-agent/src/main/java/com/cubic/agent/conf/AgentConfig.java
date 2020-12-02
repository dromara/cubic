
package com.cubic.agent.conf;


import io.netty.handler.logging.LogLevel;

/**
 * This is the core config in sniffer agent.
 */
public class AgentConfig {

    public static class Agent {

        public static String TCP_SERVERS = "localhost:9999";

        public static String ARTHAS_PATH = "";

        public static String VERSION = "1.0";

        /**
         * Uid
         */
        public static String INSTANCE_UUID = "";

        /**
         * Namespace isolates headers in cross process propagation. The HEADER name will be `HeaderName:Namespace`.
         */
        public static String NAMESPACE = "";

        /**
         * Service name is showed in skywalking-ui. Suggestion: set a unique name for each service, service instance
         * nodes share the same code
         */
        public static String SERVICE_NAME = "";

        /**
         * Authentication active is based on backend setting, see application.yml for more details. For most scenarios,
         * this needs backend extensions, only basic match auth provided in default implementation.
         */
        public static String AUTHENTICATION = "";

    }


    public static class Logging {
        /**
         * Log file name.
         */
        public static String FILE_NAME = "cubic-agent.log";

        /**
         * Log files directory. Default is blank string, means, use "{theSkywalkingAgentJarDir}/logs  " to output logs.
         * {theSkywalkingAgentJarDir} is the directory where the skywalking agent jar file is located.
         * <p>
         */
        public static String DIR = "";
        /**
         * 日志目录是否拼接 service-name
         */
        public static boolean IS_APPEND_SERVICE_NAME = false;

        /**
         * The max size of log file. If the size is bigger than this, archive the current file, and write into a new
         * file.
         */
        public static int MAX_FILE_SIZE = 300 * 1024 * 1024;

        /**
         * The max history log files. When rollover happened, if log files exceed this number, then the oldest file will
         * be delete. Negative or zero means off, by default.
         */
        public static int MAX_HISTORY_FILES = -1;

        /**
         * The log level. Default is debug.
         */
        public static LogLevel LEVEL = LogLevel.DEBUG;

        /**
         * The log output. Default is FILE.
         */

        /**
         * The log patten. Default is "%level %timestamp %thread %class : %msg %throwable". Each conversion specifiers
         * starts with a percent sign '%' and fis followed by conversion word. There are some default conversion
         * specifiers: %thread = ThreadName %level = LogLevel  {@link LogLevel} %timestamp = The now() who format is
         * 'yyyy-MM-dd HH:mm:ss:SSS' %class = SimpleName of TargetClass %msg = Message of user input %throwable =
         * Throwable of user input %agent_name = ServiceName of Agent {@link Agent#SERVICE_NAME}
         */
        public static String PATTERN = "-| %timestamp | %level | %thread | %class | %msg | %throwable";
    }

}
