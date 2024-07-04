package dev.atomixsoft.solar_eclipse.core.logging;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.config.builder.api.*;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;


public class AsyncConsoleLogHandler implements LogHandler {

    private enum LogLevel {
        TRACE(Level.TRACE),
        DEBUG(Level.DEBUG),
        INFO(Level.INFO),
        WARN(Level.WARN),
        ERROR(Level.ERROR),
        FATAL(Level.FATAL);

        private final Level log4jLevel;

        LogLevel(Level log4jLevel) {
            this.log4jLevel = log4jLevel;
        }

        Level getLog4jLevel() {
            return log4jLevel;
        }
    }

    private final Log logger;


    public AsyncConsoleLogHandler(String logHandlerName, String defaultLogLevel, String logHandlerPattern) {
        LogLevel logLevel = LogLevel.INFO;
        
        this.logger = LogFactory.getLog(logHandlerName);
        LoggerContext context = (LoggerContext) LogManager.getContext(false);
        ConfigurationBuilder<BuiltConfiguration> builder = ConfigurationBuilderFactory.newConfigurationBuilder();

        switch(defaultLogLevel) {
            case "TRACE":
                logLevel = LogLevel.TRACE;
                break;
            case "DEBUG":
                logLevel = LogLevel.DEBUG;
                break;
            case "INFO":
                logLevel = LogLevel.INFO;
                break;
            case "WARN":
                logLevel = LogLevel.WARN;
                break;
            case "ERROR":
                logLevel = LogLevel.ERROR;
                break;
            case "FATAL":
                logLevel = LogLevel.FATAL;
                break;
            default:
                logLevel = LogLevel.INFO;
                break;
        }

        builder.setStatusLevel(Level.ERROR);
        builder.setConfigurationName("AsyncConsoleConfig");
        
        AppenderComponentBuilder console = builder.newAppender("Stdout", "CONSOLE")
                                                  .addAttribute("target", ConsoleAppender.Target.SYSTEM_OUT);
        console.add(builder.newLayout("PatternLayout")
                           .addAttribute("pattern", logHandlerPattern));
        builder.add(console);
        
        RootLoggerComponentBuilder rootLogger = builder.newRootLogger(logLevel.getLog4jLevel());
        rootLogger.add(builder.newAppenderRef("Stdout"));
        builder.add(rootLogger);
        
        context.start(builder.build());
    }


    @Override
    public void debug(String message) {
        logger.debug(message);
    }

    @Override
    public void info(String message) {
        logger.info(message);
    }

    @Override
    public void warn(String message) {
        logger.warn(message);
    }

    @Override
    public void error(String message) {
        logger.error(message);
    }
}
