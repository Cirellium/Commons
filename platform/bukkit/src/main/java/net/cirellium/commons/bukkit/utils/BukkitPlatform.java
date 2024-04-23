package net.cirellium.commons.bukkit.utils;

import org.bukkit.Bukkit;

import net.cirellium.commons.common.version.Platform;
import net.cirellium.commons.common.version.Version;

public class BukkitPlatform extends Platform {

    public static final BukkitPlatform INSTANCE = new BukkitPlatform();

    @Override
    public Version getVersion() {
        return new Version(Bukkit.getServer().getBukkitVersion());
    }

    @Override
    public boolean isProxy() {
        return false;
    }
    
}