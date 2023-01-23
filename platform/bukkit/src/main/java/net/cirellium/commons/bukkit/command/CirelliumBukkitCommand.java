package net.cirellium.commons.bukkit.command;

import java.util.List;

public interface CirelliumBukkitCommand {
    
    org.bukkit.command.Command getBukkitCommand();

    String getPermission();

    List<String> getAliases();

    

}