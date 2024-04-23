package net.cirellium.commons.bukkit.command.annotation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import net.cirellium.commons.bukkit.command.annotation.adapter.ItemStackTypeAdapter;
import net.cirellium.commons.bukkit.command.annotation.adapter.OfflinePlayerTypeAdapter;
import net.cirellium.commons.bukkit.command.annotation.adapter.PlayerTypeAdapter;
import net.cirellium.commons.bukkit.command.annotation.adapter.WorldTypeAdapter;
import net.cirellium.commons.common.command.CommandRegistry;
import net.cirellium.commons.common.command.annotation.adapter.implementation.BooleanTypeAdapter;
import net.cirellium.commons.common.command.annotation.adapter.implementation.DoubleTypeAdapter;
import net.cirellium.commons.common.command.annotation.adapter.implementation.FloatTypeAdapter;
import net.cirellium.commons.common.command.annotation.adapter.implementation.IntegerTypeAdapter;
import net.cirellium.commons.common.command.annotation.adapter.implementation.StringTypeAdapter;
import net.cirellium.commons.common.command.annotation.adapter.implementation.UUIDTypeAdapter;
import net.cirellium.commons.common.command.annotation.annotations.Command;
import net.cirellium.commons.common.command.annotation.annotations.SubCommand;
import net.cirellium.commons.common.command.data.MainCommandData;
import net.cirellium.commons.common.command.data.SubCommandData;
import net.cirellium.commons.common.util.clazz.ClassUtils;

public class BukkitCommandRegistry extends CommandRegistry {

    public BukkitCommandRegistry(String prefix) {
        super(prefix);

        this.typeAdapters.addAll(Arrays.asList(
                new BooleanTypeAdapter(),
                new DoubleTypeAdapter(),
                new FloatTypeAdapter(),
                new IntegerTypeAdapter(),
                new ItemStackTypeAdapter(),
                new OfflinePlayerTypeAdapter(),
                new PlayerTypeAdapter(),
                new StringTypeAdapter(),
                new UUIDTypeAdapter(),
                new WorldTypeAdapter()));
    }

    @Override
    public void registerPackage(String packageName) {
        logger.info("Registering commands in package " + packageName);

        Set<Class<?>> classes = new HashSet<Class<?>>(
                ClassUtils.getClassesInPackage(JavaPlugin.getProvidingPlugin(CommandRegistry.class).getClass(),
                        packageName));

        logger.info("Found " + classes.size() + " classes in package " + packageName);

        classes.stream().forEach(clazz -> registerClass(clazz));
    }

    @Override
    public void registerClass(Class<?> clazz) {
        // logger.info("Checking class " + clazz.getSimpleName());

        final Set<Method> mainCommandMethods = getMethodsByAnnotation(clazz, Command.class);
        final Set<Method> subCommandMethods = getMethodsByAnnotation(clazz, SubCommand.class);

        if (mainCommandMethods.isEmpty() && subCommandMethods.isEmpty())
            return;

        logger.info("Registering commands in class " + clazz.getName());

        final Object object;
        try {
            object = clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException
                | InvocationTargetException e) {
            e.printStackTrace();
            return;
        }

        mainCommandMethods.stream()
                .map(method -> new MainCommandData(method, object))
                .forEach(commandData -> {
                    logger.info("Registering command with name '" + commandData.getCommand().label() + "'");
                    this.registeredCommands.add(commandData);
                    this.getCommandMap().register(defaultPrefix, new CirelliumBukkitCommand(commandData));
                    this.getCommandMap().getCommand(commandData.getCommand().label())
                            .setPermission(commandData.getCommand().permission());
                    this.getCommandMap().getCommand(commandData.getCommand().label())
                            .setAliases(List.of(commandData.getCommand().aliases()));
                    this.getCommandMap().getCommand(commandData.getCommand().label())
                            .setUsage(commandData.getCommand().usage());
                });

        subCommandMethods.stream()
                .filter(method -> registeredCommands.stream()
                        .anyMatch(commandData -> commandData
                                .hasSubCommand(method.getAnnotation(SubCommand.class))))
                .forEach(method -> registeredCommands.stream()
                        .filter(data -> data.hasSubCommand(method.getAnnotation(SubCommand.class)))
                        .findFirst().ifPresent(parentCommand -> parentCommand.getSubCommands()
                                .add(new SubCommandData(method, object))));
    }

    private CommandMap getCommandMap() {
        CommandMap commandMap = null;
        try {
            Server server = Bukkit.getServer();
            Method getCommandMap = server.getClass().getDeclaredMethod("getCommandMap");
            getCommandMap.setAccessible(true);
            commandMap = (CommandMap) getCommandMap.invoke(server);

            return commandMap;
        } catch (ReflectiveOperationException ignored) {
        }
        return null;
    }

}