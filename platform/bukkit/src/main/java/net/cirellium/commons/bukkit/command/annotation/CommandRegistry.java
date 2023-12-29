package net.cirellium.commons.bukkit.command.annotation;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import lombok.Data;
import net.cirellium.commons.bukkit.command.annotation.adapter.ArgumentTypeAdapter;
import net.cirellium.commons.bukkit.command.annotation.adapter.implementation.BooleanTypeAdapter;
import net.cirellium.commons.bukkit.command.annotation.adapter.implementation.DoubleTypeAdapter;
import net.cirellium.commons.bukkit.command.annotation.adapter.implementation.FloatTypeAdapter;
import net.cirellium.commons.bukkit.command.annotation.adapter.implementation.IntegerTypeAdapter;
import net.cirellium.commons.bukkit.command.annotation.adapter.implementation.ItemStackTypeAdapter;
import net.cirellium.commons.bukkit.command.annotation.adapter.implementation.OfflinePlayerTypeAdapter;
import net.cirellium.commons.bukkit.command.annotation.adapter.implementation.PlayerTypeAdapter;
import net.cirellium.commons.bukkit.command.annotation.adapter.implementation.StringTypeAdapter;
import net.cirellium.commons.bukkit.command.annotation.adapter.implementation.UUIDTypeAdapter;
import net.cirellium.commons.bukkit.command.annotation.adapter.implementation.WorldTypeAdapter;
import net.cirellium.commons.bukkit.command.annotation.annotation.Command;
import net.cirellium.commons.bukkit.command.annotation.annotation.SubCommand;
import net.cirellium.commons.bukkit.command.annotation.data.CommandData;
import net.cirellium.commons.bukkit.command.annotation.data.SubCommandData;
import net.cirellium.commons.common.logger.CirelliumLogger;
import net.cirellium.commons.common.util.clazz.ClassUtils;
import net.cirellium.commons.common.version.Platform;

@Data
public class CommandRegistry {

    private final List<CommandData> registeredCommands;

    private final List<ArgumentTypeAdapter<?>> typeAdapters;

    private final String defaultPrefix;

    private final Logger logger = new CirelliumLogger(Platform.BUKKIT, "CommandsAnnotated2");

    public CommandRegistry(String prefix) {
        this.defaultPrefix = prefix;
        this.registeredCommands = new ArrayList<CommandData>();
        this.typeAdapters = new ArrayList<ArgumentTypeAdapter<?>>();

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
        initialize();
    }

    public void initialize() {
        registerPackage("net.cirellium.commons");
        registerPackage("net.cirellium.core");
    }

    public void registerPackage(String packageName) {
        logger.info("Registering commands in package " + packageName);

        Set<Class<?>> classes = new HashSet<Class<?>>(
                ClassUtils.getClassesInPackage(JavaPlugin.getProvidingPlugin(CommandRegistry.class), packageName));

        logger.info("Found " + classes.size() + " classes in package " + packageName);

        classes.stream().forEach(clazz -> registerClass(clazz));
    }

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
                .map(method -> new CommandData(method, object))
                .forEach(commandData -> {
                    this.registeredCommands.add(commandData);
                    this.getCommandMap().register(defaultPrefix, new CirelliumCommand(commandData));
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

    private Set<Method> getMethodsByAnnotation(Class<?> clazz, Class<? extends Annotation> annotation) {
        return Arrays.stream(clazz.getMethods())
                .filter(method -> method.getAnnotation(annotation) != null)
                .collect(Collectors.toSet());
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

    @SuppressWarnings("unchecked")
    public <T> ArgumentTypeAdapter<T> findTypeAdapter(Class<T> type) {
        return (ArgumentTypeAdapter<T>) this.typeAdapters.stream().filter(adapter -> adapter.supports(type)).findAny()
                .orElseThrow(() -> new IllegalArgumentException("No type adapter found for type " + type.getName()));
    }
}