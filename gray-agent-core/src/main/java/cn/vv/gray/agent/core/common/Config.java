package cn.vv.gray.agent.core.common;

import cn.vv.gray.agent.core.logging.core.LogLevel;
import cn.vv.gray.agent.core.logging.core.WriterFactory;

/**
 * TODO
 *
 * @author Shane Fang
 * @since 1.0
 */
public class Config {

    public static class Logging {
        /**
         * Log file name.
         */
        public static String FILE_NAME = "vv-agent.log";

        /**
         * Log files directory. Default is blank string, means, use "system.out" to output logs.
         *
         * @see {@link WriterFactory#getLogWriter()}
         */
        public static String DIR = "";

        /**
         * The max size of log file. If the size is bigger than this, archive the current file, and write into a new
         * file.
         */
        public static int MAX_FILE_SIZE = 300 * 1024 * 1024;

        /**
         * The log level. Default is debug.
         *
         * @see {@link LogLevel}
         */
        public static LogLevel LEVEL = LogLevel.DEBUG;
    }

    public static class AgentCfg {
        public static String currentVersion = "";
    }
}
