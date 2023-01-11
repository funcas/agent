package cn.vv.gray.agent.core.logging.core.converters;

import cn.vv.gray.agent.core.logging.core.Converter;
import cn.vv.gray.agent.core.logging.core.LogEvent;

/**
 * Just return the logEvent.getMessage()
 */
public class MessageConverter implements Converter {
    @Override
    public String convert(LogEvent logEvent) {
        return logEvent.getMessage();
    }

    @Override
    public String getKey() {
        return "message";
    }
}
