package net.cirellium.commons.bukkit.command.annotation;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import lombok.SneakyThrows;
import net.cirellium.commons.bukkit.command.annotation.adapter.ArgumentTypeAdapter;
import net.cirellium.commons.bukkit.command.annotation.annotation.Argument;
import net.cirellium.commons.bukkit.command.annotation.data.CommandData;
import net.cirellium.commons.bukkit.command.annotation.data.SubCommandData;
import net.cirellium.commons.common.logger.CirelliumLogger;
import net.cirellium.commons.common.util.Message;
import net.cirellium.commons.common.version.Platform;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class CirelliumCommand extends org.bukkit.command.Command {

    private final CommandData data;

    public CirelliumCommand(CommandData data) {
        super(data.getCommand().label(), data.getCommand().description(), data.getCommand().usage(),
                Arrays.asList(data.getCommand().aliases()));
        this.data = data;

        setPermission(data.getCommand().permission());
    }

    @Override
    @SuppressWarnings("deprecation")
    @SneakyThrows
    public boolean execute(CommandSender sender, String commandLabel, String[] passedArguments) {
        final String[] args;
        final Method method;
        final String permission;

        if (passedArguments.length >= 1 && !data.getSubCommands().isEmpty()
                && data.getSubCommands().stream()
                        .anyMatch(subCommand -> subCommand.getSubCommand().label().equalsIgnoreCase(passedArguments[0])
                                || Arrays.stream(subCommand.getSubCommand().aliases())
                                        .anyMatch(string -> string.equalsIgnoreCase(passedArguments[0])))) {
            final SubCommandData subCommand = Objects.requireNonNull(data.getSubCommands().stream()
                    .filter(subCommandData -> subCommandData.getSubCommand().label()
                            .equalsIgnoreCase(passedArguments[0])
                            || Arrays.stream(subCommandData.getSubCommand().aliases())
                                    .anyMatch(string -> string.equalsIgnoreCase(passedArguments[0]))))
                    .findFirst().orElse(null);

            args = Arrays.copyOfRange(passedArguments, 1, passedArguments.length);
            method = subCommand.getMethod();
            permission = subCommand.getSubCommand().permission();
        } else {
            args = passedArguments;
            method = data.getMethod();
            permission = data.getCommand().permission();
        }

        if (!sender.hasPermission(permission)) {
            sender.sendMessage(ChatColor.RED + "No permission.");
            return true;
        }

        final Parameter[] parameters = Arrays.copyOfRange(method.getParameters(), 1, method.getParameters().length);

        if (passedArguments.length > (method == data.getMethod() ? method.getParameters().length - 1
                : method.getParameters().length)) {
            sender.sendMessage(Message.LEGACY_PREFIX + Message.COMMAND_ERROR_TOO_MANY_ARGUMENTS.toLegacyString() + " "
                    + Message.COMMAND_USAGE.placeholder("usage", getUsage(parameters, passedArguments))
                            .toLegacyString());
            return true;
        }

        if (parameters.length > 0 && !parameters[0].getType().isArray()) {
            Object[] objects = new Object[parameters.length];

            if (passedArguments.length == 0) {
                sender.sendMessage(Message.LEGACY_PREFIX + Message.COMMAND_ERROR_NOT_ENOUGH_ARGUMENTS.toLegacyString()
                        + " " + Message.COMMAND_USAGE.placeholder("usage", getUsage(parameters, passedArguments))
                                .toLegacyString());
                return true;
            }

            for (int i = 0; i < parameters.length; i++) {
                final Parameter parameter = parameters[i];
                final Argument arg = parameter.getAnnotation(Argument.class);
                final String value;

                if (i >= args.length && (arg == null || arg.defaultValue().isEmpty())) {
                    sender.sendMessage(Message.LEGACY_PREFIX
                            + Message.COMMAND_ERROR_NOT_ENOUGH_ARGUMENTS.toLegacyString() + " " + Message.COMMAND_USAGE
                                    .placeholder("usage", getUsage(parameters, passedArguments)).toLegacyString());
                    return true;
                } else {
                    value = arg != null && !arg.defaultValue().isEmpty() && i >= args.length ? arg.defaultValue()
                            : args[i];
                }

                final ArgumentTypeAdapter<?> typeAdapter = CommandHandler.getInstance().getRegistry()
                        .findTypeAdapter(parameter.getType());

                if (typeAdapter == null) {
                    objects[i] = value;
                } else {
                    try {
                        objects[i] = typeAdapter.parse(sender, value);

                        if (objects[i] == null) {
                            throw new NullPointerException("Error while converting argument to object");
                        }
                    } catch (Exception exception) {
                        typeAdapter.handleException(sender, value);
                        return true;
                    }
                }
            }

            objects = ArrayUtils.add(objects, 0, method.getParameters()[0].getType().cast(sender));

            method.invoke(data.getCommandObject(), objects);
        } else if (parameters.length == 1 && parameters[0].getType().isArray()) {
            method.invoke(data.getCommandObject(), sender, args);
        } else {
            method.invoke(data.getCommandObject(), sender);
        }

        return false;
    }

    private String getFullUsage(Parameter[] parameters, String[] passedArguments) {
        return ChatColor.GRAY + "Usage: "
                + getUsage(parameters, passedArguments);
    }

    private String getUsage(Parameter[] parameters, String[] passedArguments) {
        String usage = "/" + data.getCommand().label() + " ";

        if (passedArguments.length >= 1 && !data.getSubCommands().isEmpty()
                && data.getSubCommands().stream()
                        .anyMatch(subCommand -> subCommand.getSubCommand().label().equalsIgnoreCase(passedArguments[0])
                                || Arrays.stream(subCommand.getSubCommand().aliases())
                                        .anyMatch(string -> string.equalsIgnoreCase(passedArguments[0])))) {
            final SubCommandData subCommand = Objects.requireNonNull(data.getSubCommands().stream()
                    .filter(subCommandData -> subCommandData.getSubCommand().label()
                            .equalsIgnoreCase(passedArguments[0])
                            || Arrays.stream(subCommandData.getSubCommand().aliases())
                                    .anyMatch(string -> string.equalsIgnoreCase(passedArguments[0])))
                    .findFirst().orElse(null));

            return usage + subCommand.getSubCommand().label() + " "
                    + Arrays.stream(subCommand.getMethod().getParameters())
                            .map(parameter1 -> "<" + parameter1.getName() + ">")
                            .collect(Collectors.joining(" "));
        }
        return usage + Arrays.stream(parameters)
                .map(parameter1 -> "<" + parameter1.getName() + ">").collect(Collectors.joining(" "));
    }
}