/**
* Copyright (C) 2022 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Sat Jan 21 2023 14:22:13
*
* MessagePlaceholder.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.common.message;

public record MessagePlaceholder(Object key, Object value) {

    public static MessagePlaceholder of(Object key, Object value) {
        return new MessagePlaceholder(key, value);
    }
}