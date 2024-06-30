package dev.atomixsoft.solar_eclipse.core.logging;


public interface LogHandler {
    void debug(String msg);
    void info(String msg);
    void warn(String msg);
    void error(String msg);

    enum LogLevel {
        TRACE,
        DEBUG,
        INFO,
        WARN,
        ERROR,
        FATAL
    }
}
