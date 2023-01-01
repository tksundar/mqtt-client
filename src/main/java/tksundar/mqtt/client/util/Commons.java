package tksundar.mqtt.client.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.logging.*;

/**
 * Author : Sundar Krishnamachari
 * Created: 2022/12/31
 * email: tksrajan@gmail.com
 */
public class Commons {

    private static final Logger COMMONS_LOGGER = Logger.getLogger(Commons.class.getName());
    private static final String LOG_FILE_KEY = "logFileLocation";
    private static final ResourceBundle bundle = getBundle();

    private static final String fileSeparator = File.separator;

    private static ResourceBundle getBundle() {
        ResourceBundle bundle;
        try {
            bundle = new PropertyResourceBundle(new FileReader("app.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return bundle;
    }

    public enum LoggerType {CONSOLE, FILE}

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
                    Handler handler;
                    try {
                        String homeDir = System.getenv("HOME");
                        String logFileLocation = bundle.getString(LOG_FILE_KEY);
                        File f = new File(homeDir + fileSeparator + logFileLocation);
                        if (!f.exists()) {
                            boolean created = f.mkdir();
                            if (created) {
                                COMMONS_LOGGER.fine("log dir created");
                            } else {
                                COMMONS_LOGGER.severe("Error creating file");
                                COMMONS_LOGGER.throwing(Commons.class.getName(), "getLogger",
                                        new RuntimeException("Could not create dir"));
                            }
                        }
                        System.out.println(homeDir + fileSeparator + logFileLocation + fileSeparator + "mqtt.log");
                        handler = new FileHandler(homeDir + fileSeparator + logFileLocation + fileSeparator + "mqtt.log");
                        handler.setFormatter(new SimpleFormatter());
                        logger.addHandler(handler);

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
                default -> COMMONS_LOGGER.warning("Unexpected value: " + type);
            }
        }
        return logger;
    }
}
