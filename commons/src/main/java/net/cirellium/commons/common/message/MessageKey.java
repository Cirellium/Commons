/**
* Copyright (C) 2022 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Sat Jan 21 2023 14:22:09
*
* MessageKey.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.common.message;

import org.checkerframework.checker.nullness.qual.Nullable;

public interface MessageKey {

    String getKey();

    String getFallbackValue();

    Enum<?> getEnum();

    default Message toMessage() {
        return new Message(this);
    }

    default Message placeholder(String key, String value) {
        return new Message(this, new MessagePlaceholder(key, value));
    }

    public enum Default implements MessageKey {
        TEST("test", "<red>This is a test message with a test tag: <test>"),

        PREFIX("prefix", "<dark_gray>[<gold>Cirellium<dark_gray>]"),

        ;

        private String key;

        private @Nullable String fallback;

        Default(String key, @Nullable String fallback) {
            this.key = key;
            this.fallback = fallback;
        }

        public String getKey() {
            return key;
        }

        public @Nullable String getFallbackValue() {
            return fallback;
        }

        @Override
        public Enum<?> getEnum() {
            return this;
        }

        @Override
        public String toString() {
            return getKey();
        }
    }
}