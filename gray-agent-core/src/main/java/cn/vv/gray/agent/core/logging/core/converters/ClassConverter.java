package cn.vv.gray.agent.core.logging.core.converters;

import cn.vv.gray.agent.core.logging.core.Converter;
import cn.vv.gray.agent.core.logging.core.LogEvent;

/**
 * Just return logEvent.getTargetClass().
 */
public class ClassConverter implements Converter {

    @Override
    public String convert(LogEvent logEvent) {
        return logEvent.getTargetClass();
    }

    @Override
    public String getKey() {
        return "logger";
    }
}
