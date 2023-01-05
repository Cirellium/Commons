/**
* Copyright (C) 2022 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Thu Jan 05 2023 10:27:22
*
* CommandResult.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.bukkit.command.result;

public class CommandResult {
    
    private CommandOutcome outcome;
    
    private String message;
    
    public CommandResult(CommandOutcome outcome) {
        this.outcome = outcome;
    }

    public CommandResult(CommandOutcome outcome, String message) {
        this.outcome = outcome;
        this.message = message;
    }
    
    public CommandOutcome getOutcome() {
        return outcome;
    }
    
    public String getMessage() {
        return message;
    }

}