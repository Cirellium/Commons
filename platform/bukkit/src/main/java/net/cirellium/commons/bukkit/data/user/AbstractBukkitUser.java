/**
* Copyright (C) 2022 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Mon Jan 23 2023 11:51:33
*
* AbstractBukkitUser.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.bukkit.data.user;

import java.util.UUID;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import lombok.Getter;
import net.cirellium.commons.bukkit.CirelliumBukkitPlugin;
import net.cirellium.commons.bukkit.file.implementation.DatabaseFile;
import net.cirellium.commons.common.data.user.AbstractCirelliumUser;
import net.cirellium.commons.common.file.PluginFile;

public abstract class AbstractBukkitUser extends AbstractCirelliumUser implements LoadableBukkitUser {

    @Getter
    private final transient CirelliumBukkitPlugin<?> plugin;

    private @Nullable Player player;

    public AbstractBukkitUser(CirelliumBukkitPlugin<?> plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public void saveUser() {
        
        
    }

    @Override
    public void loadUser() {
        
        
    }

    @Override
    public void unloadUser() {
        
        
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public String getName() {
        return getPlayer().getName();
    }

    @Override
    public void saveTo(PluginFile<?> file) {
        if (file == null) {
            throw new IllegalArgumentException("Cannot write to null file!");
        }

        if (file instanceof DatabaseFile<?> db) {
            db.saveUser(this);
        }
    }

    @Override
    public Player getPlayer() {
        return (player != null) ? player : plugin.getServer().getPlayer(getUUID());
    }

    @Override
    public void updatePlayer(Player newPlayer) {
        this.player = newPlayer;
    }

    @Override
    public abstract void onJoin(PlayerJoinEvent event);

    @Override
    public abstract void onLeave(PlayerQuitEvent event);
    
}