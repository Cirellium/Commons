package net.cirellium.commons.common.logger;

import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.cirellium.commons.common.version.Platform;

public abstract class CirelliumLogger extends Logger {

    private static final Function<String, String> newName = name -> name.equals("cirelliumcore") ? "Core" : name;

    public CirelliumLogger(Platform platform, String name) {
        super("Cirellium: " + newName.apply(name), null);

        setLevel(Level.ALL);
    }

    @Override
    public abstract void info(String msg);
    
    public Platform getPlatform() {
        return platform;
    }

    public String getName() {
        return name;
    }
}