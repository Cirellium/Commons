/**
* Copyright (C) 2023 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Tue Jan 10 2023 17:00:00
*
* DoubleArgumentType.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.common.command.adapter.implementation;

import java.util.List;
import java.util.Set;

import net.cirellium.commons.common.command.adapter.ArgumentTypeAdapter;
import net.cirellium.commons.common.command.sender.CommandInvoker;

public class DoubleTypeAdapter implements ArgumentTypeAdapter<Double> {

    @Override
    public Double parse(CommandInvoker sender, String argument) {
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
        return clazz.equals(Double.class) || clazz.equals(double.class);
    }
    
}