/**
* Copyright (C) 2023 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Sun Jan 01 2023 10:25:51
*
* DatabaseFile.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.bukkit.file.implementation;

import java.io.File;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.cirellium.commons.bukkit.CirelliumBukkitPlugin;
import net.cirellium.commons.bukkit.data.user.LoadableBukkitUser;
import net.kyori.adventure.text.Component;

/**
 * The implementation of the {@link AbstractPluginFile} class for database
 * files.
 * Provides many methods for writing reading database values.
 * 
 * @author Fear
 */
public class DatabaseFile extends AbstractPluginFile {

    /**
     * Creates a new database file.
     * 
     * @param plugin The bukkit plugin that the file belongs to.
     */
    public DatabaseFile(CirelliumBukkitPlugin plugin) {
        super(plugin);
    }

    public DatabaseFile(CirelliumBukkitPlugin plugin, String fileName) {
        super(plugin, fileName);
    }

    public DatabaseFile(File file, FileConfiguration fileConfig) {
        super(file, fileConfig);
    }

    public DatabaseFile(File file) {
        super(file);
    }

    public DatabaseFile(String path) {
        super(path);
    }

    /**
     * Sets the value of a given path.
     * 
     * @param path  The path to set the value at.
     * @param value The value to set.
     */
    public void set(String path, Object value) {
        fileConfig.set(path, value);
    }

    /**
     * ! Code smell and not object oriented, but Java doesn't allow switching over
     * different types without using tons of if-elses and instanceofs.
     * 
     * If possible, converts a given object to a string and sets the value at a
     * given path.
     * 
     * @param path  The path to set the value at.
     * @param value The value to set.
     */
    public void setValue(String path, Object value) {
        Objects.requireNonNull(path, "Cannot set a value at a null path");

        switch (value.getClass().getSimpleName()) {
            case "String":
            case "Integer":
            case "Double":
            case "Boolean":
            case "Float":
            case "Long":
                set(path, value);
                break;
            case "Optional":
                set(path, ((Optional<?>) value).orElse(null));
                break;
            case "Date":
                set(path, ((Date) value).getTime());
                break;
            case "Location":
                Map<String, Object> map = ((Location) value).serialize();
                for (String key : map.keySet()) {
                    set(path + "." + key, map.get(key));
                }
                break;
            case "UUID":
                set(path, value.toString());
                break;
            case "Sound":
                set(path, ((Sound) value).getKey().toString());
                break;
            case "Chunk":
                Chunk c = ((Chunk) value);
                set(path + ".x", c.getX());
                set(path + ".z", c.getZ());
                set(path + ".world", c.getWorld().getName());
                break;
            case "World":
                set(path, ((World) value).getName());
                break;
            case "Inventory":
                Inventory inv = (Inventory) value;
                set(path + ".size", inv.getSize());
                for (int i = 0; i < inv.getSize(); i++) {
                    if (inv.getItem(i) == null)
                        continue;

                    setValue(path + "." + i, inv.getItem(i));
                }
                break;
            case "ItemStack":
                set(path, ((ItemStack) value).serialize());
                break;
            default:
                set(path, value);
                break;
        }
    }

    public void saveUser(LoadableBukkitUser user) {
        set("users." + user.getUniqueId().toString() + ".name", user.getName());
        setValue("users." + user.getUniqueId().toString() + ".location", user.getPlayer().getLocation());
        setValue("users." + user.getUniqueId().toString() + ".inventory", user.getPlayer().getInventory());
        setValue("users." + user.getUniqueId().toString() + ".lastSaved", Instant.now().getEpochSecond());
    }

    /**
     * Gets the value of a given path as a {@link String}.
     * 
     * @param path The path to get the string from.
     * @return The {@link String} value at the given path.
     */
    public String getString(String path) {
        return fileConfig.getString(path);
    }

    /**
     * Gets the value of a given path as an int
     * 
     * @param path The path to get the int from.
     * @return The int value at the given path.
     */
    public int getInt(String path) {
        return fileConfig.getInt(path);
    }

    /**
     * Gets the value of a given path as a boolean.
     * 
     * @param path The path to get the boolean from.
     * @return The boolean value at the given path.
     */
    public boolean getBoolean(String path) {
        return fileConfig.getBoolean(path);
    }

    public List<String> getStringList(String path) {
        return fileConfig.getStringList(path);
    }

    public List<Integer> getIntList(String path) {
        return fileConfig.getIntegerList(path);
    }

    /**
     * Gets the value of a given path as a double.
     * 
     * @param path The path to get the double from.
     * @return The double value at the given path.
     */
    public double getDouble(String path) {
        return fileConfig.getDouble(path);
    }

