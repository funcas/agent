package cn.vv.gray.agent.core.logging.core;

/**
 * The representation of logging events. This instance is pass around to the List of Converter.
 */
public class LogEvent {

    public LogEvent(LogLevel level, String message, Throwable throwable, String targetClass) {
        this.level = level;
        this.message = message;
        this.throwable = throwable;
        this.targetClass = targetClass;
    }

    private LogLevel level;
    private String message;
    private Throwable throwable;
    private String targetClass;

    public String getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(String targetClass) {
        this.targetClass = targetClass;
    }

    public LogLevel getLevel() {
        return level;
    }

    public void setLevel(LogLevel level) {
        this.level = level;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
}
