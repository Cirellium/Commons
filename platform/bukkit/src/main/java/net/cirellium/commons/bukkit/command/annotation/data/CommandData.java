package net.cirellium.commons.bukkit.command.annotation.data;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import net.cirellium.commons.bukkit.command.annotation.annotation.Command;
import net.cirellium.commons.bukkit.command.annotation.annotation.SubCommand;

@Data
public class CommandData {
    
    private Method method;

    private Object commandObject;

    private Command command;

    private List<SubCommandData> subCommands;

    public CommandData(Method method, Object commandObject) {
        this.method = method;
        this.commandObject = commandObject;
        this.command = method.getAnnotation(Command.class);
        this.subCommands = new ArrayList<SubCommandData>();
    }

    public boolean hasSubCommand(SubCommand subCommand) {
        return subCommand.mainCommand().equalsIgnoreCase(command.label());
    }
}