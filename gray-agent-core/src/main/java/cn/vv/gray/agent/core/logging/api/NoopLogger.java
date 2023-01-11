package cn.vv.gray.agent.core.logging.api;

/**
 * No operation logger implementation.
 * Just implement {@link ILog} interface, but do nothing.
 * <p>
 * Created by xin on 2016/11/10.
 */
public enum NoopLogger implements ILog {
    INSTANCE;

    @Override
    public void info(String message) {

    }

    @Override
    public void info(String format, Object... arguments) {

    }

    @Override
    public void info(final Throwable t, final String format, final Object... arguments) {

    }

    @Override
    public void warn(String format, Object... arguments) {

    }

    @Override
    public void error(String format, Throwable e) {

    }

    @Override
    public boolean isDebugEnable() {
        return false;
    }

    @Override
    public boolean isInfoEnable() {
        return false;
    }

    @Override
    public boolean isWarnEnable() {
        return false;
    }

    @Override
    public boolean isErrorEnable() {
        return false;
    }

    @Override
    public boolean isTraceEnabled() {
        return false;
    }

    @Override
    public void debug(String format) {

    }

    @Override
    public void debug(String format, Object... arguments) {

    }

    @Override
    public void debug(final Throwable t, final String format, final Object... arguments) {

    }

    @Override
    public void error(String format) {

    }

    @Override
    public void trace(final String format) {

    }

    @Override
    public void trace(final String format, final Object... arguments) {

    }

    @Override
    public void trace(final Throwable t, final String format, final Object... arguments) {

    }

    @Override
    public void error(Throwable e, String format, Object... arguments) {

    }

    @Override
    public void warn(Throwable e, String format, Object... arguments) {

    }
}
