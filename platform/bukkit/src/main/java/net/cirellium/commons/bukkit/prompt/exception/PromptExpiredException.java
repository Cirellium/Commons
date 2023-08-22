package net.cirellium.commons.bukkit.prompt.exception;

import net.cirellium.commons.bukkit.prompt.Prompt;

/**
 * This exception indicates that a prompt was answered too late, because it has already expired.
 * 
 * @author Fear
 */
public class PromptExpiredException extends PromptException {

	public PromptExpiredException(Prompt<?> prompt) {
		super(prompt, "The prompt " + prompt.getUniqueId().toString() + " has expired");
	}
}