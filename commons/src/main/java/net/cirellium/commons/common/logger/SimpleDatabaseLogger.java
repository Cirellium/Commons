package net.cirellium.commons.common.logger;

import java.sql.ResultSet;

import net.cirellium.commons.common.version.Platform;

public class SimpleDatabaseLogger extends CirelliumLogger {

    public SimpleDatabaseLogger(Platform platform, String name) {
        super(platform, name);
    }

    public void logSentQuery(String query) {
        info("Sent query " + query);
    }

    public void log(ResultSet resultSet) {
        info("Received result: " + resultSet);
    }
    
}