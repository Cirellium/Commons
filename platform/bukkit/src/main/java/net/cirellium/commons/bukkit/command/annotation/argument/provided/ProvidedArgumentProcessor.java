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
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import net.cirellium.commons.common.util.Processor;

public class ProvidedArgumentProcessor implements Processor<List<String>, ProvidedArguments> {

    public ProvidedArguments process(List<String> value) {
        Set<String> flags = new HashSet<>();
        
        List<String> arguments = value.stream()
            .filter(s -> !s.isEmpty())
            .collect(Collectors.toCollection(ArrayList::new));
        
        return new ProvidedArguments(arguments, flags);
    }
}