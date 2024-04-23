/**
* Copyright (C) 2023 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Sun Jan 08 2023 14:36:14
*
* DefaultFileHandler.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.bukkit.file;


import java.util.logging.Logger;

import net.cirellium.commons.bukkit.CirelliumBukkitPlugin;
import net.cirellium.commons.bukkit.file.implementation.ConfigFile;
import net.cirellium.commons.bukkit.utils.BukkitPlatform;
import net.cirellium.commons.common.logger.SimpleCirelliumLogger;

public final class DefaultFileHandler extends AbstractFileHandlerService {

    public DefaultFileHandler(CirelliumBukkitPlugin plugin) {
        super(plugin);
    }
    
    @Override
    public void addFiles() {
        logger.info("Adding files...");

        addFile(new ConfigFile(plugin, "config"));
        addFile(new ConfigFile(plugin, "database"));
        addFile(new ConfigFile(plugin, "messages"));
    }

    @Override
    public Logger getLogger() {
        return new SimpleCirelliumLogger(BukkitPlatform.INSTANCE, getName());
    }
}