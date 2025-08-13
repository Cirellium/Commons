package net.cirellium.commons.common.command.annotation.adapter.implementation;

import java.util.List;
import java.util.Set;

import net.cirellium.commons.common.command.annotation.adapter.ArgumentTypeAdapter;
import net.cirellium.commons.common.command.result.CommandExecutionResult;
import net.cirellium.commons.common.command.sender.CommandInvoker;

public interface CommandArgumentTypeAdapter<A> extends ArgumentTypeAdapter<A, CommandInvoker> {

    List<String> tabComplete(CommandInvoker sender, Set<String> argumentSet, String argument);

    List<?> getPossibleResults();

    default void handleException(CommandInvoker sender, String source) {
        sender.sendMessage(CommandExecutionResult.ERROR_PARSE_ARG.placeholder("arg", source));
    }
}