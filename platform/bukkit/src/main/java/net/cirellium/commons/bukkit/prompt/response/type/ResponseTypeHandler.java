package net.cirellium.commons.bukkit.prompt.response.type;

import net.cirellium.commons.bukkit.prompt.exception.PromptException;

public interface ResponseTypeHandler<T> {

    T parse(String input) throws PromptException;

    boolean supports(final Class<?> type);

    static ResponseTypeHandler<?> getHandler(final ResponseType responseType) {
        return responseType.getHandler();
    }
}