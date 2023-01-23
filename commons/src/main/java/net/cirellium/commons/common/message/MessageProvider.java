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

import net.cirellium.commons.common.util.Processor;

public abstract class MessageProvider implements Processor<MessageKey, String> {
    
    public abstract String get(MessageKey key);
    
    @Override
    public String process(MessageKey key) {
        return get(key);
    }
}