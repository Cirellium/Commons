/**
* Copyright (C) 2023 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Tue Jan 10 2023 16:59:57
*
* BooleanArgumentType.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.common.command.annotation.adapter.implementation;

import java.util.List;
import java.util.Set;

import net.cirellium.commons.common.command.result.CommandExecutionResult;
import net.cirellium.commons.common.command.sender.CommandInvoker;

public class BooleanTypeAdapter implements CommandArgumentTypeAdapter<Boolean> {

    List<String> validBooleans = List.of("true", "false", "yes", "no", "y", "n", "0", "1");

    @Override
    public Boolean parse(CommandInvoker sender, String argument) {
        if (!isValidBoolean(argument)) {
            sender.sendMessage(CommandExecutionResult.ERROR_INVALID_TYPE.placeholder("type", "integer"));
            return null;
        }

        return Boolean.parseBoolean(argument);
    }
    
    @Override
    public List<String> tabComplete(CommandInvoker sender, Set<String> argumentSet, String argument) {
        return List.of("true", "false");
    }

    private boolean isValidBoolean(String argument) {
        return validBooleans.contains(argument.toLowerCase());
    }

    @Override
    public List<?> getPossibleResults() {
        return validBooleans;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Boolean.class) || clazz.equals(boolean.class);
    }
}