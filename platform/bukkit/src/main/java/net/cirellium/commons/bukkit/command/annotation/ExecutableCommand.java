/**
* Copyright (C) 2023 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Tue Jan 10 2023 14:11:54
*
* ExecutableCommand.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.bukkit.command.annotation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.collect.Lists;

import net.cirellium.commons.bukkit.command.CirelliumBukkitCommand;
import net.cirellium.commons.bukkit.command.annotation.argument.ArgumentData;
import net.cirellium.commons.bukkit.command.annotation.argument.ArgumentTypeHandler;
import net.cirellium.commons.bukkit.command.annotation.argument.provided.ProvidedArgumentProcessor;
import net.cirellium.commons.bukkit.command.annotation.argument.provided.ProvidedArguments;

public class ExecutableCommand
        extends org.bukkit.command.Command
        implements PluginIdentifiableCommand,
        CirelliumBukkitCommand {

    protected CommandNode node;

    private final JavaPlugin owningPlugin;

    public ExecutableCommand(CommandNode node, JavaPlugin plugin) {
        super(node.getName(), "", "/",
                (node.getAliases() != null ? Lists.newArrayList(node.getRealAliases()) : new ArrayList<>()));
        this.node = node;
        this.owningPlugin = plugin;
    }

    public boolean execute(final CommandSender sender, String label, String[] args) {
        // CommandHandler.getInstance().getLogger().info("Attempting to execute command
        // " + label + " with arguments " + StringUtils.join(args, " "));

        final ProvidedArguments arguments = new ProvidedArgumentProcessor().process(getCommandLine(label, args));
        final CommandNode executionNode = this.node.findCommand(arguments);
        // final String realLabel = getFullLabel(executionNode);
        if (sender.hasPermission(executionNode.getPermission())) {
            try {
                if (executionNode.isAsync()) {
                    owningPlugin.getServer().getScheduler().runTaskAsynchronously(owningPlugin, () -> {
                        if (executionNode.invoke(sender, arguments))
                            return;
                    });
                    return true;
                }
                // Sync command execution
                CommandHandler.getInstance().getLogger().info(
                        "Synchronously executing command " + executionNode.getName() + " with arguments " + arguments);

                if (executionNode.invoke(sender, arguments))
                    return true;
                return true;
            } catch (CommandException e) {
                sender.sendMessage(ChatColor.RED + "An error occurred while processing your command.");
                e.printStackTrace();
            }
        }
        if (executionNode.isHidden()) {
            sender.sendMessage("§fUnknown command. Type \"/help\" for help.");
            return true;
        }
        sender.sendMessage(ChatColor.RED + "No permission.");
        return true;
    }

    @SuppressWarnings({ "rawtypes", "deprecation", "unchecked" })
    public List<String> tabComplete(CommandSender sender, String args) {
        if (!(sender instanceof Player))
            return Collections.emptyList();

        List<String> argList = new ArrayList<>();
        String replacedArgs = args.replace(this.owningPlugin.getName().toLowerCase() + ":", "");
        argList.addAll(Arrays.asList(replacedArgs.split(" ")));

        if (argList.size() < 1) {
            return Collections.emptyList();
        }

        ProvidedArguments arguments = new ProvidedArgumentProcessor().process(argList);
        CommandNode realNode = this.node.findCommand(arguments);

        if (!sender.hasPermission(realNode.getPermission())) {
            return Collections.emptyList();
        }

        List<String> realArgs = arguments.getArguments();
        int currentIndex = Math.max(realArgs.size() - 1, 0);
        if (args.endsWith(" ")) {
            currentIndex++;
        }

        ArrayList<String> completions = new ArrayList<>();

        if (realNode.hasCommands()) {
            String name = (realArgs.size() == 0) ? "" : realArgs.get(realArgs.size() - 1);
            completions.addAll(realNode.getChildren().values().stream()
                    .filter(node -> sender.hasPermission(node.getPermission())
                            && (StringUtils.startsWithIgnoreCase(node.getName(), name) || StringUtils.isEmpty(name)))
                    .map(CommandNode::getName)
                    .collect(Collectors.toList()));
            if (!completions.isEmpty()) {
                return completions;
            }
        }

        if (argList.get(argList.size() - 1).equalsIgnoreCase(realNode.getName()) && !args.endsWith(" ")) {
            return Collections.emptyList();
        }

        // if (realNode.getValidFlags() != null && !realNode.getValidFlags().isEmpty())
        // {
        // for (String flags : realNode.getValidFlags()) {
        // String arg = rawArgs[rawArgs.length - 1];
        // if ((!Flag.FLAG_PATTERN.matcher(arg).matches() && !arg.equals("-"))
        // || (!StringUtils.startsWithIgnoreCase(flags, arg.substring(1)) &&
        // !arg.equals("-")))
        // continue;
        // completions.add("-" + flags);
        // }
        // if (completions.size() > 0)
        // return completions;
        // }

        ArgumentTypeHandler argumentHandler = null;
        ArgumentData data = null;
        if (realNode.getParameters() != null) {
            List<ArgumentData> params = realNode.getParameters()
                    .stream()
                    .filter(d -> d instanceof ArgumentData)
                    .map(d -> (ArgumentData) d)
                    .collect(Collectors.toList());
            int fixed = Math.max(0, currentIndex - 1);
            data = params.get(fixed);
            argumentHandler = CommandHandler.getInstance().getRegistry().getArgumentTypeHandler(data.getType());
            if (data.getArgumentType() != null) {
                try {
                    argumentHandler = data.getArgumentType().newInstance();
                } catch (IllegalAccessException | InstantiationException e) {
                    e.printStackTrace();
                }
            }
        }
        if (data == null) {
            CommandHandler.getInstance().getLogger().info("Data is null");
            return completions;
        }
        if (argumentHandler != null) {
            if (currentIndex < realArgs.size()
                    && ((String) realArgs.get(currentIndex)).equalsIgnoreCase(realNode.getName())) {
                realArgs.add("");
                currentIndex++;
            }
            String currentArgument = (currentIndex >= realArgs.size()) ? "" : realArgs.get(currentIndex);
            List<String> suggested = argumentHandler.tabComplete((Player) sender, data.getTabCompleteFlags(),
                    currentArgument);
            completions.addAll((Collection<? extends String>) suggested.stream()
                    .filter(s -> StringUtils.startsWithIgnoreCase(s, currentArgument))
                    .collect(Collectors.toList()));
        }

        return completions;
    }

    private List<String> getCommandLine(String label, String[] args) {
        List<String> cmdLine = new ArrayList<>();
        cmdLine.add(label);
        cmdLine.addAll(Arrays.asList(args));
        return cmdLine;
    }

    public String getFullLabel(CommandNode node) {
        ArrayList<String> labels = new ArrayList<>();
        while (node != null) {
            String name = node.getName();
            if (name != null)
                labels.add(name);
            node = node.getParent();
        }
        Collections.reverse(labels);
        labels.remove(0);
        StringBuilder builder = new StringBuilder();
        labels.forEach(s -> builder.append(s).append(' '));
        return builder.toString();
    }

    public String stripLabel(String label) {
        return label.replace(this.owningPlugin.getName().toLowerCase() + ":", "");
    }

    @Override
    public String getPermission() {
        return node.getPermission();
    }

    @Override
    public org.bukkit.command.Command getBukkitCommand() {
        return this;
    }

    public CommandNode getNode() {
        return this.node;
    }

    @Override
    public Plugin getPlugin() {
        return owningPlugin;
    }

}