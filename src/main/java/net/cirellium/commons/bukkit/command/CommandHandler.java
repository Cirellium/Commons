package net.cirellium.commons.bukkit.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import net.cirellium.commons.common.plugin.CirelliumPlugin;

public class CommandHandler<
    P extends CirelliumPlugin<P>, 
    H, 
    M extends AbstractCommandManager<P, H, M>
    > implements CommandExecutor, TabCompleter {

    private M commandManager; 

    private HashMap<String, AbstractCommand<P, M>> commands;

    public CommandHandler(M manager) {
        this.commandManager = manager;
        this.commands = manager.getCommandMap();
    }

    public void executeCommand(Player player, org.bukkit.command.Command command, String[] args) {
        commandManager.executeCommand(player, command, args);
    }

    public void executeConsole(org.bukkit.command.Command command, String[] args) {
        commandManager.executeConsole(command, args);
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (sender instanceof Player) {
            //("Executing player command 1");

            executeCommand((Player) sender, command, args);
        } else {
            executeConsole(command, args);
        }
        return true;
    }

    @Deprecated
    public List<String> onTabCompleteOld(CommandSender sender, org.bukkit.command.Command command, String alias, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 1) {
                //CustomMusic.debug("Args length is 1");
                List<String> result = new ArrayList<String>();
                for (String commandName : commands.keySet()) {
                    // CustomMusic.debug("Checking command: " + commandName);
                    if (commandName.startsWith(args[0])) {
                        // CustomMusic.debug("Adding command: " + commandName);
                        result.add(commandName);
                    }
                }
                // CustomMusic.debug("Returning result: " + result);
                return result;
            }

            // CustomMusic.debug("Testing tab completion for player");
            for (String commandName : commands.keySet()) {
                if (args[0].equalsIgnoreCase(commandName)) {
                    return commands.get(commandName).tabComplete(sender, args);
                }
            }

        }
        return new ArrayList<String>(commands.keySet());
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command unused, String alias, String[] args) {
        List<String> completes = new ArrayList<String>();
        String commandName = args[0];
        if (args.length == 1) {
            List<String> cmds = new ArrayList<String>();
            for (AbstractCommand<P, M> command : commandManager.getCommands()) {
                if (command.hasPermissions(sender)) cmds.add(command.getCommandName());
            }
            for (String string : cmds) {
                if (string.toLowerCase().startsWith(commandName.toLowerCase())) completes.add(string);
            }
            // CustomMusic.debug("Returning completes: " + completes);
            return completes.stream()
                    .distinct()
                    .collect(Collectors.toList());
        }
        for (AbstractCommand<P, M> command : commandManager.getCommands()) {
            if (command.matches(commandName)) completes.addAll(command.tabComplete(sender, args));
        }
        // CustomMusic.debug("Returning completes: " + completes);
        return completes.stream()
                .distinct()
                .collect(Collectors.toList());
    }

}