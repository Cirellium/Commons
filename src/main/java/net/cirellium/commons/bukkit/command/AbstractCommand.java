/**
* Copyright (C) 2022 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Thu Jan 05 2023 10:29:18
*
* AbstractCommand.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.bukkit.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.cirellium.commons.common.data.user.AbstractCirelliumUser;
import net.cirellium.commons.common.plugin.CirelliumPlugin;

public abstract class AbstractCommand<
    P extends CirelliumPlugin<P>, 
    M extends AbstractCommandManager<P, ?, M>> {
    
    protected final P plugin;
    protected final M manager;

    public HashMap<String, AbstractCommand<P, M>> subCommands;

    public int minArgs, maxArgs;

    public String commandName, permission, description, help;

    public boolean isPlayerCommand, isConsoleCommand, isSubCommand, autoTabComplete;

    public AbstractCommand(P plugin, M manager, String name, boolean register) {
        this.plugin = plugin;
        this.manager = manager;

        this.commandName = name;

        this.subCommands = new HashMap<String, AbstractCommand<P, M>>();

        

        if (register) this.manager.registerCommand(this);
    }

    public void setMinArgs(int minArgs) {
        this.minArgs = minArgs;
    }

    public void setMaxArgs(int maxArgs) {
        this.maxArgs = maxArgs;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    public void setPlayerCommand(boolean playerCommand) {
        this.isPlayerCommand = playerCommand;
    }

    public void setConsoleCommand(boolean consoleCommand) {
        this.isConsoleCommand = consoleCommand;
    }

    public void setSubCommand(boolean subCommand) {
        this.isSubCommand = subCommand;
    }

    public void setAutoTabComplete(boolean autoTabComplete) {
        this.autoTabComplete = autoTabComplete;
    }

    public String getCommandName() {
        return commandName;
    }

    public void addSubCommand(AbstractCommand<P, M> command) {
        if (command instanceof SubCommand) {
            subCommands.put(command.getCommandName(), command);
        }
    }

    public Map<String, AbstractCommand<P, M>> getSubCommands() {
        return subCommands;
    }

    public boolean hasSubCommand(String subCommand) {
        return subCommands.containsKey(subCommand);
    }

    public AbstractCommand<P, M> getSubCommand(String string) {
        return subCommands.get(string);
    }

    public boolean matches(String arg) {
        return arg.equalsIgnoreCase(this.commandName);
    }

    public boolean hasPermissions(CommandSender sender) {
        return (this.permission == null || this.permission.isEmpty() || sender.hasPermission(this.permission));
    }

    public abstract void executePlayer(AbstractCirelliumUser<P> player, org.bukkit.command.Command command, String[] arguments);

    public abstract void executeConsole(org.bukkit.command.Command command, String[] arguments);

    public abstract List<String> onTabComplete(AbstractCirelliumUser<P> player, String[] arguments);

    public final List<String> tabComplete(CommandSender sender, String[] args) {
        List<String> completes = new ArrayList<String>();
        if (sender instanceof Player) {
            // Player p = (Player) sender;

            AbstractCirelliumUser<P> cp = null; // TODO add support for CirelliumUser

            List<String> commandCompletes = onTabComplete(cp, args);
            String cmdName = args[1];
            if (commandCompletes != null && !commandCompletes.isEmpty()) completes.addAll(commandCompletes);

            if (args.length == 2) {
                List<String> cmds = new ArrayList<>();
                for (AbstractCommand<P, M> command : this.subCommands.values()) {
                    if (command.hasPermissions(sender))
                        cmds.add(command.getCommandName());
                }
                for (String string : cmds) {
                    if (string.toLowerCase().startsWith(cmdName.toLowerCase()))
                        completes.add(string);
                }
                return completes;
            }
            for (AbstractCommand<P, M> command : this.subCommands.values()) {
                if (command.matches(cmdName))
                    completes.addAll(command.tabComplete(sender, Arrays.<String>copyOfRange(args, 1, args.length)));
            }
        }
        return completes;
    }

}