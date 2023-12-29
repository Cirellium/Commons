package net.cirellium.commons.bukkit.command.annotation.data;

import java.lang.reflect.Method;

import lombok.Data;
import net.cirellium.commons.bukkit.command.annotation.annotation.Command;
import net.cirellium.commons.bukkit.command.annotation.annotation.SubCommand;

@Data
public class SubCommandData {
    
    private final Method method;

    private final Object commandObject;

    private final SubCommand subCommand;

    public SubCommandData(Method method, Object commandObject) {
        this.method = method;
        this.commandObject = commandObject;
        this.subCommand = method.getAnnotation(SubCommand.class);
    }

    public boolean isSubCommandOf(Command command) {
        return subCommand.mainCommand().equalsIgnoreCase(command.label());
    }
}