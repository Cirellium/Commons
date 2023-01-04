package net.cirellium.commons.common.logger;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;

import net.cirellium.commons.common.version.Platform;

public class CirelliumLogger extends Logger {

    public CirelliumLogger(Platform platform, String name) {
        super("cirellium: "+ name, null);

        if(platform == Platform.BUKKIT) {
            setParent(Bukkit.getServer().getLogger());
        }

        setLevel(Level.ALL);
    }

    
    
}