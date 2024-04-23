/**
* Copyright (C) 2023 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Tue Jan 10 2023 16:59:44
*
* OfflinePlayerArgumentType.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.bukkit.command.annotation.adapter;

import java.util.List;
import java.util.Set;

import org.bukkit.OfflinePlayer;

import net.cirellium.commons.common.command.annotation.adapter.ArgumentTypeAdapter;
import net.cirellium.commons.common.command.sender.CommandInvoker;

public class OfflinePlayerTypeAdapter implements ArgumentTypeAdapter<OfflinePlayer> {

    @Override
    public OfflinePlayer parse(CommandInvoker sender, String argument) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'parse'");
    }

    @Override
    public List<String> tabComplete(CommandInvoker sender, Set<String> argumentSet, String argument) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'tabComplete'");
    }

    @Override
    public List<?> getPossibleResults() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPossibleResults'");
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(OfflinePlayer.class);
    }
    

}
