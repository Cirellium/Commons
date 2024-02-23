/**
* Copyright (C) 2023 Cirellium Network - All Rights Reserved
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
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import lombok.Getter;
import lombok.NonNull;
import net.cirellium.commons.bukkit.CirelliumBukkitPlugin;
import net.cirellium.commons.bukkit.utils.BukkitPlatform;
import net.cirellium.commons.common.file.PluginFile;
import net.cirellium.commons.common.logger.SimpleCirelliumLogger;
import net.cirellium.commons.common.service.ServiceType;

/**
 * This class an abstract implementation of a plugin file.
 * It uses the {@link FileConfiguration} class as well as the {@link YamlConfiguration} class to load and save the file.
 */
public abstract class AbstractPluginFile implements PluginFile<FileConfiguration> {

    private final CirelliumBukkitPlugin plugin;

    public File file;

    @Getter
    @NonNull
    public FileConfiguration fileConfig;

    public Logger logger;

    public String name;

    public boolean comments;

    public AbstractPluginFile(String path) {
        this(new File(path));
    }

    public AbstractPluginFile(File file) {
        this(file, YamlConfiguration.loadConfiguration(file));
    }

    public AbstractPluginFile(File file, FileConfiguration fileConfig) {
        this.file = file;
        this.fileConfig = fileConfig;
        this.fileConfig.options().copyDefaults(true);
        this.name = file.getName().replace(".yml", "");
        this.logger = new SimpleCirelliumLogger(BukkitPlatform.INSTANCE, file.getName());
        this.comments = true;

        this.plugin = null;

        create();
    }

    public AbstractPluginFile(CirelliumBukkitPlugin plugin) {
        plugin.getConfig().options().copyDefaults(true);
        plugin.saveConfig();

        this.plugin = plugin;
        this.name = "config";
        this.file = new File(plugin.getDataFolder(), name + ".yml");
        this.logger = new SimpleCirelliumLogger(BukkitPlatform.INSTANCE, plugin.getService(ServiceType.FILE).getName() + " » " + file.getName());
        this.comments = true;

        create();
    }

    public AbstractPluginFile(CirelliumBukkitPlugin plugin, String fileName) {
        this.plugin = plugin;
        this.name = fileName;
        this.file = new File(plugin.getDataFolder(), fileName + ".yml");
        this.logger = new SimpleCirelliumLogger(BukkitPlatform.INSTANCE, plugin.getService(ServiceType.FILE).getName() + " » " + file.getName());
        this.comments = true;

        create();
    }

    public void create() {
        if (file.exists()) {
            load(file);
            return;
        }

        logger.info("§bCreating new file " + name + ".yml");

        try {
            if (plugin.getResource(name + ".yml") != null) {
                FileUtils.copyToFile(plugin.getResource(name + ".yml"), file);
                load();
                return;
            }
            file.createNewFile();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration load() {
        fileConfig = YamlConfiguration.loadConfiguration(file);
        fileConfig.options().copyDefaults(true);
        fileConfig.options().parseComments(comments);        

        return fileConfig;
    }

    public FileConfiguration load(File file) {
        fileConfig = YamlConfiguration.loadConfiguration(file);
        fileConfig.options().copyDefaults(true);
        fileConfig.options().parseComments(comments);

        return fileConfig;
    }

    public void save() {
        try {
            fileConfig.options().parseComments(comments);
            fileConfig.save(file);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Could not save file " + file.getName(), e);
        }
    }

    public void reload() {
        fileConfig = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), name + ".yml"));
    }

    public File getFile() {
        return file;
    }

    public FileConfiguration get() {
        return fileConfig;
    }

    public boolean useComments() {
        return comments;
    }

    public void setUseComments(boolean useComments) {
        this.comments = useComments;
    }

        /**
     * Gets the value of a given path.
     * @param path The path to get the value from.
     * @return An object of the value at the given path.
     */
    public Object getValue(String path) {
        return fileConfig.get(path);
    }

    /**
     * Gets the value of a given path as a specific type.
     * @param c The class of the type to get the value as.
     * @param path The path to get the value from.
     * @return An optional of the value at the given path, or an empty optional if the value is not of the given type.
     */
    public <T> Optional<T> getValueAs(Class<T> c, String path) {
        Object object = getValue(path);

        if(object == null) return Optional.empty();

        return (c.isInstance(object)) ? Optional.of(c.cast(object)) : Optional.empty();
    }
}