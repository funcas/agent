package cn.vv.gray.agent.core.logging.core.converters;

import cn.vv.gray.agent.core.logging.core.Converter;
import cn.vv.gray.agent.core.logging.core.LogEvent;

/**
 * This Converter is used to return the literal.
 */
public class LiteralConverter implements Converter {

    private final String literal;

    public LiteralConverter(String literal) {
        this.literal = literal;
    }

    @Override
    public String convert(LogEvent logEvent) {
        return literal;
    }

    @Override
    public String getKey() {
        return "";
    }
}
