package net.cirellium.commons.bukkit.command.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import lombok.SneakyThrows;
import net.cirellium.commons.bukkit.command.annotation.invoker.BukkitCommandInvoker;
import net.cirellium.commons.common.command.CommandHandler;
import net.cirellium.commons.common.command.annotation.adapter.ArgumentTypeAdapter;
import net.cirellium.commons.common.command.annotation.annotations.Argument;
import net.cirellium.commons.common.command.data.CommandData;
import net.cirellium.commons.common.command.data.MainCommandData;
import net.cirellium.commons.common.command.data.SubCommandData;
import net.cirellium.commons.common.command.result.CommandExecutionResult;

public class CirelliumBukkitCommand extends org.bukkit.command.Command implements PluginIdentifiableCommand {

    private final MainCommandData data;

    public CirelliumBukkitCommand(MainCommandData data) {
        super(data.getCommand().label(), data.getCommand().description(), data.getCommand().usage(),
                Arrays.asList(data.getCommand().aliases()));
        this.data = data;

        setPermission(data.getCommand().permission());
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] passedArguments) {
        SubCommandData subCommand = null;
        if (passedArguments.length >= 1 && !data.getSubCommands().isEmpty()) {
            subCommand = data.findSubCommand(passedArguments[0]);
        }

        final CommandData<?> commandData = subCommand != null ? subCommand : data;

        final String[] args = subCommand != null ? Arrays.copyOfRange(passedArguments, 1, passedArguments.length)
                : passedArguments;
        final Method method = subCommand != null ? subCommand.getMethod() : data.getMethod();
        final String permission = subCommand != null ? subCommand.getSubCommand().permission()
                : data.getCommand().permission();

        final BukkitCommandInvoker invoker = new BukkitCommandInvoker(sender);

        if (!invoker.hasPermission(permission)) {
            invoker.sendMessage(CommandExecutionResult.ERROR_NO_PERMISSION.placeholder("permission", permission));
            return true;
        }

        return invoke(commandData, method, invoker, commandLabel, passedArguments, args);
    }

    @SneakyThrows
    private boolean invoke(CommandData<?> commandData, Method method, BukkitCommandInvoker sender, String commandLabel,
            String[] passedArguments, String[] args) {
        final Parameter[] parameters = Arrays.copyOfRange(method.getParameters(), 1, method.getParameters().length);

        if (parameters.length > 0 && !parameters[0].getType().isArray()) {
            final Object[] invokeParams = new Object[parameters.length];

            if (passedArguments.length == 0) {
                sender.sendMessage(CommandExecutionResult.NOT_ENOUGH_ARGUMENTS.toMessage(),
                        CommandExecutionResult.USAGE.placeholder("usage", commandData.getUsage(commandLabel)));
                return true;
            }

            for (int i = 0; i < parameters.length; i++) {
                final Parameter parameter = parameters[i];
                final Argument arg = parameter.getAnnotation(Argument.class);
                final String value;

                if (i >= args.length && (arg == null || arg.defaultValue().isEmpty())) {
                    sender.sendMessage(CommandExecutionResult.NOT_ENOUGH_ARGUMENTS.toMessage(),
                            CommandExecutionResult.USAGE.placeholder("usage", commandData.getUsage(commandLabel)));
                    return true;
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

            Bukkit.getLogger().info("Parameter type: " + method.getParameters()[0].getType());

            final Object[] finalParams = new Object[invokeParams.length + 1];
            finalParams[0] = method.getParameters()[0].getType().cast(sender);
            System.arraycopy(invokeParams, 0, finalParams, 1, invokeParams.length);

            method.invoke(data.getCommandObject(), finalParams);
        } else if (parameters.length == 1 && parameters[0].getType().isArray()) {
            method.invoke(data.getCommandObject(), sender, args);
        } else {
            method.invoke(data.getCommandObject(), sender);
        }
        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String label, String[] args) {
        List<String> completions = new ArrayList<String>();

        if (args.length == 1) {
            data.getSubCommands().stream()
                    .filter(subCmd -> subCmd.getSubCommand().mainCommand().equalsIgnoreCase(label))
                    .filter(subCmd -> subCmd.getAnnotation().label().toLowerCase().startsWith(args[0].toLowerCase()))
                    .filter(subCmd -> sender.hasPermission(subCmd.getSubCommand().permission()))
                    .forEach(subCmd -> completions.add(subCmd.getSubCommand().label()));
            return completions;
        } else if (args.length < 1) {
            CommandData<? extends Annotation> currentCommand = null, subCommand = null;

            for (int i = 0; i < args.length; i++) {
                if (data.hasSubCommand(args[i])) {
                    subCommand = data.findSubCommand(args[i]);

                    if (subCommand == null)
                        break;
                }
                currentCommand = subCommand;
            }

            Method commandMethod = currentCommand.getMethod();

            Class<?> parameterType = commandMethod.getParameterTypes()[args.length - 1];

            ArgumentTypeAdapter<?> adapter = CommandHandler.getInstance().getRegistry().findTypeAdapter(parameterType);

            if (adapter != null) {
                completions.addAll(
                        adapter.tabComplete(new BukkitCommandInvoker(sender), Set.of(args), args[args.length - 1]));
            }

            return completions;
        }

        return completions;
    }

    @Override
    public Plugin getPlugin() {
        return JavaPlugin.getProvidingPlugin(getClass());
    }
}