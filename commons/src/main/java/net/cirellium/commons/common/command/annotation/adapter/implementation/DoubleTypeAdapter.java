/**
* Copyright (C) 2023 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Tue Jan 10 2023 17:00:00
*
* DoubleArgumentType.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.common.command.annotation.adapter.implementation;

import java.util.List;
import java.util.Set;

import net.cirellium.commons.common.command.sender.CommandInvoker;

/**
 * A type adapter for double arguments
 */
public class DoubleTypeAdapter implements CommandArgumentTypeAdapter<Double> {

    @Override
    public Double parse(CommandInvoker sender, String argument) {
        try {
            return Double.parseDouble(argument);
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
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