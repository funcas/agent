package cn.vv.gray.agent.core.common;

import cn.vv.gray.agent.core.logging.core.LogLevel;
import cn.vv.gray.agent.core.logging.core.WriterFactory;

/**
 * TODO
 *
 * @author Shane Fang
 * @since 1.0
 */
public class Constants {
    public static String PATH_SEPARATOR = System.getProperty("file.separator", "/");

    public static String LINE_SEPARATOR = System.getProperty("line.separator", "\n");

    public static final String VERSION = "x-version";

    public static final String META_VERSION = "version";

    public static final String CTX_VERSION_KEY = "ver";


}
