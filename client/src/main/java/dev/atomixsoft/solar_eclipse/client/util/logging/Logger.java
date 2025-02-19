package dev.atomixsoft.solar_eclipse.client.util.logging;

import org.lwjgl.glfw.GLFWErrorCallback;

import dev.atomixsoft.solar_eclipse.core.logging.LogHandler;
import dev.atomixsoft.solar_eclipse.core.logging.AsyncConsoleLogHandler;

public class Logger {
    private LogHandler m_LogHandler;

    public enum SupportedLogHandlerTypes {
        ASYNC_CONSOLE
    }


    public class Log4jGLFWErrorCallback extends GLFWErrorCallback {
        @Override
        public void invoke(int error, long description) {
            String errorMsg = GLFWErrorCallback.getDescription(description);
            
            Logger.this.error("GLFW Error [" + error + "]: " + errorMsg);
        }
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

    public void trace(String message) {
        this.m_LogHandler.trace(message);
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
