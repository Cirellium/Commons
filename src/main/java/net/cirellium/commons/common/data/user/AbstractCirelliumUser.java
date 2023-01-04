/**
* Copyright (C) 2022 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Fri Dec 30 2022 17:00:52
*
* CirelliumUser.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.common.data.user;

import java.util.UUID;

import net.cirellium.commons.common.plugin.CirelliumPlugin;

/**
 * This class represents a user of the plugin.
 */
public abstract class AbstractCirelliumUser<P extends CirelliumPlugin<P>> {
    
    private transient final P plugin;

    private UUID uuid;

    public AbstractCirelliumUser(P plugin) {
        this.plugin = plugin;
    }



}