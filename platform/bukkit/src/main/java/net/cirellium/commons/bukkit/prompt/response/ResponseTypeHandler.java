package net.cirellium.commons.bukkit.prompt.response;

public interface ResponseTypeHandler<T> {

    T parse(String input);

    boolean supports(final Class<?> type);

    static ResponseTypeHandler<?> getHandler(final ResponseType responseType) {
        return responseType.getHandler();
    }
}