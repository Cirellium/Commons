/**
* Copyright (C) 2022 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Sun Jan 08 2023 14:36:14
*
* DefaultFileHandler.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.bukkit.file;

import net.cirellium.commons.bukkit.CirelliumBukkitPlugin;
import net.cirellium.commons.bukkit.file.implementation.ConfigFile;
import net.cirellium.commons.bukkit.file.implementation.DatabaseFile;

public class DefaultFileHandler<P extends CirelliumBukkitPlugin<P>> extends AbstractFileHandlerService<P> {

    public DefaultFileHandler(P plugin) {
        super(plugin);
    }
    
    @Override
    public void addFiles() {
        addFile(new ConfigFile<P>(plugin, "config"));
        addFile(new DatabaseFile<P>(plugin, "database"));
    }
}