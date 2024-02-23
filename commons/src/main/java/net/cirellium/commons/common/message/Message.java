/**
* Copyright (C) 2022 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Sat Jan 21 2023 14:22:02
*
* Message.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.common.message;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.ParserDirective;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.minimessage.tag.standard.StandardTags;

public final class Message {

    public static final String PLACEHOLDER_BOUNDS = "<>";

    private static final MessageProvider DEFAULT_PROVIDER = MessageProvider.defaultMessageProvider;

    @Getter
    private final MessageKey messageKey;

    @Getter
    private List<MessagePlaceholder> placeholders;

    private Component component;

    private static final MiniMessage parser = MiniMessage.builder()
            .tags(TagResolver.builder()
                    .resolver(StandardTags.color())
                    .resolver(StandardTags.decorations())
                    // .resolver(this.someResolvers)
                    .resolver(TagResolver.resolver("test",
                            Tag.styling(HoverEvent.showText(Component.text("Test Hover!")))))
                    .build())
            .editTags(t -> t.resolver(TagResolver.resolver("clear", ParserDirective.RESET)))
            .build();

    public Message(MessageKey messageKey, @Nullable MessagePlaceholder... placeholders) {
        this(messageKey, Arrays.asList(placeholders));
    }

    public Message(MessageKey messageKey, @Nullable List<MessagePlaceholder> placeholders) {
        this.messageKey = messageKey;
        this.placeholders = placeholders;

        placeholders.forEach(placeholder -> placeholder(placeholder.key(), placeholder.value()));
    }

    public static Message of(MessageKey messageKey, @Nullable MessagePlaceholder... placeholders) {
        return new Message(messageKey, placeholders);
    }

    public Component replacePlaceholders(String msg) {
        if(placeholders == null || placeholders.isEmpty()) return parser.deserialize(msg);

        for(MessagePlaceholder placeholder : placeholders) {
            placeholder(placeholder.key(), placeholder.value());
        }

        return getComponent();
    }

    public Message placeholder(Object key, Object value) {
        this.component = parser.deserialize(getString(), Placeholder.parsed(String.valueOf(key), String.valueOf(value)));
        return this;
    }

    public String getString() {
        return DEFAULT_PROVIDER.provide(messageKey);
    }

    public String getStringReplaced() {
        return replacePlaceholders(getString()).toString();
    }

    public Component getComponent() {
        return Component.text(Message.of(MessageKey.Default.PREFIX).getString()).append(component);
    }
}