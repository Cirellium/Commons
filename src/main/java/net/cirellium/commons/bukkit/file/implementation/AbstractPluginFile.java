/**
* Copyright (C) 2022 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Sun Jan 01 2023 10:26:14
*
* PluginFile.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.bukkit.file.implementation;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import net.cirellium.commons.bukkit.CirelliumBukkitPlugin;
import net.cirellium.commons.common.logger.CirelliumLogger;
import net.cirellium.commons.common.version.Platform;

/**
 * This class an abstract implementation of a plugin file.
 * It uses the {@link FileConfiguration} class as well as the {@link YamlConfiguration} class to load and save the file.
 */
public abstract class AbstractPluginFile<P extends CirelliumBukkitPlugin<P>> {

    private final P plugin;

    public File file;
    public FileConfiguration fileConfig;

    public Logger logger;

    public String name;

    public boolean comments;

    public AbstractPluginFile(P plugin) {
        plugin.getConfig().options().copyDefaults(true);
        plugin.saveConfig();

        this.plugin = plugin;
        this.name = "config";
        this.file = new File(plugin.getDataFolder(), name + ".yml");
        this.logger = plugin.getLogger();

        create();
    }

    public AbstractPluginFile(P plugin, String fileName) {
        this.plugin = plugin;
        this.name = fileName;
        this.file = new File(plugin.getDataFolder(), fileName + ".yml");
        this.logger = plugin.getLogger();

        create();
    }

    public AbstractPluginFile(File file, FileConfiguration fileConfig) {
        this.file = file;
        this.fileConfig = fileConfig;
        this.fileConfig.options().copyDefaults(true);
        this.name = file.getName().replace(".yml", "");
        this.logger = new CirelliumLogger(Platform.BUKKIT, name);

        this.plugin = null;
    }

    public AbstractPluginFile(File file) {
        this(file, YamlConfiguration.loadConfiguration(file));
    }

    public AbstractPluginFile(String path) {
        this(new File(path));
    }

    public void create() {
        if (file.exists()) {
            load(file);
            return;
        }

        logger.info("&bCreating new file " + name + ".yml"  );

        try {
            if (plugin.getResource(name + ".yml") == null) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                FileUtils.copyToFile(plugin.getResource(name + ".yml"), file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        fileConfig = YamlConfiguration.loadConfiguration(file);
        fileConfig.options().copyDefaults(true);
    }

    public FileConfiguration get() {
        return fileConfig != null ? fileConfig : YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration load(File file) {
        fileConfig = YamlConfiguration.loadConfiguration(file);

        return fileConfig;
    }

    public void save() {
        try {
            fileConfig.options().parseComments(comments);
            fileConfig.save(file);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Could not save file " + getFileName(), e);
        }
    }

    public void reload() {
        fileConfig = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), name + ".yml"));
    }

    public String getName() {
        return name;
    }

    public String getFileName() {
        return name + ".yml";
    }

    public File getFile() {
        return file;
    }

    public boolean useComments() {
        return comments;
    }

    public void setUseComments(boolean useComments) {
        this.comments = useComments;
    }
}