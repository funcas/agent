package cn.vv.gray.agent.core.logging.core.converters;

import cn.vv.gray.agent.core.logging.core.Converter;
import cn.vv.gray.agent.core.logging.core.LogEvent;

/**
 * Just return logEvent.getLevel().name()
 */
public class LevelConverter implements Converter {
    @Override
    public String convert(LogEvent logEvent) {
        return logEvent.getLevel().name();
    }

    @Override
    public String getKey() {
        return "level";
    }
}
