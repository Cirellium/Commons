package net.cirellium.commons.bukkit.prompt.response;

import net.cirellium.commons.bukkit.data.user.AbstractBukkitUser;

public interface PromptResponder {
    
    void respond(AbstractBukkitUser user, String response);

    public record PromptResponse(Object parsedResponse) {}

}