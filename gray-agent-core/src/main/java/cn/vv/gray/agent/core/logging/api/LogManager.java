package cn.vv.gray.agent.core.logging.api;


import cn.vv.gray.agent.core.logging.core.EasyLogResolver;

/**
 * LogManager is the {@link LogResolver} implementation manager. By using {@link LogResolver}, {@link
 * LogManager#getLogger(Class)} returns a {@link ILog} implementation. This module use this class as the main entrance,
 * and block the implementation detail about log-component. In different modules, like server or sniffer, it will use
 * different implementations. <p> If no {@link LogResolver} is registered, return {@link NoopLogger#INSTANCE} to avoid
 * {@link NullPointerException}. If {@link LogManager#setLogResolver(LogResolver)} is called twice, the second will
 * override the first without any warning or exception. <p> Created by xin on 2016/11/10.
 */
public class LogManager {
    private static LogResolver RESOLVER = new EasyLogResolver();

    public static void setLogResolver(LogResolver resolver) {
        LogManager.RESOLVER = resolver;
    }

    public static ILog getLogger(Class<?> clazz) {
        if (RESOLVER == null) {
            return NoopLogger.INSTANCE;
        }
        return LogManager.RESOLVER.getLogger(clazz);
    }
}
