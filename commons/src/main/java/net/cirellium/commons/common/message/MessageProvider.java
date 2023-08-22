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

public interface MessageProvider extends Provider<String, MessageKey> {
    
    String provide(MessageKey key);

    MessageProvider defaultMessageProvider = new MessageProvider() { 
        @Override
        public String provide(MessageKey key) {
            return key.getFallbackValue();
        }

    };

    public class YmlMessageProvider implements MessageProvider {
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