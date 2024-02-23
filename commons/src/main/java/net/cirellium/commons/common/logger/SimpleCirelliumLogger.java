package net.cirellium.commons.common.logger;

import java.util.logging.Logger;

import net.cirellium.commons.common.version.Platform;

public class SimpleCirelliumLogger extends CirelliumLogger {

    public SimpleCirelliumLogger(Platform platform, String name) {
        super(platform, name);
    }

    public SimpleCirelliumLogger(String name) {
        super(Platform.UNKNOWN, name);
    }

    @Override
    public void info(String msg) {
        Logger.getLogger("default").info(msg);
    }
}