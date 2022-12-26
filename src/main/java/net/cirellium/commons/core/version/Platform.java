/*
 * Copyright (C) 2022 Cirellium Network - All Rights Reserved
 *
 * Created by FearMyShotz on Mon Dec 26 2022 15:36:47
 *
 * Platform.java is part of Cirellium Commons
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 */
package net.cirellium.commons.core.version;

import org.bukkit.Bukkit;
import org.spongepowered.api.Sponge;
import net.md_5.bungee.api.ProxyServer;

/**
 * Represents a platform that Cirellium Commons is running on.
 * Provides several default implementations for Bukkit, BungeeCord, Sponge, and Velocity.
 * 
 * @author FearMyShotz
 * @version 1.0
 */
public abstract class Platform {

    public abstract Version getVersion();

    public abstract boolean isProxy();

    public static Platform getPlatform() {
        return null;
    }

    public static final Platform BUKKIT = new Platform() {
            
        @Override
        public Version getVersion() {
            return new Version(Bukkit.getServer().getBukkitVersion());
        }
    
        @Override
        public boolean isProxy() {
            return false;
        }
    };

    public static final Platform BUNGEE = new Platform() {

        @Override
        public Version getVersion() {
            return new Version(ProxyServer.getInstance().getVersion());
        }

        @Override
        public boolean isProxy() {
            return true;
        }
    };

    public static final Platform VELOCITY = new Platform() {

        @Override
        public Version getVersion() {
            return new Version(com.velocitypowered.api.proxy.ProxyServer.class.getPackage().getImplementationVersion());
        }

        @Override
        public boolean isProxy() {
            return true;
        }
    };

    public static final Platform SPONGE = new Platform() {

        @Override
        public Version getVersion() {
            return new Version(Sponge.server().game().platform().minecraftVersion().toString());
        }

        @Override
        public boolean isProxy() {
            return false;
        }
    };

    public static final Platform UNKNOWN = new Platform() {

        @Override
        public Version getVersion() {
            throw new UnsupportedOperationException("Cannot get version of unknown platform");
        }

        @Override
        public boolean isProxy() {
            throw new UnsupportedOperationException("Cannot get proxy status of unknown platform");
        }
    };
}

