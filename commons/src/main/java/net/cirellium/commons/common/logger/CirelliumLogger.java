package net.cirellium.commons.common.logger;

import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.cirellium.commons.common.version.Platform;

public abstract class CirelliumLogger extends Logger {

    private static final Function<String, String> newName = name -> name.equals("cirelliumcore") ? "Core" : name;

    private final Platform platform;

    private final String name;

    public CirelliumLogger(Platform platform, String name) {
        super("Cirellium: " + newName.apply(name), null);
        this.platform = platform;
        this.name = newName.apply(name);

        setLevel(Level.ALL);
    }

    @Override
    public abstract void info(String msg);

    
    
}