package net.cirellium.commons.common.exception.plugin;

import net.cirellium.commons.common.plugin.CirelliumPlugin;

public class PluginLoadException extends PluginException {

    public PluginLoadException(CirelliumPlugin<?> plugin, String message) {
        super(plugin, message);
    }
  
    public PluginLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}