package cn.vv.gray.agent.core.logging.core;

/**
 * The Converter, it is used to convert the LogEvent to the String.
 * For JsonLogger, the `getKey()` method is used to generate the key for json.
 */
public interface Converter {

    String convert(LogEvent logEvent);

    String getKey();
}
