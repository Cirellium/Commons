/**
* Copyright (C) 2023 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Tue Jan 10 2023 16:50:42
*
* MethodProcessor.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.bukkit.command.annotation;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.logging.Logger;

import org.bukkit.command.CommandSender;

import net.cirellium.commons.bukkit.command.abstraction.AbstractCommand.SenderType;
import net.cirellium.commons.bukkit.command.annotation.annotations.Argument;
import net.cirellium.commons.bukkit.command.annotation.annotations.Command;
import net.cirellium.commons.bukkit.command.annotation.argument.ArgumentData;
import net.cirellium.commons.bukkit.command.annotation.argument.ArgumentTypeHandler;
import net.cirellium.commons.bukkit.command.annotation.argument.Type;
import net.cirellium.commons.bukkit.command.annotation.exception.CommandProcessException;
import net.cirellium.commons.common.util.Processor;

@SuppressWarnings({ "rawtypes", "unused" })
public class CommandProcessor implements Processor<Method, Set<CommandNode>> {

    Logger logger = CommandHandler.getInstance().getLogger();

    public Function<Parameter, Class<? extends ArgumentTypeHandler>> specificType = parameter -> parameter.isAnnotationPresent(Type.class) ? (parameter.getAnnotation(Type.class)).value() : null;
    
    public Function<String, String[]> enhanceArgs = name -> (name = name.toLowerCase().trim()).contains(" ") ? name.split(" ") : new String[] { name };

    public Set<CommandNode> process(Method value) {
        // logger.info("Processing method " + value.getName());

        // logger.info("Annotation count: " + value.getAnnotations().length);
        // logger.info("Annotation 0: " + value.getAnnotations()[0].toString());

        // logger.info("Parameter count: " + value.getParameterCount());
        // logger.info("Parameters: " + value.getParameters());

        if (!value.isAnnotationPresent(Command.class)) {
            throw new CommandProcessException("Every method must have the @Command annotation! ("
                    + value.getDeclaringClass().getName() + ":" + value.getName() + ")");
        }

        if (!(value.getParameterCount() >= 1)) {
            throw new CommandProcessException("Every method must have at least one parameter! ("
                    + value.getDeclaringClass().getName() + ":" + value.getName() + ")");
        }

        if (!CommandSender.class.isAssignableFrom(value.getParameterTypes()[0])) {
            throw new CommandProcessException("The first parameter must be a CommandSender! ("
                    + value.getDeclaringClass().getName() + ":" + value.getName() + ")");
        }

        Command command = value.getAnnotation(Command.class);
        Class<?> owningClass = value.getDeclaringClass();

        logger.info("Registering command " + command.names()[0] + " from " + owningClass.getName());

        ArrayList<ArgumentData> args = new ArrayList<>();

        if (value.getParameterCount() > 1) {
            for (int i = 1; i < value.getParameterCount(); i++) {
                Parameter parameter = value.getParameters()[i];
                if (!parameter.isAnnotationPresent(Argument.class)) {
                    throw new CommandProcessException(
                            "Every parameter except the sender must have the @Argument annotation! ("
                                    + value.getDeclaringClass().getName() + ":" + value.getName() + ")");
                
                }
                Argument arg = parameter.getAnnotation(Argument.class);

                ArgumentData data = new ArgumentData(
                        arg.name(),
                        arg.defaultValue(),
                        parameter.getType(),
                        arg.wildcard(),
                        i,
                        Set.of(arg.tabCompleteFlags()),
                        specificType.apply(parameter));

                args.add(data);
            }
        }
        HashSet<CommandNode> registered = new HashSet<>();
        for (String name : command.names()) {
            boolean change = true;
            boolean hadChild = false;
            // logger.info("name 1: " + name);

            // String[] cmdNames = (name = name.toLowerCase().trim()).contains(" ") ?
            // name.split(" ") : new String[1]; ! OLD
            String[] cmdNames = enhanceArgs.apply(name);

            // logger.info("cmdNames[0]: " + cmdNames[0].toString());
            String primary = cmdNames[0];
            // logger.info("primary: " + primary);
            CommandNode workingNode = new CommandNode(owningClass);
            if (CommandRegistry.ROOT_COMMAND_NODE.hasCommand(primary)) {
                workingNode = CommandRegistry.ROOT_COMMAND_NODE.getNode(primary);
                change = false;
            }
            if (change) {
                workingNode.setName(cmdNames[0]);
            } else {
                if (workingNode.hasAliases()) workingNode.getAliases().add(cmdNames[0]);
            }
            CommandNode parentNode = new CommandNode(owningClass);

            if (workingNode.hasCommand(cmdNames[0])) {
                parentNode = workingNode.getNode(cmdNames[0]);
            } else {
                parentNode.setName(cmdNames[0]);
                parentNode.setOwningClass(owningClass);
                parentNode.setPermission("");
                parentNode.setSenderType(SenderType.BOTH);
            }
            if (cmdNames.length > 1) {
                hadChild = true;
                workingNode.registerCommand(parentNode);
                CommandNode childNode = new CommandNode(owningClass);
                for (int i = 1; i < cmdNames.length; i++) {
                    String subName = cmdNames[i];
                    childNode.setName(subName);
                    if (parentNode.hasCommand(subName)) childNode = parentNode.getNode(subName);
                    parentNode.registerCommand(childNode);
                    if (i == cmdNames.length - 1) {
                        childNode.setMethod(value);
                        childNode.setOwningClass(owningClass);
                        childNode.setAsync(command.async());
                        childNode.setHidden(command.hidden());
                        childNode.setPermission(command.permission());
                        childNode.setDescription(command.description());
                        childNode.setSenderType(command.senderType());
                        childNode.setArguments(args);
                        childNode.setDebugMode(command.debug());
                    } else {
                        parentNode = childNode;
                        childNode = new CommandNode(owningClass);
                    }
                }
            }
            if (!hadChild) {
                parentNode.setMethod(value);
                parentNode.setAsync(command.async());
                parentNode.setHidden(command.hidden());
                parentNode.setPermission(command.permission());
                parentNode.setDescription(command.description());
                parentNode.setSenderType(command.senderType());
                parentNode.setArguments(args);
                parentNode.setDebugMode(command.debug());
                workingNode.registerCommand(parentNode);
            }
            CommandRegistry.ROOT_COMMAND_NODE.registerCommand(workingNode);
            registered.add(workingNode);
        }
        return registered;

    }

}