package cn.vv.gray.agent.core.logging.core.converters;

import cn.vv.gray.agent.core.common.Constants;
import cn.vv.gray.agent.core.logging.core.Converter;
import cn.vv.gray.agent.core.logging.core.LogEvent;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Return the StackTrace of String with logEvent.getThrowable()
 */
public class ThrowableConverter implements Converter {
    @Override
    public String convert(LogEvent logEvent) {
        Throwable t = logEvent.getThrowable();
        return t == null ? "" : format(t);
    }

    public static String format(Throwable t) {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        t.printStackTrace(new java.io.PrintWriter(buf, true));
        String expMessage = buf.toString();
        try {
            buf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Constants.LINE_SEPARATOR + expMessage;
    }

    @Override
    public String getKey() {
        return "throwable";
    }
}
