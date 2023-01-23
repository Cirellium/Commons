/**
* Copyright (C) 2023 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Tue Jan 10 2023 15:32:53
*
* ProvidedArgumentProcessor.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.bukkit.command.annotation.argument.provided;

import java.util.ArrayList;
import java.util.HashSet;

import net.cirellium.commons.common.util.Processor;

public class ProvidedArgumentProcessor implements Processor<String[], ProvidedArguments> {

    public ProvidedArguments process(String[] value) {
        HashSet<String> flags = new HashSet<>();
        ArrayList<String> arguments = new ArrayList<>();
        for (String s : value) {
            if (!s.isEmpty()) arguments.add(s);
        }
        return new ProvidedArguments(arguments, flags);
    }
}