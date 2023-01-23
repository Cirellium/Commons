package net.cirellium.commons.bukkit.data.user;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.cirellium.commons.common.data.user.LoadableUser;

public interface LoadableBukkitUser extends LoadableUser {

    Player getPlayer();

    void updatePlayer(Player newPlayer);

    void onJoin(PlayerJoinEvent event);

    void onLeave(PlayerQuitEvent event);
    
    // ! Some additonal data methods to get Cirellium data from the player.
}