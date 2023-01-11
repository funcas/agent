package cn.vv.gray.agent.core.logging.core.converters;

import cn.vv.gray.agent.core.logging.core.Converter;
import cn.vv.gray.agent.core.logging.core.LogEvent;

/**
 * Just return the Thread.currentThread().getName()
 */
public class ThreadConverter implements Converter {
    @Override
    public String convert(LogEvent logEvent) {
        return Thread.currentThread().getName();
    }

    @Override
    public String getKey() {
        return "thread";
    }
}
