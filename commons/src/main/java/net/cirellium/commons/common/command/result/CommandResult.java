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
    
    public static final CommandResult INVALID_SENDER = new CommandResult(CommandOutcome.ERROR_INVALID_SENDER);

    private CommandOutcome outcome;
    
    private Message message;
    
    public CommandResult(CommandOutcome outcome) {
        this.outcome = outcome;
    }

    public CommandResult(CommandOutcome outcome, MessageKey key) {
        this.outcome = outcome;
        this.message = new Message(key);
    }
    
    public CommandOutcome getOutcome() {
        return outcome;
    }
    
    public Message getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return outcome == CommandOutcome.SUCCESS || outcome == CommandOutcome.SUCCESS_NEED_CONFIRMATION;
    }

    public boolean shouldDisplayUsage() {
        return !isSuccess() && 
            outcome != CommandOutcome.ERROR
            && outcome == CommandOutcome.ERROR_NO_COMMAND_FOUND
            && outcome != CommandOutcome.ERROR_NO_SUBCOMMAND_FOUND
            && outcome != CommandOutcome.ERROR_NO_PERMISSION
            && outcome != CommandOutcome.ERROR_INVALID_SENDER;
    }
}