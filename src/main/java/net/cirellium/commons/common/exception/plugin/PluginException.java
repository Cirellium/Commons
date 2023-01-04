/**
* Copyright (C) 2022 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Sun Jan 01 2023 10:30:41
*
* PluginException.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.common.exception.plugin;

import net.cirellium.commons.common.plugin.CirelliumPlugin;

public abstract class PluginException extends RuntimeException {

    protected CirelliumPlugin<?> plugin;

    public PluginException(CirelliumPlugin<?> plugin, String message) {
        super(message);

        this.plugin = plugin;
    }
  
    public PluginException(String message, Throwable cause) {
        super(message, cause);
    }

    public CirelliumPlugin<?> getPlugin() {
        return plugin;
    }
}