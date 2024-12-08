package com.nicelink.nicer.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class LoggingUtil {

    private final Logger logger;

    // Приватный конструктор, чтобы запретить создание экземпляров
    private LoggingUtil() {
        throw new UnsupportedOperationException("Utility class");
    }

    // Метод для получения логгера
    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    public LoggingUtil(Class<?> clazz) {
        this.logger = LoggerFactory.getLogger(clazz);
    }

    public void info(String message) {
        logger.info(message);
    }

    public void debug(String message) {
        logger.debug(message);
    }

    public void warn(String message) {
        logger.warn(message);
    }

    public void error(String message, Throwable throwable) {
        logger.error(message, throwable);
    }
}
