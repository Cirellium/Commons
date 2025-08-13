/**
* Copyright (C) 2023 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Thu Jan 05 2023 10:27:22
*
* CommandResult.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.common.command.result;

import net.cirellium.commons.common.message.Message;
import net.cirellium.commons.common.message.MessageKey;

public class CommandResult {
    
    public static final CommandResult INVALID_SENDER = new CommandResult(CommandExecutionResult.ERROR_INVALID_SENDER);

    private CommandExecutionResult outcome;
    
    private Message message;
    
    public CommandResult(CommandExecutionResult outcome) {
        this.outcome = outcome;
    }

    public CommandResult(CommandExecutionResult outcome, MessageKey key) {
        this.outcome = outcome;
        this.message = new Message(key);
    }
    
    public CommandExecutionResult getOutcome() {
        return outcome;
    }
    
    public Message getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return outcome == CommandExecutionResult.SUCCESS || outcome == CommandExecutionResult.SUCCESS_NEED_CONFIRMATION;
    }

    public boolean shouldDisplayUsage() {
        return !isSuccess() && 
            outcome != CommandExecutionResult.ERROR
            && outcome == CommandExecutionResult.ERROR_NO_COMMAND_FOUND
            && outcome != CommandExecutionResult.ERROR_NO_SUBCOMMAND_FOUND
            && outcome != CommandExecutionResult.ERROR_NO_PERMISSION
            && outcome != CommandExecutionResult.ERROR_INVALID_SENDER;
    }
}