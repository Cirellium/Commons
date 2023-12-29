/**
* Copyright (C) 2023 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Tue Jan 10 2023 17:00:05
*
* FloatArgumentType.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.bukkit.command.annotation.adapter.implementation;

import java.util.List;
import java.util.Set;

import org.bukkit.command.CommandSender;

import net.cirellium.commons.bukkit.command.annotation.adapter.ArgumentTypeAdapter;

public class FloatTypeAdapter implements ArgumentTypeAdapter<Float> {

    @Override
    public Float parse(CommandSender sender, String argument) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'parse'");
    }

    @Override
    public List<String> tabComplete(CommandSender sender, Set<String> argumentSet, String argument) {
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
        return clazz.equals(Float.class) || clazz.equals(float.class);
    }
    
}