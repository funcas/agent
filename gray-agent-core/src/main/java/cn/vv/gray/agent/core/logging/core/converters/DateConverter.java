package cn.vv.gray.agent.core.logging.core.converters;

import cn.vv.gray.agent.core.logging.core.Converter;
import cn.vv.gray.agent.core.logging.core.LogEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The Converter is used to return a now date with format.
 */
public class DateConverter implements Converter {

    @Override
    public String convert(LogEvent logEvent) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
    }

    @Override
    public String getKey() {
        return "@timestamp";
    }
}
