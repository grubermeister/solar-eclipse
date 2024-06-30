package dev.atomixsoft.solar_eclipse.client.logging;

import dev.atomixsoft.solar_eclipse.core.logging.LogHandler;
import dev.atomixsoft.solar_eclipse.core.logging.AsyncConsoleLogHandler;

public class Logger {
    //private String LEVEL_VAR;
    //private String PATTERN_VAR;

    private LogHandler m_LogHandler;

    public enum SupportedLogHandlerTypes {
        ASYNC_CONSOLE
    }

    public Logger(String logHandlerName, SupportedLogHandlerTypes logHandlerType, String logHandlerLevel, String logHandlerPattern) {
        switch(logHandlerType) {
            case ASYNC_CONSOLE:
                this.m_LogHandler = new AsyncConsoleLogHandler(logHandlerName, logHandlerLevel, logHandlerPattern);
                       
                break;
            default:
                throw new IllegalArgumentException("Unsupported log handler type");
        }
    }

    public void debug(String message) {
        this.m_LogHandler.debug(message);
    }

    public void warn(String message) {
        this.m_LogHandler.warn(message);
    }

    public void error(String message) {
        this.m_LogHandler.error(message);
    }
    
    public void info(String message) {
        this.m_LogHandler.info(message);
    }
}
