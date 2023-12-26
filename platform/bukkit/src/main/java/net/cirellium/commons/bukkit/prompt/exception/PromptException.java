package net.cirellium.commons.bukkit.prompt.exception;

import net.cirellium.commons.bukkit.prompt.Prompt;

/**
 * Any exception that is related to prompts.
 * 
 * @author Fear
 */
public class PromptException extends RuntimeException {
    
    /**
     * The dedicated {@link Prompt}
     */
    private final Prompt<?> prompt;

    /**
     * Create a new {@link PromptException} with a provided message.
     * 
     * @param prompt The dedicated prompt
     * @param message The message providing additional information
     */
    public PromptException(Prompt<?> prompt, String message) {
        super(message);

        this.prompt = prompt;
    }

    /**
     * Returns the dedicated prompt.
     * 
     * @return The dedicated prompt
     */
    public Prompt<?> getPrompt() {
        return this.prompt;
    }
}