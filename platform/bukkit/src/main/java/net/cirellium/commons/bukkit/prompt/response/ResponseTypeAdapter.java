package net.cirellium.commons.bukkit.prompt.response;

import net.cirellium.commons.common.command.annotation.adapter.ArgumentTypeAdapter;

public interface ResponseTypeAdapter<T> extends ArgumentTypeAdapter<T, PromptResponder> {

    static ResponseTypeAdapter<?> getHandler(final ResponseType responseType) {
        return responseType.getHandler();
    }
}