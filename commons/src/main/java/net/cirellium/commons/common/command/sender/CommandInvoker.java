package net.cirellium.commons.common.command.sender;

import net.cirellium.commons.common.message.Message;
import net.cirellium.commons.common.message.MessageKey;
import net.cirellium.commons.common.message.MessageKey.Default;
import net.cirellium.commons.common.message.MessageProvider.ComponentMessageProvider;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;

public interface CommandInvoker {

    default void sendMessage(MessageKey messageKey) {
        sendMessage(ComponentMessageProvider.defaultMessageProvider.provide(messageKey));
    }

    default void sendMessage(Message... messages) {
        Component finalComponent = Default.PREFIX.toMessage().getComponent();

        for (Message message : messages) {
            finalComponent = finalComponent.appendSpace().append(message.getComponent());
        }

        sendMessage(finalComponent);
    }

    void sendMessage(ComponentLike message);

    boolean hasPermission(String permission);

    boolean isPlayer();
}