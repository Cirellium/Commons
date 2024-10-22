/**
* Copyright (C) 2023 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Fri Dec 30 2022 17:00:52
*
* CirelliumUser.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.common.data.user;

import java.util.UUID;

import lombok.Getter;
import net.cirellium.commons.common.plugin.CirelliumPlugin;
import net.cirellium.commons.common.util.Controllable;

/**
 * This class represents a user of the plugin.
 */
 public abstract class AbstractCirelliumUser<A extends AbstractCirelliumUser<A>> implements Controllable<UserDataHandler<A>>, LoadableUser {
    
    @Getter
    protected transient final CirelliumPlugin<?> plugin;

    @Getter
    protected UUID uuid;

    public AbstractCirelliumUser(CirelliumPlugin<?> plugin) {
        this.plugin = plugin;

        // getService().getLogger().info("User created for plugin: " + plugin.getPluginName());
    }

    @Override
    public UUID getId() {
        return uuid;
    }
}