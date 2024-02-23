package net.cirellium.commons.common.logger;

import java.sql.ResultSet;
import java.util.logging.Logger;

import net.cirellium.commons.common.version.Platform;

public class SimpleDatabaseLogger extends CirelliumLogger {

    private Logger parentLogger;

    public SimpleDatabaseLogger(Platform platform, String name, Logger parent) {
        super(platform, name);

        parentLogger = parent;
    }

    public void logSentQuery(String query) {
        info("Sent query " + query);
    }

    public void log(ResultSet resultSet) {
        info("Received result: " + resultSet);
    }

    @Override
    public void info(String msg) {
        parentLogger.info(msg);
    }
    
}