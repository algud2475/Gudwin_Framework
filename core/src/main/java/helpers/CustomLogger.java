package helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomLogger {

    protected CustomLogger() {}

    public static void debug(String message, Object... args) {
        getLogger().debug(message, args);
    }

    public static void info(String message, Object... args) {
        getLogger().info(message, args);
    }

    public static void warn(String message, Object... args) {
        getLogger().warn(message, args);
    }

    public static void error(String message, Object... args) {
        getLogger().error(message, args);
    }

    private static Logger getLogger() {
        return LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[3].getClassName());
    }
}
