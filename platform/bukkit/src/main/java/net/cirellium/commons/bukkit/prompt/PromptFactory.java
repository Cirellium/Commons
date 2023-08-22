package net.cirellium.commons.bukkit.prompt;

import net.cirellium.commons.bukkit.data.user.AbstractBukkitUser;

public interface PromptFactory {
    
    void promptUser(AbstractBukkitUser user, Prompt<?> prompt);

}