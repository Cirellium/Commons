/**
* Copyright (C) 2023 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Thu Jan 05 2023 10:27:18
*
* CommandOutcome.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.bukkit.command.abstraction.result;

public enum CommandOutcome {

    SUCCESS,
    SUCCESS_NEED_CONFIRMATION,

    NOT_ENOUGH_ARGUMENTS,
    TOO_MANY_ARGUMENTS,
    INVALID_ARGUMENTS,

    ERROR,
    ERROR_NO_PERMISSION,
    ERROR_NO_CONSOLE,
    ERROR_NO_PLAYER,
    ERROR_INVALID_SENDER,

    ERROR_NO_COMMAND,
    ERROR_NO_COMMAND_FOUND,
    ERROR_NO_SUBCOMMAND,
    ERROR_NO_SUBCOMMAND_FOUND;

}