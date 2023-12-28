/**
* Copyright (C) 2023 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Tue Jan 10 2023 14:12:21
*
* ArgumentRegistry.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.bukkit.command.annotation;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import com.google.common.collect.Lists;

import lombok.NonNull;
import net.cirellium.commons.bukkit.CirelliumBukkitPlugin;
import net.cirellium.commons.bukkit.command.annotation.annotations.SubCommand;
import net.cirellium.commons.bukkit.command.annotation.argument.ArgumentTypeHandler;
import net.cirellium.commons.bukkit.command.annotation.argument.implementation.IntegerArgumentType;
import net.cirellium.commons.bukkit.command.annotation.argument.implementation.PlayerArgumentType;
import net.cirellium.commons.bukkit.command.annotation.argument.implementation.StringArgumentType;
import net.cirellium.commons.common.util.clazz.ClassUtils;

@SuppressWarnings({ "unused" })
public class CommandRegistry<P extends CirelliumBukkitPlugin<P>> {

    public static final CommandNode ROOT_COMMAND_NODE = new CommandNode();

    private static final VarHandle MODIFIERS;

    private Map<Class<?>, ArgumentTypeHandler<?>> ARGUMENT_TYPE_MAP = new HashMap<>();

    private final P plugin;

    private CommandMap commandMap;

    private Map<String, Command> registeredCommands;

    private @NonNull Set<Method> registeredMethods;

    static {
        try {
            var lookup = MethodHandles.privateLookupIn(Field.class, MethodHandles.lookup());
            MODIFIERS = lookup.findVarHandle(Field.class, "modifiers", int.class);
        } catch (IllegalAccessException | NoSuchFieldException ex) {
            throw new RuntimeException(ex);
        }
    }

    public CommandRegistry(P plugin) {
        this.plugin = plugin;
        registeredMethods = new HashSet<Method>();
        initialize();
    }

    public void initialize() {
        // registerArgumentType(boolean.class, (ArgumentType<?>) new
        // BooleanArgumentType());
        registerArgumentTypeHandler(int.class, (ArgumentTypeHandler<?>) new IntegerArgumentType());
        // registerArgumentType(double.class, (ArgumentType<?>) new
        // DoubleArgumentType());
        // registerArgumentType(float.class, (ArgumentType<?>) new FloatArgumentType());
        registerArgumentTypeHandler(String.class, (ArgumentTypeHandler<?>) new StringArgumentType());
        registerArgumentTypeHandler(Player.class, (ArgumentTypeHandler<?>) new PlayerArgumentType());
        // registerArgumentType(World.class, (ArgumentType<?>) new WorldArgumentType());
        // registerArgumentType(ItemStack.class, (ArgumentType<?>) new
        // ItemStackArgumentType());
        // registerArgumentType(OfflinePlayer.class, (ArgumentType<?>) new
        // OfflinePlayerArgumentType());
        // registerArgumentType(UUID.class, (ArgumentType<?>) new UUIDArgumentType());
        // registerArgumentType(OfflinePlayerWrapper.class, (ArgumentType<?>) new
        // OfflinePlayerWrapperArgumentType());

        commandMap = getCommandMap();

        plugin.getLogger().info("CommandMap is null: " + String.valueOf(commandMap == null));
        plugin.getLogger().info("CommandMap: " + commandMap.toString());

        registeredCommands = getRegisteredCommands();
        registerCommands();
    }

    public void registerCommands() {
        // registerClass(TestCommand.class);
        registerPackage("net.cirellium.commons");
        registerPackage("net.cirellium.core");

        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            try {
                swapCommandMap();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 5L);
    }

    public void registerArgumentTypeHandler(Class<?> clazz, ArgumentTypeHandler<?> type) {
        ARGUMENT_TYPE_MAP.put(clazz, type);
    }

    public ArgumentTypeHandler<?> getArgumentTypeHandler(Class<?> clazz) {
        return ARGUMENT_TYPE_MAP.get(clazz);
    }

    @SuppressWarnings("unchecked")
    protected Map<String, Command> getRegisteredCommands() {
        try {
            Field knownCommands = SimpleCommandMap.class.getDeclaredField("knownCommands");
            knownCommands.setAccessible(true);

            return (Map<String, Command>) knownCommands.get(commandMap);
        } catch (ReflectiveOperationException ignored) {
        }
        return null;
    }

    public void registerMethod(Method method) {
        method.setAccessible(true);
        if (!method.isAnnotationPresent(net.cirellium.commons.bukkit.command.annotation.annotations.Command.class)
                || !method.isAnnotationPresent(SubCommand.class))
            return;

        Set<CommandNode> nodes = new CommandProcessor().process(method);

        if (nodes == null)
            return;

        nodes.forEach(node -> {
            if (node != null) {
                ExecutableCommand command = new ExecutableCommand(node,
                        JavaPlugin.getProvidingPlugin(method.getDeclaringClass()));
                register(command);
                // node.getChildren().values().forEach(children -> {});
            }
        });
        registeredMethods.add(method);
    }

    private void register(ExecutableCommand command) {
        try {
            Map<String, Command> knownCommands = getRegisteredCommands();
            Iterator<Map.Entry<String, Command>> iterator = knownCommands.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Command> entry = iterator.next();
                if (!((Command) entry.getValue()).getName().equalsIgnoreCase(command.getName()))
                    continue;
                ((Command) entry.getValue()).unregister(commandMap);
                iterator.remove();
            }
            for (String alias : command.getAliases())
                knownCommands.put(alias, command);
            command.register(commandMap);
            knownCommands.put(command.getName(), command);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void registerClass(Class<?> clazz) {
        boolean hasCommandMethod = false;

        // for (Method m : clazz.getMethods()) {
        // if
        // (m.isAnnotationPresent(net.cirellium.commons.bukkit.command.annotation.Command.class))
        // {
        // hasCommandMethod = true;
        // }
        // }

        // if(!hasCommandMethod) return;

        CommandHandler.getInstance().getLogger().info("Registering class " + clazz.getName() + " to command registry");
        Lists.newArrayList(clazz.getMethods()).forEach(m -> registerMethod(m));
    }

    public void unregisterClass(Class<?> clazz) {
        Map<String, Command> knownCommands = getRegisteredCommands();
        Iterator<Command> iterator = knownCommands.values().iterator();
        while (iterator.hasNext()) {
            Command command = iterator.next();
            if (!(command instanceof ExecutableCommand)
                    || ((ExecutableCommand) command).getNode().getOwningClass() != clazz)
                continue;
            command.unregister(commandMap);
            iterator.remove();
        }
    }

    public void registerAll(Plugin plugin) {
        registerPackage(plugin, plugin.getClass().getPackage().getName());
    }

    public void registerPackage(Plugin plugin, String packageName) {
        // ClassUtils.getClassesInPackage(plugin, packageName).forEach(clazz ->
        // registerClass(clazz));

        try {
            plugin.getLogger().info("PackageName: " + plugin.getClass().getPackageName());
            ClassUtils.getAllClasses(plugin.getClass().getPackageName()).forEach(clazz -> registerClass(clazz));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void registerPackage(String packageName) {
        plugin.getLogger().info("PackageName: " + packageName);
        getCommandMethods(packageName)
                .stream()
                .filter(m -> !(registeredMethods.contains(m)))
                .forEach(m -> registerMethod(m));
    }

    public Set<Method> getCommandMethods(String packageName) {
        Reflections reflections = new Reflections(
                new ConfigurationBuilder()
                        .setUrls(ClasspathHelper.forPackage(packageName))
                        .setScanners(Scanners.MethodsAnnotated));

        return reflections
                .getMethodsAnnotatedWith(net.cirellium.commons.bukkit.command.annotation.annotations.Command.class);
    }

    private static CommandMap getCommandMap() {
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

    /**
     * Swap the value of the commandMap field in the Bukkit server instance with a
     * reference to a new CommandMapAlternative instance.
     * Allows for overriding the default behaviour of command execution.
     * 
     * @throws Exception if an error occurs during the process of modifying the
     *                   commandMap field in the Bukkit server instance
     * 
     */
    private void swapCommandMap() throws Exception {
        Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
        commandMapField.setAccessible(true);

        Object oldCommandMap = commandMapField.get(Bukkit.getServer());
        CommandMapAlternative newCommandMap = new CommandMapAlternative(Bukkit.getServer());

        Field knownCommandsField = SimpleCommandMap.class.getDeclaredField("knownCommands");
        knownCommandsField.setAccessible(true);
        knownCommandsField.set(newCommandMap, knownCommandsField.get(oldCommandMap));

        commandMapField.set(Bukkit.getServer(), newCommandMap);
    }
}