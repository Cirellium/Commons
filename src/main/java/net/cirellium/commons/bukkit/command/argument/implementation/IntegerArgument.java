package net.cirellium.commons.bukkit.command.argument.implementation;

import java.util.Arrays;
import java.util.List;

import net.cirellium.commons.bukkit.command.CommandContext;
import net.cirellium.commons.bukkit.command.argument.CommandArgument;

public class IntegerArgument implements CommandArgument<Integer> {

    @Override
    public Integer transform(CommandContext context, String argument) {
        try {
            return Integer.parseInt(argument);
        } catch (NumberFormatException e) {
            context.getCommandSender().sendMessage("§cPlease enter a valid integer.");
        }

        return -1;
    }

    public List<String> tabComplete(CommandContext context, String argument) {
        // Return possible matching integers
        return Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
    }
    
    
}