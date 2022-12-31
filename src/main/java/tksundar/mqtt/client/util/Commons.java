package tksundar.mqtt.client.util;

import java.io.IOException;
import java.util.logging.*;

/**
 * Author : Sundar Krishnamachari
 * Created: 2022/12/31
 * email: tksrajan@gmail.com
 */
public class Commons {

    private static final Logger COMMONS_LOGGER = Logger.getLogger(Commons.class.getName());

    public enum LoggerType {CONSOLE, FILE, STREAM, MEMORY}

    public static Logger getLogger(String loggerName, LoggerType... types) {
        Logger logger = Logger.getLogger(loggerName);
        for (LoggerType type : types) {
            switch (type) {
                case CONSOLE -> {
                    COMMONS_LOGGER.fine("setting console logger");
                    Handler handler = new ConsoleHandler();
                    handler.setFormatter(new SimpleFormatter());
                    logger.addHandler(handler);
                }
                case FILE -> {
                    COMMONS_LOGGER.fine("setting file logger");
                    Handler handler ;
                    try {
                        handler = new FileHandler("mqtt.log");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    handler.setFormatter(new SimpleFormatter());
                    logger.addHandler(handler);
                }
                default -> COMMONS_LOGGER.warning("Unexpected value: " + type);
            }
        }
        return logger;
    }
}
