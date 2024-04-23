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

public abstract class AbstractFileHandlerService extends AbstractBukkitService {

    protected Map<String, AbstractPluginFile> files = new HashMap<>();

    public AbstractFileHandlerService(CirelliumBukkitPlugin plugin) {
        super(plugin, ServiceType.FILE, ServiceType.NONE);
    }

    @Override
    public void initialize(CirelliumBukkitPlugin plugin) {
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
    public void shutdown(CirelliumBukkitPlugin plugin) {
        getFiles().stream().filter(file -> file instanceof DatabaseFile).forEach(file -> file.save());
    }

    public void addFile(AbstractPluginFile file) {
        files.putIfAbsent(file.getFile().getName(), file);
    }

    public AbstractPluginFile getFile(String name) {
        AbstractPluginFile file = files.get(name);

        return file == null ? new ConfigFile(plugin, name): files.get(name);
    }

    public Set<String> getFileNames() {
        return files.keySet();
    }

    public Set<AbstractPluginFile> getFiles() {
        return Set.copyOf(files.values());
    }

    protected abstract void addFiles();

}