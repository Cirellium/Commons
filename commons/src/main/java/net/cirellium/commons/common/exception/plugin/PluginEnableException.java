/**
* Copyright (C) 2023 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Sun Jan 01 2023 10:32:37
*
* PluginEnableException.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.common.exception.plugin;

import net.cirellium.commons.common.plugin.CirelliumPlugin;

public class PluginEnableException extends PluginException {

    public PluginEnableException(CirelliumPlugin<?> plugin, String message) {
        super(plugin, message);
    }
  
    public PluginEnableException(String message, Throwable cause) {
        super(message, cause);
    }
}