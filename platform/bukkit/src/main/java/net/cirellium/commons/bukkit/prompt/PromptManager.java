package net.cirellium.commons.bukkit.prompt;

import java.util.HashMap;
import java.util.Map;

import net.cirellium.commons.bukkit.data.user.AbstractBukkitUser;
import net.cirellium.commons.bukkit.prompt.exception.PromptException;
import net.cirellium.commons.bukkit.prompt.response.PromptResponder;
import net.cirellium.commons.bukkit.prompt.response.type.ResponseType;

public class PromptManager implements PromptFactory, PromptResponder {
    
    private final Map<AbstractBukkitUser, Prompt<?>> prompts;
    
    public PromptManager() {
        this.prompts = new HashMap<AbstractBukkitUser, Prompt<?>>();
    }

    public Prompt<?> getPrompt(AbstractBukkitUser user) {
        return prompts.get(user);
    }

    public boolean hasPrompt(AbstractBukkitUser user) {
        return prompts.containsKey(user);
    }

    @Override
    public void promptUser(AbstractBukkitUser user, Prompt<?> prompt) {
        if (hasPrompt(user)) return;

        
    }

    @Override
    public void respond(AbstractBukkitUser user, String input) {
        if (!hasPrompt(user)) return;

        Prompt<?> prompt = getPrompt(user);

        ResponseType expectedType = prompt.getResponseType();

        try {
            Object parsed = expectedType.getHandler().parse(input);

            prompt.respond(new PromptResponse(parsed));
            
        } catch (PromptException e) {
            
        }
    }
}