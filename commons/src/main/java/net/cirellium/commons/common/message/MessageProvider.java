/**
* Copyright (C) 2022 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Sat Jan 21 2023 14:22:17
*
* MessageProvider.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.common.message;

import java.util.NoSuchElementException;

import net.cirellium.commons.common.file.PluginFile;
import net.cirellium.commons.common.util.Provider;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public interface MessageProvider<M> extends Provider<M, MessageKey> {

    M provide(MessageKey key);

    public interface StringMessageProvider extends MessageProvider<String> {
    
        @Override
        String provide(MessageKey key);

        StringMessageProvider defaultMessageProvider = (key) -> key.getFallbackValue();

        StringMessageProvider legacyMessageProvider = (key) -> LegacyComponentSerializer.legacy('§').serialize(key.toMessage().getComponent());

    }
    public interface ComponentMessageProvider extends MessageProvider<Component> {
        
        @Override
        Component provide(MessageKey key);

        ComponentMessageProvider defaultMessageProvider = (key) -> Message.PARSER.deserialize(key.getFallbackValue());
        
        ComponentMessageProvider componentMessageProvider = (key) -> {
            Style style = Message.PARSER.deserialize(key.getFallbackValue()).style();
            
            return Component.text().content(key.getFallbackValue()).style(style).build();
        };
    }


    public class YmlMessageProvider implements StringMessageProvider {
        private final PluginFile<?> ymlFile;

        public YmlMessageProvider(PluginFile<?> ymlFile) {
            this.ymlFile = ymlFile;
        }

        @Override
        public String provide(MessageKey key) {
            String message;
            try {
                message = ymlFile.getValueAs(String.class, key.getKey()).get();
            } catch (NoSuchElementException e) {
                message = key.getFallbackValue();
            }
            return message;
        }
    }
}