    /**
     * Gets the value of a given path as a float.
     * 
     * @param path The path to get the float from.
     * @return The float value at the given path.
     */
    public float getFloat(String path) {
        return Float.valueOf(String.valueOf(getValue(path)));
    }

    /**
     * Gets the value of a given path as a long.
     * 
     * @param path The path to get the long from.
     * @return The long value at the given path.
     */
    public long getLong(String path) {
        return Long.valueOf(String.valueOf(getValue(path)));
    }

    /**
     * Gets the value of a given path as a {@link Date}.
     * 
     * @param path The path to get the {@link Date} from.
     * @return A {@link Date} from the long value at the given path.
     */
    public Date getDate(String path) {
        return new Date(getLong(path));
    }

    /**
     * Gets the value of a given path as a {@link Chunk}.
     * 
     * @param path The path to get the {@link Chunk} from.
     * @return A {@link Chunk} of the x and z values at the given path.
     */
    public Chunk getChunk(String path) {
        return Bukkit.getWorld(getString(path + ".world")).getChunkAt(getInt(path + ".x"), getInt(path + ".z"));
    }

    /**
     * Gets the value of a given path as a {@link UUID}.
     * 
     * @param path The path to get the {@link UUID} from.
     * @return The {@link UUID} value at the given path.
     */
    public UUID getUUID(String path) {
        String value = getString(path);
        return value != null ? UUID.fromString(value) : null;
    }

    /**
     * Gets the value of a given path as a {@link Sound}.
     * 
     * @param path The path to get the {@link Sound} from.
     * @return A {@link Sound} of the key at the given path.
     */
    public Sound getSound(String path) {
        return Sound.valueOf(getString(path));
    }

    /**
     * Gets the value of a given path as a {@link World}.
     * 
     * @param path The path to get the {@link World} from.
     * @return A {@link World} from the name at the given path.
     */
    public World getWorld(String path) {
        return Bukkit.getWorld(getString(path));
    }

    /**
     * Gets the value of a given path as a {@link Location}.
     * 
     * @param path The path to get the {@link Location} from.
     * @return A {@link Location} from the world, x, y, z, yaw and pitch values at
     *         the given path.
     */
    public Location getLocation(String path) {
        return new Location(Bukkit.getWorld(getString(path + ".world")), getDouble(path + ".x"), getDouble(path + ".y"),
                getDouble(path + ".z"), getFloat(path + ".yaw"), getFloat(path + ".pitch"));
    }

    /**
     * Gets the value of a given path as an {@link ItemStack}.
     * 
     * @param path The path to get the {@link ItemStack} from.
     * @return An {@link ItemStack}, from the given path.
     */
    public ItemStack getItemStack(String path) {
        return fileConfig.getItemStack(path);
    }

    /**
     * Gets the value of a given path as an {@link Inventory} and fills it with the
     * items from the given path.
     * 
     * @param path  The path to get the {@link Inventory} from.
     * @param size  The size of the {@link Inventory}.
     * @param title The title of the {@link Inventory}.
     * @return An {@link Inventory}, created from the given path.
     */
    public Inventory getInventory(String path, int size, String title) {
        Inventory inventory = Bukkit.createInventory(null, size,
                Component.text(ChatColor.translateAlternateColorCodes('&', title)).toString());
        for (int i = 0; i < size; i++) {
            inventory.setItem(i, getItemStack(path + "." + i));
        }
        return inventory;
    }

    /**
     * Gets the value of a given path as an {@link Inventory} and fills it with the
     * items from the given path.
     * 
     * @param path  The path to get the {@link Inventory} from.
     * @param title The title of the {@link Inventory}.
     * @return An {@link Inventory}, created from the given path.
     */
    public Inventory getInventory(String path, String title) {
        int size = getInt(path + ".size");
        Inventory inventory = Bukkit.createInventory(null, size,
                Component.text(ChatColor.translateAlternateColorCodes('&', title)).toString());

        for (int i = 0; i < size; i++) {
            inventory.setItem(i, getItemStack(path + "." + i));
        }

        return inventory;
    }

    /**
     * Gets all the keys from the {@code fileConfig} as a {@link String} and returns
     * them as a {@link Set}.
     * 
     * @return A {@link Set} containing all the keys from the {@code fileConfig}.
     */
    public Set<String> getKeys() {
        return fileConfig.getKeys(false);
    }

    /**
     * Gets all the sub-keys of a given path as a {@link String} and returns them as
     * a {@link Set}.
     * 
     * @param path The path to get all of the sub-keys from.
     * @return A {@link Set} containing all the sub-keys from the given path.
     */
    public Set<String> getKeys(String path) {
        ConfigurationSection section = fileConfig.getConfigurationSection(path);
        return section != null ? section.getKeys(false) : new HashSet<>();
    }
}