package net.cirellium.commons.common.logger;

import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;

import net.cirellium.commons.common.util.StringUtils;
import net.cirellium.commons.common.version.Platform;

public class CirelliumLogger extends Logger {

    private static final Function<String, String> newName = name -> name.equals("cirelliumcore") ? "Core" : name;

    private final Platform platform;

    private final String name;

    public CirelliumLogger(Platform platform, String name) {
        super("Cirellium: " + newName.apply(name), null);
        this.platform = platform;
        this.name = newName.apply(name);

        if(platform == Platform.BUKKIT) {
            setParent(Bukkit.getServer().getLogger());
        }

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