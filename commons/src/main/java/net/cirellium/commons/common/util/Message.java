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

import org.bukkit.command.CommandSender;

import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
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

public enum Message implements Sendable<CommandSender> {

    TEST("test", "<red>This is a test message with a test tag: <test>"),

    COMMAND_NO_PERMISSION("command.no-permission", "You do not have permission to use this command!", Color.RED),
    COMMAND_CONFIRMATION("command.confirmation", "Are you sure you want to do this?", Color.RED, Format.BOLD),

    COMMAND_PLAYER_ONLY("command.player-only", "This command can only be executed by a player!", Color.RED),
    COMMAND_CONSOLE_ONLY("command.console-only", "This command can only be executed by the console!", Color.RED),
    COMMAND_INVALID_USAGE("command.invalid-usage", "Invalid usage! Please use /<command> <usage>", Color.RED),
    ;

    public final static String MESSAGES_FILE_NAME = "messages";
    public final static String MESSAGES_PATH_PREFIX = "messages.";
    public final static Style DEFAULT = Style.style().color(NamedTextColor.GRAY).build();
    public final static Component PREFIX = MiniMessage.miniMessage()
            .deserialize("<dark_gray>[<gold>Cirellium<dark_gray>] <gray>");

    // public static ConfigFile<?> messagesFile;

    private final MiniMessage parser = MiniMessage.builder()
            .tags(TagResolver.builder()
                    .resolver(StandardTags.color())
                    .resolver(StandardTags.decorations())
                    // .resolver(this.someResolvers)
                    .resolver(TagResolver.resolver("test",
                            Tag.styling(HoverEvent.showText(Component.text("Test Hover!")))))
                    .build())
            .editTags(t -> t.resolver(TagResolver.resolver("clear", ParserDirective.RESET)))
            .build();

    private Component messageComponent;

    @Getter
    private final String messagePath, fallbackMessage;

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
            // this.messageComponent = parser.deserialize(toString());
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
    }

    public Component asComponent() {
        return PREFIX.append(parser.deserialize(toString()));
    }

    public Component getComponent() {
        return PREFIX.append(messageComponent);
    }

    @Override
    public String toString() {
        // return messagesFile.getValueAs(String.class, MESSAGES_PATH_PREFIX + messagePath).get();
        return null;
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
        this.messageComponent = parser.deserialize(toString(), Placeholder.parsed(placeholder, value));
        return this;
    }

    public void send(CommandSender sender) {
        sender.sendMessage(messageComponent);
    }

    // public static void setMessagesFile(ConfigFile<?> mFile) {
    //     messagesFile = mFile;
    // }

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