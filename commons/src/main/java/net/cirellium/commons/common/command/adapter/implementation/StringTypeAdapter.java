/**
* Copyright (C) 2023 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Tue Jan 10 2023 17:00:24
*
* StringArgumentType.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.common.command.adapter.implementation;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import net.cirellium.commons.common.command.adapter.ArgumentTypeAdapter;
import net.cirellium.commons.common.command.sender.CommandInvoker;

public class StringTypeAdapter implements ArgumentTypeAdapter<String> {

    @Override
    public String parse(CommandInvoker sender, String argument) {
        return argument;
    }

    @Override
    public List<String> tabComplete(CommandInvoker sender, Set<String> argumentSet, String argument) {
        return Collections.emptyList();
    }

    @Override
    public List<?> getPossibleResults() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(String.class);
    }
    
}