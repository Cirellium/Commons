package net.cirellium.commons.bukkit.command.annotation;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;
import org.bukkit.command.CommandSender;

import lombok.SneakyThrows;
import net.cirellium.commons.bukkit.command.annotation.adapter.ArgumentTypeAdapter;
import net.cirellium.commons.bukkit.command.annotation.annotation.Argument;
import net.cirellium.commons.bukkit.command.annotation.data.CommandData;
import net.cirellium.commons.bukkit.command.annotation.data.MainCommandData;
import net.cirellium.commons.bukkit.command.annotation.data.SubCommandData;
import net.cirellium.commons.common.util.Message;

public class CirelliumCommand extends org.bukkit.command.Command {

    private final MainCommandData data;

    public CirelliumCommand(MainCommandData data) {
        super(data.getCommand().label(), data.getCommand().description(), data.getCommand().usage(),
                Arrays.asList(data.getCommand().aliases()));
        this.data = data;

        setPermission(data.getCommand().permission());
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] passedArguments) {
        SubCommandData subCommand = null;
        if (passedArguments.length >= 1 && !data.getSubCommands().isEmpty()) {
            subCommand = data.findSubCommand(passedArguments);
        }

        final CommandData<?> commandData = subCommand != null ? subCommand : data;

        final String[] args = subCommand != null ? Arrays.copyOfRange(passedArguments, 1, passedArguments.length) : passedArguments;
        final Method method = subCommand != null ? subCommand.getMethod() : data.getMethod();
        final String permission = subCommand != null ? subCommand.getSubCommand().permission() : data.getCommand().permission();

        if (!sender.hasPermission(permission)) {
            return sendMessage(sender, Message.COMMAND_ERROR_NO_PERMISSION.placeholder("permission", permission));
        }

        // Parameter[] params = method.getParameters();

        // if (passedArguments.length > (method == data.getMethod() ? params.length - 1
        //         : params.length)) {
        //     return sendMessage(sender, Message.COMMAND_ERROR_TOO_MANY_ARGUMENTS,
        //             Message.COMMAND_USAGE.placeholder("usage", commandData.getUsage(commandLabel)));
        // }

        return invoke(commandData, method, sender, commandLabel, passedArguments, args);
    }

    @SneakyThrows
    @SuppressWarnings("deprecation")
    private boolean invoke(CommandData<?> commandData, Method method, CommandSender sender, String commandLabel,
            String[] passedArguments, String[] args) {
        final Parameter[] parameters = Arrays.copyOfRange(method.getParameters(), 1, method.getParameters().length);

        if (parameters.length > 0 && !parameters[0].getType().isArray()) {
            final Object[] invokeParams = new Object[parameters.length];

            if (passedArguments.length == 0) {
                return sendMessage(sender, Message.COMMAND_ERROR_NOT_ENOUGH_ARGUMENTS,
                        Message.COMMAND_USAGE.placeholder("usage", commandData.getUsage(commandLabel)));
            }

            for (int i = 0; i < parameters.length; i++) {
                final Parameter parameter = parameters[i];
                final Argument arg = parameter.getAnnotation(Argument.class);
                final String value;

                if (i >= args.length && (arg == null || arg.defaultValue().isEmpty())) {
                    return sendMessage(sender, Message.COMMAND_ERROR_NOT_ENOUGH_ARGUMENTS, Message.COMMAND_USAGE
                            .placeholder("usage", commandData.getUsage(commandLabel)));
                } else {
                    value = arg != null && !arg.defaultValue().isEmpty() && i >= args.length ? arg.defaultValue()
                            : args[i];
                }

                final ArgumentTypeAdapter<?> typeAdapter = CommandHandler.getInstance().getRegistry()
                        .findTypeAdapter(parameter.getType());

                if (typeAdapter != null) {
                    invokeParams[i] = typeAdapter.parse(sender, value);

                    if (invokeParams[i] == null) {
                        typeAdapter.handleException(sender, value);
                        return true;
                    }
                } else {
                    invokeParams[i] = value;
                }
            }

            final Object[] finalParams = ArrayUtils.add(invokeParams, 0, method.getParameters()[0].getType().cast(sender));

            method.invoke(data.getCommandObject(), finalParams);
        } else if (parameters.length == 1 && parameters[0].getType().isArray()) {
            method.invoke(data.getCommandObject(), sender, args);
        } else {
            method.invoke(data.getCommandObject(), sender);
        }
        return false;
    }

    private boolean sendMessage(CommandSender sender, Message... toSend) {
        sender.sendMessage(Message.LEGACY_PREFIX
                + Arrays.stream(toSend).map(msg -> msg.toLegacyString()).collect(Collectors.joining(" ")));
        return true;
    }
}