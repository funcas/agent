package cn.vv.gray.agent.core.common;

import cn.vv.gray.agent.core.logging.core.LogLevel;
import cn.vv.gray.agent.core.logging.core.LogOutput;
import cn.vv.gray.agent.core.logging.core.ResolverType;

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

        public static String DIR = "";

        /**
         * The max size of log file. If the size is bigger than this, archive the current file, and write into a new
         * file.
         */
        public static int MAX_FILE_SIZE = 30 * 1024 * 1024;

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
        public static LogOutput OUTPUT = LogOutput.FILE;

        /**
         * The log resolver type. Default is PATTERN which will create PatternLogResolver later.
         */
        public static ResolverType RESOLVER = ResolverType.PATTERN;

        public static String PATTERN = "%timestamp %level %thread %class - %msg %throwable";
    }

    public static class Agent {
        public static String CURRENT_VERSION = "";

        public static String TAG = "";

        public static String SERVICE_NAME = "";

        public static boolean GRAY_MODE_ENABLED = false;

    }
}
