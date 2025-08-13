/**
* Copyright (C) 2023 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Sun Jan 01 2023 10:26:22
*
* ConfigFile.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.bukkit.file.implementation;

import java.io.File;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

import net.cirellium.commons.bukkit.CirelliumBukkitPlugin;

/**
 * @author Fear
 * 
 * The implementation of the {@link AbstractPluginFile} class for config files.
 * Provides some simple methods for reading config values.
 */
public class ConfigFile extends AbstractPluginFile {

    /**
     * Creates a new config file.
     * @param plugin The bukkit plugin that the file belongs to.
     */
    public ConfigFile(CirelliumBukkitPlugin plugin) {
        super(plugin);
    }

    /**
     * Creates a new config file.
     * @param plugin The bukkit plugin that the file belongs to.
     * @param fileName The name of the file.
     */
    public ConfigFile(CirelliumBukkitPlugin plugin, String fileName) {
        super(plugin, fileName);
    }

    public ConfigFile(File file, FileConfiguration fileConfig) {
        super(file, fileConfig);
    }

    public ConfigFile(File file) {
        super(file);
    }

    public ConfigFile(String path) {
        super(path);
    }

    /**
     * Returns a string value from a given key.
     * @param key The key to get the value from.
     * @return The string value of the key.
     */
    public String getString(String key) {
        return fileConfig.getString(key);
    }

    /**
     * Returns an integer value from a given key.
     * @param key The key to get the value from.
     * @return The int value of the key.
     */
    public int getInt(String key) {
        return fileConfig.getInt(key);
    }

    /**
     * Returns a double value from a given key.
     * @param key The key to get the value from.
     * @return The double value of the key.
     */
    public double getDouble(String key) {
        return fileConfig.getDouble(key);
    }

    /**
     * Returns a boolean value from a given key.
     * @param key The key to get the value from.
     * @return The boolean value of the key.
     */
    public boolean getBoolean(String key) {
        return fileConfig.getBoolean(key);
    }

    /**
     * Returns a list of strings from a given key.
     * @param key The key to get the value from.
     * @return The string list of the key.
     */
    public List<String> getList(String key) {
        return fileConfig.getStringList(key);
    }
}