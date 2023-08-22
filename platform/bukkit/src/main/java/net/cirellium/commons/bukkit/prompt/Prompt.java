package net.cirellium.commons.bukkit.prompt;

import java.time.temporal.TemporalAccessor;
import java.util.UUID;

import org.checkerframework.checker.nullness.qual.Nullable;

import net.cirellium.commons.bukkit.data.user.AbstractBukkitUser;
import net.cirellium.commons.bukkit.prompt.response.PromptResponder.PromptResponse;
import net.cirellium.commons.bukkit.prompt.response.type.ResponseType;
import net.cirellium.commons.bukkit.prompt.response.type.ResponseTypeHandler;

public abstract class Prompt<T> {

    private final UUID uniqueId;
    
    protected AbstractBukkitUser recipient;
    
    private ResponseType responseType;
    private final ResponseTypeHandler<T> responseTypeHandler;

    private @Nullable PromptResponse response;
    
    protected String promptedContent;

    private boolean completed;
    private TemporalAccessor expired;
    
    @SuppressWarnings("unchecked")
    public Prompt(final ResponseType responseType) {
        this.uniqueId = UUID.randomUUID();
        this.recipient = null;
        this.response = null;
        this.responseType = responseType;
        this.responseTypeHandler = (ResponseTypeHandler<T>) ResponseTypeHandler.getHandler(responseType);
        this.promptedContent = null;
    }

    public abstract void sendTo(AbstractBukkitUser recipient);

    public UUID getUniqueId() {
        return uniqueId;
    }

    public AbstractBukkitUser getRecipient() {
        return recipient;
    }
    
    public ResponseType getResponseType() {
        return responseType;
    }
    
    public ResponseTypeHandler<T> getResponseTypeHandler() {
        return responseTypeHandler;
    }

    public boolean isCompleted() {
        return completed;
    }

    // TODO: Implement expiring prompts
    public boolean isExpired() {
        return expired != null;
    }

    public void respond(PromptResponse response) {
        this.response = response;
    }

    public interface Builder {
        
        Builder receiver(AbstractBukkitUser user);

        Builder responseType(ResponseType type);

    }
}