/**
* Copyright (C) 2023 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Thu Jan 05 2023 10:20:02
*
* ArgumentTypeHandler.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.common.command.annotation.adapter;

import java.util.List;
import java.util.Set;

import net.cirellium.commons.common.command.sender.CommandInvoker;
import net.cirellium.commons.common.util.Message;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public interface ArgumentTypeAdapter<T> {
    
    T parse(CommandInvoker sender, String argument);

    List<String> tabComplete(CommandInvoker sender, Set<String> argumentSet, String argument);

    List<?> getPossibleResults();

    boolean supports(Class<?> clazz);

    default void handleException(CommandInvoker sender, String source) {
        Component errorMessage = Message.COMMAND_ERROR_PARSE_ARG.placeholder("arg", source).getComponent();

        sender.sendMessage(LegacyComponentSerializer.legacy('§').serialize(errorMessage));
    }
    
}