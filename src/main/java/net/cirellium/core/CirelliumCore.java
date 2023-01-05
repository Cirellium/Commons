/**
* Copyright (C) 2022 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Thu Dec 29 2022 17:22:04
*
* CirelliumCore.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.core;

import java.util.HashSet;
import java.util.Set;

import net.cirellium.commons.bukkit.CirelliumBukkitPlugin;
import net.cirellium.commons.common.plugin.CirelliumPlugin;

/**
 * This is the main class for CirelliumCore.
 * It manages all Cirellium plugins.
 */
public class CirelliumCore extends CirelliumBukkitPlugin<CirelliumCore> {

    private static CirelliumCore instance;

    private Set<CirelliumPlugin<?>> cirelliumPlugins;

    @Override
    public boolean load() {
        this.cirelliumPlugins = new HashSet<CirelliumPlugin<?>>();

        // TODO Setup NMS - if it fails, disable the plugin

        return true;
    }

    @Override
    public void enable() {
        instance = this;
    }

    @Override
    public void disable() {
        
    }

    public static CirelliumCore getInstance() {
        return instance;
    }

    @Override
    public CirelliumCore getSelf() {
        return this;
    }

    
}