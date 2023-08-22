package net.cirellium.commons.bukkit.prompt;

import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import net.cirellium.commons.bukkit.prompt.event.PromptEvent;
import net.cirellium.commons.bukkit.prompt.exception.PromptException;
import net.cirellium.commons.bukkit.prompt.implementation.AnvilPrompt;
import net.cirellium.commons.bukkit.prompt.implementation.BookPrompt;
import net.cirellium.commons.bukkit.prompt.implementation.ChatPrompt;
import net.cirellium.commons.bukkit.prompt.implementation.InventoryPrompt;
import net.cirellium.commons.bukkit.prompt.implementation.SignPrompt;
import net.cirellium.commons.common.util.ClassTyped;

public enum PromptType implements ClassTyped {

    CHAT(ChatPrompt.class, (event, prompt) -> {
        if (!(event instanceof AsyncPlayerChatEvent || event instanceof PlayerCommandPreprocessEvent)) return null;
        
        
        return null;
    }),

    INVENTORY(InventoryPrompt.class, (event, prompt) -> {
        if (!(event instanceof InventoryClickEvent)) return null;

        return null;
    }),

    ANVIL(AnvilPrompt.class, (event, prompt) -> {
        if (!(event instanceof InventoryClickEvent)) return null;

        return null;
    }),

    BOOK(BookPrompt.class, (event, prompt) -> {
        return null;
    }),

    SIGN(SignPrompt.class, (event, prompt) -> {
        return null;
    });

    private final Class<?> type;
    private final PromptEventHandler<?> handler;

    PromptType(Class<?> type, PromptEventHandler<?> handler) {
        this.type = type;
        this.handler = handler;
    }

    @Override
    public Class<?> getType() {
        return type;
    }

    public PromptEventHandler<?> getHandler() {
        return handler;
    }

    public interface PromptEventHandler<E extends PromptEvent> {
        E handle(Event bukkitEvent, Prompt<?> prompt) throws PromptException;
    }
}