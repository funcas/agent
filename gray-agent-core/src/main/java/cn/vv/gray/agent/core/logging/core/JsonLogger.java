package cn.vv.gray.agent.core.logging.core;

import cn.vv.gray.agent.core.logging.core.converters.LiteralConverter;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class JsonLogger extends AbstractLogger {
    private final Gson gson;

    public JsonLogger(Class<?> targetClass, Gson gson) {
        this(targetClass.getSimpleName(), gson);
    }

    /**
     * In the Constructor, the instances of converters are created,
     * except those {@link LiteralConverter} since this class is used
     * only the literals in {@link PatternLogger} ,
     * and thus should not be added to the json log.
     *
     * @param targetClass the logger class
     * @param gson        instance of Gson works as json serializer
     */
    public JsonLogger(String targetClass, Gson gson) {
        super(targetClass);
        this.gson = gson;
        for (Map.Entry<String, Class<? extends Converter>> entry : DEFAULT_CONVERTER_MAP.entrySet()) {
            final Class<? extends Converter> converterClass = entry.getValue();
            try {
                if (converters instanceof LiteralConverter) {
                    continue;
                }
                converters.add(converterClass.newInstance());
            } catch (IllegalAccessException | InstantiationException e) {
                throw new IllegalStateException("Create Converter error. Class: " + converterClass, e);
            }
        }
    }

    @Override
    protected String format(LogLevel level, String message, Throwable e) {
        LogEvent logEvent = new LogEvent(level, message, e, this.targetClass);
        Map<String, String> log = new HashMap<>(this.converters.size());
        for (Converter converter : this.converters) {
            log.put(converter.getKey(), converter.convert(logEvent));
        }
        return this.gson.toJson(log);
    }
}
