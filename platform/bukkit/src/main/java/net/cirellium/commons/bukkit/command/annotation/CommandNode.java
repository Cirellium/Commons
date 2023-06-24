/**
* Copyright (C) 2023 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Tue Jan 10 2023 14:11:58
*
* CommandNode.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.bukkit.command.annotation;

import java.beans.ConstructorProperties;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;

import com.google.common.base.Stopwatch;
import com.google.common.base.Strings;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import net.cirellium.commons.bukkit.command.abstraction.AbstractCommand.SenderType;
import net.cirellium.commons.bukkit.command.annotation.argument.ArgumentData;
import net.cirellium.commons.bukkit.command.annotation.argument.ArgumentTypeHandler;
import net.cirellium.commons.bukkit.command.annotation.argument.provided.ProvidedArguments;
import net.cirellium.commons.common.util.Message;

public class CommandNode {

    private String name, permission, description;

    private boolean async, hidden, tabComplete;

    private boolean debug = true;

    @Getter
    @Setter
    private SenderType senderType;

    protected Method method;

    protected Class<?> owningClass;

    private Set<String> aliases;

    private List<ArgumentData> arguments;

    private Map<String, CommandNode> children = new TreeMap<>();

    private CommandNode parentNode;

    public CommandNode() {
    }

    public CommandNode(Class<?> owningClass) {
        this.owningClass = owningClass;
    }

    @ConstructorProperties({ "name", "permission" })
    public CommandNode(@NonNull String name, @NonNull String permission) {
        if (name == null)
            throw new NullPointerException("name");
        if (permission == null)
            throw new NullPointerException("permission");
        this.name = name;
        this.permission = permission;
    }

    @ConstructorProperties({ "name", "aliases", "permission", "description", "async", "hidden", "method", "owningClass",
            "validFlags", "parameters", "children", "parent", "logToConsole" })
    public CommandNode(
            @NonNull String name,
            Set<String> aliases,
            @NonNull String permission,
            String description,
            boolean async,
            boolean hidden,
            Method method,
            Class<?> owningClass,
            List<ArgumentData> arguments,
            Map<String, CommandNode> children,
            CommandNode parentNode) {

        if (name == null)
            throw new NullPointerException("name");
        if (permission == null)
            throw new NullPointerException("permission");
        this.name = name;
        this.aliases = aliases;
        this.permission = permission;
        this.description = description;
        this.async = async;
        this.hidden = hidden;
        this.method = method;
        this.owningClass = owningClass;
        this.arguments = arguments;
        this.children = children;
        this.parentNode = parentNode;
    }

    @SuppressWarnings({ "deprecation" })
    public boolean invoke(CommandSender sender, ProvidedArguments args) throws CommandException {
        // Check if the method is null
        if (this.method == null) {
            // If the method is null, log a warning message
            CommandHandler.getInstance().getLogger().warning("Method is null for command: " + this.name);

            // Check if the command has subcommands
            if (hasCommands()) {
                // Check if the sender has any subcommands
                if (getSubCommands(sender, true).isEmpty()) {
                    // Check if the command is hidden
                    if (isHidden()) {
                        // If the command is hidden, send a message to the sender
                        sender.sendMessage("§fUnknown command. Try /help for help.");
                    } else {
                        // If the command is not hidden, send a message to the sender
                        sender.sendMessage("No permission.");
                    }
                }
            } else {
                // If the command does not have subcommands, send a message to the sender
                sender.sendMessage("§fUnknown command. Try /help for help.");
            }
            return true;
        }

        // CommandHandler.getInstance().getLogger().info("Parameter count of method " + this.method.getName() + ": "
        //         + this.method.getParameterCount());

        List<Object> objects = new ArrayList<Object>(this.method.getParameterCount());
        objects.add(sender);

        // CommandHandler.getInstance().getLogger().info("Arguments: " + String.join(", ", args.getArguments()));

        int index = 0;
        Iterator<ArgumentData> iterator = this.arguments.iterator();
        while (iterator.hasNext()) {
            // CommandHandler.getInstance().getLogger().info("Argument " + index + ": " + args.getArguments().get(index));

            ArgumentData argumentData = iterator.next();
            String argument;
            try {
                argument = args.getArguments().get(index);
            } catch (Exception e) {
                CommandHandler.getInstance().getLogger().warning("An exception occurred while getting argument " + index
                        + " for command " + this.name + ": " + e.getMessage());
                if (argumentData.getDefaultValue().isEmpty())
                    return false;
                argument = argumentData.getDefaultValue();
            }
            if (argumentData.isWildcard() && (argument.isEmpty() || !argument.equals(argumentData.getDefaultValue())))
                argument = args.join(index);

            ArgumentTypeHandler<?> type = CommandHandler.getInstance().getRegistry().getArgumentTypeHandler(argumentData.getType());
            if (argumentData.getArgumentType() != null)
                try {
                    type = argumentData.getArgumentType().newInstance();
                } catch (IllegalAccessException | InstantiationException var12) {
                    var12.printStackTrace();
                    throw new CommandException("Failed to create ParameterType instance: "
                            + argumentData.getArgumentType().getName(), var12);
                }
            if (type == null) {
                Class<?> t = (argumentData.getArgumentType() == null) ? argumentData.getType()
                        : argumentData.getArgumentType();
                sender.sendMessage(ChatColor.RED + "No parameter type found: " + t.getSimpleName());
                return true;
            }

            Object result = type.parse(sender, argument);

            if (result == null) return true;

            // CommandHandler.getInstance().getLogger().info("RESULT: " + result);
            // CommandHandler.getInstance().getLogger().info("Method index: " + argumentData.getMethodIndex());

            objects.add(argumentData.getMethodIndex(), result);
            index++;
        }
        // CommandHandler.getInstance().getLogger().info("Invoking command: " + getFullLabel());
        try {
            CommandHandler.getInstance().getLogger().info("Invoking command: " + String.join(", ", objects.stream().map(Object::toString).collect(Collectors.joining(", "))));

            Stopwatch stopwatch = Stopwatch.createStarted();
            try {
                this.method.invoke(owningClass.newInstance(), objects.toArray());
            } catch (IllegalArgumentException e) {
                if (e.getMessage().contains("type mismatch") && !senderType.matches(sender)) {
                    sender.sendMessage((senderType == SenderType.PLAYER ? Message.COMMAND_PLAYER_ONLY : Message.COMMAND_CONSOLE_ONLY).toString());
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
            stopwatch.stop();
            if (!this.async && stopwatch.elapsed().toMillis() >= 10) {
                CommandHandler.getInstance().getLogger().warning("Command '/" + getFullLabel() + "' took " + stopwatch.elapsed().toMillis() + "ms!");
            }
            return true;
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            throw new CommandException("An error occurred while executing the command", e);
        }
    }

    public List<String> getSubCommands(CommandSender sender, boolean shouldPrint) {
        List<String> commands = new ArrayList<>();
        if (sender.hasPermission(permission)) {
            String command = ((sender instanceof org.bukkit.entity.Player) ? "/" : "") + getFullLabel()
                    + (!Strings.isNullOrEmpty(this.description) ? (ChatColor.GRAY + " - " + getDescription()) : "");
            if (this.parentNode == null) {
                commands.add(command);
            } else if (this.parentNode.getName() != null
                    && CommandRegistry.ROOT_COMMAND_NODE.getNode(this.parentNode.getName()) != this.parentNode) {
                commands.add(command);
            }
            if (hasCommands()) {
                Iterator<CommandNode> children = getChildren().values().iterator();
                while (children.hasNext()) {
                    CommandNode n = children.next();
                    commands.addAll(n.getSubCommands(sender, false));
                }
            }
        }
        if (!commands.isEmpty() && shouldPrint) {
            sender.sendMessage(ChatColor.BLUE.toString() + ChatColor.STRIKETHROUGH + StringUtils.repeat('-', 35));
            Iterator<String> var7 = commands.iterator();
            while (var7.hasNext()) {
                String command = var7.next();
                sender.sendMessage(ChatColor.RED + command);
            }
            sender.sendMessage(ChatColor.BLUE.toString() + ChatColor.STRIKETHROUGH + StringUtils.repeat('-', 35));
        }
        return commands;
    }

    public void registerCommand(CommandNode commandNode) {
        commandNode.setParent(this);
        this.children.put(commandNode.getName(), commandNode);
    }

    public boolean hasCommand(String name) {
        return this.children.containsKey(name.toLowerCase());
    }

    public CommandNode getNode(String name) {
        return this.children.get(name.toLowerCase());
    }

    public boolean hasCommands() {
        return (this.children.size() > 0);
    }

    public CommandNode findCommand(List<String> commandLine) {
        if (commandLine.size() > 0) {
            String trySub = commandLine.get(0);
            
            if (hasCommand(trySub)) {
                commandLine.remove(0);
                CommandNode returnNode = getNode(trySub);
                return returnNode.findCommand(commandLine);
            }
        }
        return this;
    }

    public CommandNode findCommand(ProvidedArguments arguments) {
        if (arguments.getArguments().size() > 0) {
            String trySub = arguments.getArguments().get(0);
            if (hasCommand(trySub)) {
                arguments.getArguments().remove(0);
                CommandNode returnNode = getNode(trySub);
                return returnNode.findCommand(arguments);
            }
        }
        return this;
    }

    public Set<String> getRealAliases() {
        Set<String> aliases = getAliases();
        if (aliases.contains(getName()))
            aliases.remove(getName());
        return aliases;
    }

    public String getFullLabel() {
        List<String> labels = new ArrayList<>();
        for (CommandNode node = this; node != null; node = node.getParent()) {
            String name = node.getName();
            if (name != null)
                labels.add(name);
        }
        Collections.reverse(labels);
        labels.remove(0);
        StringBuilder builder = new StringBuilder();
        labels.forEach(s -> builder.append(s).append(' '));
        return builder.toString().trim();
    }

    @NonNull
    public String getName() {
        return this.name;
    }

    public void setName(@NonNull String name) {
        Objects.requireNonNull(name, "name cannot be null");
        this.name = name;
    }

    public Set<String> getAliases() {
        return this.aliases;
    }

    public boolean hasAliases() {
        return this.aliases != null && (this.aliases.size() > 0);
    }

    @NonNull
    public String getPermission() {
        return this.permission;
    }

    public void setPermission(@NonNull String permission) {
        if (permission == null)
            throw new NullPointerException("permission");
        this.permission = permission;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isAsync() {
        return this.async;
    }

    public boolean isHidden() {
        return this.hidden;
    }

    public boolean tabComplete() {
        return this.tabComplete;
    }

    public boolean isDebugMode() {
        return this.debug;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public void setTabComplete(boolean tabComplete) {
        this.tabComplete = tabComplete;
    }

    public void setDebugMode(boolean debug) {
        this.debug = debug;
    }

    public Method getMethod() {
        return this.method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public void setOwningClass(Class<?> owningClass) {
        this.owningClass = owningClass;
    }

    public Class<?> getOwningClass() {
        return this.owningClass;
    }

    public List<ArgumentData> getParameters() {
        return this.arguments;
    }

    public void setArguments(List<ArgumentData> arguments) {
        this.arguments = arguments;
    }

    public Map<String, CommandNode> getChildren() {
        return this.children;
    }

    public CommandNode getParent() {
        return this.parentNode;
    }

    public void setParent(CommandNode parentNode) {
        this.parentNode = parentNode;
    }
}