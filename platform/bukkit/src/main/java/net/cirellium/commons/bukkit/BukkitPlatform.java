package net.cirellium.commons.bukkit;

import net.cirellium.commons.common.version.Platform;

public abstract class BukkitPlatform extends Platform {
 
    @Override
    public boolean isProxy() {
        return false;
    }

}