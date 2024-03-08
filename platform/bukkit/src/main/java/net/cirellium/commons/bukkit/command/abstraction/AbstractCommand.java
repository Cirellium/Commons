/**
* Copyright (C) 2023 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Thu Jan 05 2023 10:29:18
*
* AbstractCommand.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.bukkit.command.abstraction;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import net.cirellium.commons.bukkit.CirelliumBukkitPlugin;
import net.cirellium.commons.bukkit.command.CirelliumBukkitCommand;
import net.cirellium.commons.bukkit.command.abstraction.result.CommandResult;
import net.cirellium.commons.common.data.user.AbstractCirelliumUser;

public abstract class AbstractCommand<M extends AbstractCommandService<?, M>>
        implements CirelliumBukkitCommand {

    protected final CirelliumBukkitPlugin plugin;
    protected final M manager;

    protected HashMap<String, AbstractCommand<M>> subCommands;

    protected int minArgs, maxArgs;

    protected String commandName, permission, description, help;

    protected SenderType senderType;

    protected List<String> aliases;

    protected boolean isPlayerCommand, isConsoleCommand, isSubCommand;

    public AbstractCommand(CirelliumBukkitPlugin plugin, M manager, String name, boolean register) {
        this.plugin = plugin;
        this.manager = manager;
        this.commandName = name;
        this.subCommands = new HashMap<String, AbstractCommand<M>>();
        this.aliases = new ArrayList<String>();

        if (register)
            this.manager.registerCommand(this);
    }

    public void setMinArgs(int minArgs) {
        this.minArgs = minArgs;
    }

    public int getMinArgs() {
        return this.minArgs;
    }

    public void setMaxArgs(int maxArgs) {
        this.maxArgs = maxArgs;
    }

    public int getMaxArgs() {
        return this.maxArgs;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    @Override
    public String getPermission() {
        return this.permission;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    public String getHelp() {
        return this.help;
    }

    public void setPlayerCommand(boolean playerCommand) {
        this.isPlayerCommand = playerCommand;
    }

    public boolean isPlayerCommand() {
        return this.isPlayerCommand && (senderType == SenderType.PLAYER || senderType == SenderType.BOTH);
    }

    public void setConsoleCommand(boolean consoleCommand) {
        this.isConsoleCommand = consoleCommand;
    }

    public boolean isConsoleCommand() {
        return this.isConsoleCommand && (senderType == SenderType.CONSOLE || senderType == SenderType.BOTH);
    }

    public void setSubCommand(boolean subCommand) {
        this.isSubCommand = subCommand;
    }

    public boolean isSubCommand() {
        return this instanceof SubCommand;
    }

    public boolean isDirectCommand() {
        return !isSubCommand();
    }

    public String getCommandName() {
        return commandName;
    }

    public void addSubCommand(AbstractCommand<M> command) {
        if (command instanceof SubCommand) {
            subCommands.put(command.getCommandName(), command);
        }
    }

    public Map<String, AbstractCommand<M>> getSubCommands() {
        return subCommands;
    }

    public boolean hasSubCommand(String subCommand) {
        return subCommands.containsKey(subCommand);
    }

    public AbstractCommand<M> getSubCommand(String name) {
        return hasSubCommand(name) ? subCommands.get(name) : null;
    }

    public boolean matches(String arg) {
        return arg.equalsIgnoreCase(this.commandName);
    }

    public boolean hasPermissions(CommandSender sender) {
        return (this.permission == null || this.permission.isEmpty() || sender.hasPermission(this.permission));
    }

    @Override
    public List<String> getAliases() {
        return aliases;
    }

    @Override
    public @Nullable Command getBukkitCommand() {
        return getCommandMap().getCommand(commandName);
    }

    public abstract CommandResult executePlayer(AbstractCirelliumUser<?> player, org.bukkit.command.Command command,
            String[] arguments);

    public abstract CommandResult executeConsole(org.bukkit.command.Command command, String[] arguments);

    public abstract List<String> onTabComplete(AbstractCirelliumUser<?> player, String[] arguments);

    public final List<String> tabComplete(CommandSender sender, String[] args) {
        List<String> completes = new ArrayList<String>();
        if (sender instanceof Player) {
            // Player p = (Player) sender;

            AbstractCirelliumUser<?> cp = null; // TODO add support for CirelliumUser

            List<String> commandCompletes = onTabComplete(cp, args);
            String cmdName = args[1];
            if (commandCompletes != null && !commandCompletes.isEmpty())
                completes.addAll(commandCompletes);

            if (args.length == 2) {
                List<String> cmds = new ArrayList<>();
                for (AbstractCommand<M> command : this.subCommands.values()) {
                    if (command.hasPermissions(sender))
                        cmds.add(command.getCommandName());
                }
                for (String string : cmds) {
                    if (string.toLowerCase().startsWith(cmdName.toLowerCase()))
                        completes.add(string);
                }
                return completes;
            }
            for (AbstractCommand<M> command : this.subCommands.values()) {
                if (command.matches(cmdName))
                    completes.addAll(command.tabComplete(sender, Arrays.<String>copyOfRange(args, 1, args.length)));
            }
        }
        return completes;
    }

    public enum SenderType {
        PLAYER, CONSOLE, BOTH;

        public boolean matches(CommandSender sender) {
            return switch (this) {
                case PLAYER -> sender instanceof Player;
                case CONSOLE -> sender instanceof ConsoleCommandSender;
                case BOTH -> true;
                default -> false;
            };
        }
    }

    private static CommandMap getCommandMap() {
        try {
            Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            return (CommandMap) commandMapField.get(Bukkit.getServer());
        } catch (ReflectiveOperationException ignored) {
        }
        return null;
    }
}