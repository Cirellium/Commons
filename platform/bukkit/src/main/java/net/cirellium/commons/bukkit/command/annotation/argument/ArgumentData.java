/**
* Copyright (C) 2023 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Tue Jan 10 2023 14:12:17
*
* ArgumentData.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.bukkit.command.annotation.argument;

import java.beans.ConstructorProperties;
import java.util.Set;

@SuppressWarnings("rawtypes")
public class ArgumentData {
    
    private final String name, defaultValue;

    private final boolean wildcard;

    private final int methodIndex;

    private final Set<String> tabCompleteFlags;

    private final Class<?> type;

    private final Class<? extends ArgumentTypeHandler> argumentType;

    @ConstructorProperties({"name", "defaultValue", "type", "wildcard", "methodIndex", "tabCompleteFlags", "argumentType"})
    public ArgumentData(String name, String defaultValue, Class<?> type, boolean wildcard, int methodIndex, Set<String> tabCompleteFlags, Class<? extends ArgumentTypeHandler> argumentType) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.type = type;
        this.wildcard = wildcard;
        this.methodIndex = methodIndex;
        this.tabCompleteFlags = tabCompleteFlags;
        this.argumentType = argumentType;
    }

    public String getName() { return this.name; }

    public String getDefaultValue() { return this.defaultValue; }

    public boolean isWildcard() { return this.wildcard; }

    public int getMethodIndex() { return this.methodIndex; }

    public Set<String> getTabCompleteFlags() { return this.tabCompleteFlags; }

    public Class<?> getType() { return this.type; }

    public Class<? extends ArgumentTypeHandler> getArgumentType() { return this.argumentType; }

}