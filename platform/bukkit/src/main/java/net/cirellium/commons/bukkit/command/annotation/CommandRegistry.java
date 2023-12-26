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

    // protected static CommandMap getCommandMap() {
    //     return Bukkit.getCommandMap();

    //     // return (CommandMap) (new
    //     // ClassWrapper(Bukkit.getServer())).getField("commandMap").get();
    // }

    private static CommandMap getCommandMap() {
        try {
            Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            return (CommandMap) commandMapField.get(Bukkit.getServer());
        } catch (ReflectiveOperationException ignored) {}
        return null;
    }

    @SuppressWarnings("unchecked")
    protected Map<String, Command> getRegisteredCommands() {
        try {
            Field commandMapField = commandMap.getClass().getDeclaredField("knownCommands");
            commandMapField.setAccessible(true);
            return (Map<String, Command>) commandMapField.get(commandMap);
        } catch (ReflectiveOperationException ignored) {}
        return null;
    }

    public void registerMethod(Method method) {
        method.setAccessible(true);
        if (!method.isAnnotationPresent(net.cirellium.commons.bukkit.command.annotation.annotations.Command.class))
            return;

        Set<CommandNode> nodes = new CommandProcessor().process(method);

        if (nodes == null)
            return;

        nodes.forEach(node -> {
            if (node != null) {
                ExecutableCommand command = new ExecutableCommand(node, JavaPlugin.getProvidingPlugin(method.getDeclaringClass()));
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
                .setScanners(Scanners.MethodsAnnotated)
        );

        return reflections.getMethodsAnnotatedWith(net.cirellium.commons.bukkit.command.annotation.annotations.Command.class);
    }

    private void swapCommandMap() throws Exception {
        Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
        MODIFIERS.set(commandMapField, commandMapField.getModifiers() & ~Modifier.FINAL);
        commandMapField.setAccessible(true);

        // CraftCommandMap
        Object oldCommandMap = commandMapField.get(Bukkit.getServer());
        CommandMapAlternative newCommandMap = new CommandMapAlternative(Bukkit.getServer());

        // knownCommands -> HashMap<String, Command>()

        Field testField = oldCommandMap.getClass().getSuperclass().getDeclaredField("knownCommands");
        MODIFIERS.set(testField, testField.getModifiers() & ~Modifier.FINAL);
        testField.setAccessible(true);
        // plugin.getLogger().info("testField: " +
        // testField.get(oldCommandMap).toString());

        Field knownCommandsField = SimpleCommandMap.class.getDeclaredField("knownCommands");
        MODIFIERS.set(knownCommandsField, knownCommandsField.getModifiers() & ~Modifier.FINAL);
        knownCommandsField.setAccessible(true);

        // Field modifiersField = Field.class.getDeclaredField("modifiers");
        // modifiersField.setAccessible(true);
        // modifiersField.setInt(knownCommandsField, knownCommandsField.getModifiers() &
        // 0xFFFFFFEF); // ! doesn't work anymore
        MODIFIERS.set(knownCommandsField, knownCommandsField.getModifiers() & 0xFFFFFFEF);

        knownCommandsField.set(newCommandMap, knownCommandsField.get(oldCommandMap));

        // knownCommandsField.set(knownCommandsField.get(oldCommandMap), newCommandMap);

        commandMapField.set(Bukkit.getServer(), newCommandMap);
    }
}