/**
* Copyright (C) 2022 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Sun Jan 01 2023 10:26:06
*
* FileHandlerService.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.bukkit.file;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.cirellium.commons.bukkit.CirelliumBukkitPlugin;
import net.cirellium.commons.bukkit.file.implementation.AbstractPluginFile;
import net.cirellium.commons.bukkit.file.implementation.ConfigFile;
import net.cirellium.commons.bukkit.file.implementation.DatabaseFile;
import net.cirellium.commons.common.service.AbstractService;
import net.cirellium.commons.common.service.ServiceType;

public class DefaultFileHandlerService<P extends CirelliumBukkitPlugin<P>> extends AbstractService<P> {
    
    public Map<String, AbstractPluginFile<P>> files = new HashMap<>();

    public DefaultFileHandlerService(P plugin) {
        super(plugin, ServiceType.FILE);
    }
    
    @Override
    public void initialize() {
        addFile(new ConfigFile<P>(plugin, "config"));
        addFile(new DatabaseFile<P>(plugin, "database"));
    }
    
    @Override
    public void shutdown() {
        getFiles().stream().filter(file -> file instanceof DatabaseFile).forEach(file -> file.save());
    }

    public void addFile(AbstractPluginFile<P> file) {
        files.put(file.getName(), file);
    }

    public AbstractPluginFile<P> getFile(String name) {
        return files.get(name);
    }

    public Set<String> getFileNames() {
        return files.keySet();
    }

    public Set<AbstractPluginFile<P>> getFiles() {
        return Set.copyOf(files.values());
    }
    
    
    
}