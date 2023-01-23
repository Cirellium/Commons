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

import javax.annotation.Nullable;

public enum MessageKey {
    
    TEST("test", "<red>This is a test message with a test tag: <test>"),

    PREFIX("prefix", "<dark_gray>[<gold>Cirellium<dark_gray>]"),
    ;

    private String key;

    private @Nullable String fallback;

    MessageKey(String key, @Nullable String fallback) {
        this.key = key;
        this.fallback = fallback;
    }

    public String getKey() {
        return key;
    }

    public @Nullable String getFallbackValue() {
        return fallback;
    }

}