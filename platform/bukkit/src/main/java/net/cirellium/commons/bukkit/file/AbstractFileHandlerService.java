/**
* Copyright (C) 2023 Cirellium Network - All Rights Reserved
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
import net.cirellium.commons.bukkit.service.AbstractBukkitService;
import net.cirellium.commons.common.service.ServiceType;

public abstract class AbstractFileHandlerService<P extends CirelliumBukkitPlugin<P>> 
    extends AbstractBukkitService<P> {

    protected Map<String, AbstractPluginFile<P>> files = new HashMap<>();

    public AbstractFileHandlerService(P plugin) {
        super(plugin, ServiceType.FILE, ServiceType.NONE);
    }

    @Override
    public void initialize() {
        logger.info("§aInitializing files...");
        addFiles();

        logger.info("Added files: " + files.size());

        logger.info("&aMessages file test: " + files.get("messages").getValue("messages.command.confirmation"));

        files.values().forEach(file -> {
            file.create();
            file.load();
        });
    }

    @Override
    public void shutdown() {
        getFiles().stream().filter(file -> file instanceof DatabaseFile).forEach(file -> file.save());
    }

    public void addFile(AbstractPluginFile<P> file) {
        files.putIfAbsent(file.getFile().getName(), file);
    }

    public AbstractPluginFile<P> getFile(String name) {
        AbstractPluginFile<P> file = files.get(name);

        return file == null ? new ConfigFile<P>(plugin, name): files.get(name);
    }

    public Set<String> getFileNames() {
        return files.keySet();
    }

    public Set<AbstractPluginFile<P>> getFiles() {
        return Set.copyOf(files.values());
    }

    protected abstract void addFiles();

}