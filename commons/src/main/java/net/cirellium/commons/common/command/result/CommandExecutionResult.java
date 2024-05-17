/**
* Copyright (C) 2023 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Thu Jan 05 2023 10:27:18
*
* CommandOutcome.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.common.command.result;


import net.cirellium.commons.common.message.MessageKey;

public enum CommandExecutionResult implements MessageKey {

    SUCCESS("<green>Command executed successfully."),
    SUCCESS_NEED_CONFIRMATION("<green>Command executed successfully. Please confirm your action by running <command>"),

    USAGE("<color:#ff523b>Usage: <usage>"),
    NOT_ENOUGH_ARGUMENTS("<color:#ff523b>Not enough arguments."),
    TOO_MANY_ARGUMENTS("<color:#ff523b>Too many arguments."),
    INVALID_ARGUMENTS("<color:#ff523b>Invalid arguments."),

    ERROR("<color:#ff523b>An unknown error occurred while executing this command."),
    ERROR_PARSE_ARG("<color:#ff523b>An error occurred while parsing argument <arg>"),
    ERROR_NO_PERMISSION("<color:#ff523b>You do not have the required permission <permission> to execute this command."),
    ERROR_NO_CONSOLE("<color:#ff523b>This command can only be executed by the console."),
    ERROR_NO_PLAYER("<color:#ff523b>This command can only be executed by a player."),
    ERROR_INVALID_SENDER("<red>This command has to be executed by either a player or the console."),
    
    ERROR_INVALID_TYPE("<color:#ff523b>Please provide a valid <type>"),

    ERROR_NO_COMMAND("<color:#ff523b>Please provide a command"),
    ERROR_NO_COMMAND_FOUND("<color:#ff523b>No command was found for <command>"),
    ERROR_NO_SUBCOMMAND("<color:#ff523b>Please provide a subcommand"),
    ERROR_NO_SUBCOMMAND_FOUND("<color:#ff523b>No subcommand was found for <subcommand>");

    CommandExecutionResult(String fallback) {
        this.fallbackValue = fallback;
    }

    private String fallbackValue;

    public CommandExecutionResult withPlaceholder(String key, String value) {
        this.fallbackValue = this.fallbackValue.replace("<" + key + ">", value);
        return this;
    }

    @Override
    public String getKey() {
        return this.name().toLowerCase().replace("_", "-");
    }

    @Override
    public String getFallbackValue() {
        return fallbackValue;
    }

    @Override
    public Enum<?> getEnum() {
        return this;
    }
}