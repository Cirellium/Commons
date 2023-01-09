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

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;

public class CommandResult {
    
    public static final CommandResult INVALID_SENDER = new CommandResult(CommandOutcome.ERROR_INVALID_SENDER, Component.text("This command has to be executed by either a player or a console.", Style.style(NamedTextColor.RED)));

    private CommandOutcome outcome;
    
    private Component message;
    
    public CommandResult(CommandOutcome outcome) {
        this.outcome = outcome;
    }

    public CommandResult(CommandOutcome outcome, Component message) {
        this.outcome = outcome;
        this.message = message;
    }
    
    public CommandOutcome getOutcome() {
        return outcome;
    }
    
    public Component getMessage() {
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