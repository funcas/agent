package cn.vv.gray.agent.core.logging.core;

import cn.vv.gray.agent.core.logging.api.ILog;
import cn.vv.gray.agent.core.util.StringUtil;

/**
 * A flexible Logger configurable with pattern string. This is default implementation of {@link ILog} This can parse a
 * pattern to the List of converter with Parser. We package LogEvent with message, level,timestamp ..., passing around
 * to the List of converter to concat actually Log-String.
 */
public class PatternLogger extends AbstractLogger {
    public static final String DEFAULT_PATTERN = "%level %timestamp %thread %class : %msg %throwable";

    private String pattern;

    public PatternLogger(Class<?> targetClass, String pattern) {
        this(targetClass.getSimpleName(), pattern);
    }

    public PatternLogger(String targetClass, String pattern) {
        super(targetClass);
        this.setPattern(pattern);
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        if (StringUtil.isEmpty(pattern)) {
            pattern = DEFAULT_PATTERN;
        }
        this.pattern = pattern;
        this.converters = new Parser(pattern, DEFAULT_CONVERTER_MAP).parse();
    }

    @Override
    protected String format(LogLevel level, String message, Throwable t) {
        LogEvent logEvent = new LogEvent(level, message, t, targetClass);
        StringBuilder stringBuilder = new StringBuilder();
        for (Converter converter : this.converters) {
            stringBuilder.append(converter.convert(logEvent));
        }
        return stringBuilder.toString();
    }
}
