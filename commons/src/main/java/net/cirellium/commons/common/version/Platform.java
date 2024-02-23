/**
 * Copyright (C) 2023 Cirellium Network - All Rights Reserved
 *
 * Created by FearMyShotz on Mon Dec 26 2022 15:36:47
 *
 * Platform.java is part of Cirellium Commons
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 */
package net.cirellium.commons.common.version;

/**
 * Represents a platform that Cirellium Commons is running on.
 * Provides several default implementations for Bukkit, BungeeCord, Sponge, and Velocity.
 * 
 * @author Fear
 * @version 1.0
 */
public abstract class Platform {

    public static final Platform UNKNOWN = new Platform() {
        @Override
        public Version getVersion() {
            return Version.UNKNOWN;
        }

        @Override
        public boolean isProxy() {
            return false;
        }
    };

    public abstract Version getVersion();

    public abstract boolean isProxy();
}

