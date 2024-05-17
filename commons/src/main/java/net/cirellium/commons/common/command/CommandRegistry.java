package net.cirellium.commons.common.command;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import lombok.Data;
import net.cirellium.commons.common.command.annotation.adapter.implementation.CommandArgumentTypeAdapter;
import net.cirellium.commons.common.command.data.MainCommandData;
import net.cirellium.commons.common.logger.SimpleCirelliumLogger;
import net.cirellium.commons.common.version.Platform;

@Data
public abstract class CommandRegistry {

    protected final List<MainCommandData> registeredCommands;

    protected final List<CommandArgumentTypeAdapter<?>> typeAdapters;

    protected final String defaultPrefix;

    protected final Logger logger = new SimpleCirelliumLogger(Platform.UNKNOWN, "CommandsAnnotated2");

    public CommandRegistry(String prefix) {
        this.defaultPrefix = prefix;
        this.registeredCommands = new ArrayList<MainCommandData>();
        this.typeAdapters = new ArrayList<CommandArgumentTypeAdapter<?>>();
        initialize();
    }

    public void initialize() {
        registerPackage("net.cirellium.commons");
        registerPackage("net.cirellium.core");
    }

    public abstract void registerPackage(String packageName);

    public abstract void registerClass(Class<?> clazz);

    protected Set<Method> getMethodsByAnnotation(Class<?> clazz, Class<? extends Annotation> annotation) {
        return Arrays.stream(clazz.getMethods())
                .filter(method -> method.getAnnotation(annotation) != null)
                .collect(Collectors.toSet());
    }

    @SuppressWarnings("unchecked")
    public <T> CommandArgumentTypeAdapter<T> findTypeAdapter(Class<T> type) {
        return (CommandArgumentTypeAdapter<T>) this.typeAdapters.stream().filter(adapter -> adapter.supports(type)).findAny()
                .orElseThrow(() -> new IllegalArgumentException("No type adapter found for type " + type.getName()));
    }
}