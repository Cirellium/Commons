/**
* Copyright (C) 2023 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Fri Jan 13 2023 16:46:07
*
* Message.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.common.util;

import javax.annotation.Nullable;

import lombok.Getter;
import lombok.Setter;
import net.cirellium.commons.common.command.sender.CommandInvoker;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.StyleBuilderApplicable;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.ParserDirective;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.minimessage.tag.standard.StandardTags;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public enum Message implements Sendable<CommandInvoker> {

    TEST("test", "<red>This is a test message with a test tag: <test>"),

    COMMAND_USAGE("command.usage", "Usage: <usage>", Color.RED),
    COMMAND_CONFIRMATION("command.confirmation", "Are you sure you want to <action>?", Color.RED, Format.BOLD),

    COMMAND_ERROR_NO_PERMISSION("command.error.no-permission",
            "You do not have permission to use this command! (<permission>)",
            Color.RED),
    COMMAND_ERROR_PLAYER_ONLY("command.error.player-only", "This command can only be executed by a player!", Color.RED),
    COMMAND_ERROR_CONSOLE_ONLY("command.error.console-only", "This command can only be executed by the console!",
            Color.RED),
    COMMAND_ERROR_INVALID_USAGE("command.error.invalid-usage", "Invalid usage! Please use <usage>", Color.RED),
    COMMAND_ERROR_PARSE_ARG("command.error.parse-argument", "An error occurred while parsing argument <arg>",
            Color.RED),
    COMMAND_ERROR_TOO_MANY_ARGUMENTS("command.error.too-many-arguments", "Too many arguments!", Color.RED),
    COMMAND_ERROR_NOT_ENOUGH_ARGUMENTS("command.error.not-enough-arguments", "Not enough arguments!", Color.RED),

    ;

    public final static String MESSAGES_FILE_NAME = "messages";
    public final static String MESSAGES_PATH_PREFIX = "messages.";
    public final static Style DEFAULT = Style.style().color(NamedTextColor.GRAY).build();
    public final static Component PREFIX = MiniMessage.miniMessage()
            .deserialize("<dark_gray>[<gold>Cirellium<dark_gray>] <gray>");
    public final static String LEGACY_PREFIX = LegacyComponentSerializer.legacy('§').serialize(Message.PREFIX);

    // public static ConfigFile<?> messagesFile;

    private final MiniMessage parser = MiniMessage.builder()
            .tags(TagResolver.builder()
                    .resolver(StandardTags.color())
                    .resolver(StandardTags.decorations())
                    // .resolver(this.someResolvers)
                    // .resolver(TagResolver.resolver("test",
                    // Tag.styling(HoverEvent.showText(Component.text("Test Hover!")))))
                    .build())
            .editTags(t -> t.resolver(TagResolver.resolver("clear", ParserDirective.RESET)))
            .build();

    private Component messageComponent;

    @Getter
    private final String messagePath, fallbackMessage, legacyString;

    @Getter
    @Setter
    @Nullable
    private Style messageStyle;

    Message(String mP, String fM) {
        this(mP, fM, null);
    }

    Message(String messagePath, String fallbackMessage, Color color, Format... format) {
        this.messagePath = messagePath;
        this.fallbackMessage = fallbackMessage;
        if (format == null || color == null) {
            this.messageComponent = parser.deserialize(toString());
            this.legacyString = LegacyComponentSerializer.legacy('§').serialize(messageComponent);
            return;
        }
        TextDecoration[] decorations = new TextDecoration[format.length];
        for (int i = 0; i < format.length; i++) {
            decorations[i] = format[i].toTextDecoration();
        }
        this.messageStyle = Style.style()
                .color(color.getColor())
                .decorate(decorations)
                .build();
        this.messageComponent = Component.text().content(fallbackMessage).style(messageStyle).build();
        this.legacyString = LegacyComponentSerializer.legacy('§').serialize(messageComponent);
    }

    public Component asComponent() {
        return PREFIX.append(parser.deserialize(toString()));
    }

    public Component getComponent() {
        return PREFIX.append(messageComponent);
    }

    @Override
    public String toString() {
        // return messagesFile.getValueAs(String.class, MESSAGES_PATH_PREFIX +
        // messagePath).get();
        return fallbackMessage;
    }

    // ! TODO test this method
    public Message tag(String tag, StyleBuilderApplicable style) {
        MiniMessage parser = MiniMessage.builder()
                .tags(TagResolver.builder()
                        .resolver(StandardTags.color())
                        .resolver(StandardTags.decorations())
                        .resolver(TagResolver.resolver(tag, Tag.styling(style)))
                        .build())
                .editTags(t -> t.resolver(TagResolver.resolver("clear", ParserDirective.RESET)))
                .build();

        this.messageComponent = parser.deserialize(toString());

        return this;
    }

    public Message placeholder(String placeholder, String value) {
        this.messageComponent = parser
                .deserialize(
                        toString(),
                        Placeholder.parsed(placeholder, value));
        return this;
    }

    public Message legacyPlaceholder(String placeholder, String value) {
        legacyString.replace(placeholder, value);
        return this;
    }

    public String toLegacyString() {
        return LegacyComponentSerializer.legacy('§').serialize(messageComponent);
    }

    public void send(CommandInvoker sender) {
        sender.sendMessage(toLegacyString());
    }

    public static enum Color {
        BLACK(NamedTextColor.BLACK),
        DARK_BLUE(NamedTextColor.DARK_BLUE),
        DARK_GREEN(NamedTextColor.DARK_GREEN),
        DARK_AQUA(NamedTextColor.DARK_AQUA),
        DARK_RED(NamedTextColor.DARK_RED),
        DARK_PURPLE(NamedTextColor.DARK_PURPLE),
        GOLD(NamedTextColor.GOLD),
        GRAY(NamedTextColor.GRAY),
        DARK_GRAY(NamedTextColor.DARK_GRAY),
        BLUE(NamedTextColor.BLUE),
        GREEN(NamedTextColor.GREEN),
        AQUA(NamedTextColor.AQUA),
        RED(NamedTextColor.RED),
        LIGHT_PURPLE(NamedTextColor.LIGHT_PURPLE),
        YELLOW(NamedTextColor.YELLOW),
        WHITE(NamedTextColor.WHITE);

        @Getter
        private final NamedTextColor color;

        Color(NamedTextColor color) {
            this.color = color;
        }

        public Style toStyle() {
            return Style.style().color(this.color).build();
        }
    }

    public static enum Format {
        BOLD(TextDecoration.BOLD),
        ITALIC(TextDecoration.ITALIC),
        UNDERLINED(TextDecoration.UNDERLINED),
        STRIKETHROUGH(TextDecoration.STRIKETHROUGH),
        OBFUSCATED(TextDecoration.OBFUSCATED);

        @Getter
        private final TextDecoration format;

        Format(TextDecoration format) {
            this.format = format;
        }

        public Style toStyle() {
            return Style.style().decorate(this.format).build();
        }

        public TextDecoration toTextDecoration() {
            return this.format;
        }
    }
}