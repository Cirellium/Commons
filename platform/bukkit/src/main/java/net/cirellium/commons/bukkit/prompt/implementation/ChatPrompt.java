package net.cirellium.commons.bukkit.prompt.implementation;

import net.cirellium.commons.bukkit.data.user.AbstractBukkitUser;
import net.cirellium.commons.bukkit.prompt.Prompt;
import net.cirellium.commons.bukkit.prompt.response.type.ResponseType;

public class ChatPrompt<T> extends Prompt<T> {

	public ChatPrompt(ResponseType responseType) {
		super(responseType);
	}

	@Override
	public void sendTo(AbstractBukkitUser recipient) {
		super.recipient = recipient;

		
	}
    
}