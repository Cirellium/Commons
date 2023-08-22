package net.cirellium.commons.bukkit.prompt.exception;

import net.cirellium.commons.bukkit.prompt.Prompt;

/**
 * This exception indicates that a user has already been prompted.
 * 
 * @author Fear
 */
public class UserAlreadyPromptedException extends PromptException {

	public UserAlreadyPromptedException(Prompt<?> prompt) {
		super(prompt, "The user has already been prompted");
	}   
}