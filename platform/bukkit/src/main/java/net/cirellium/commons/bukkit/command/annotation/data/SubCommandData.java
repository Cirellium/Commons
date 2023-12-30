package net.cirellium.commons.bukkit.command.annotation.data;

import java.lang.reflect.Method;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.cirellium.commons.bukkit.command.annotation.annotation.Command;
import net.cirellium.commons.bukkit.command.annotation.annotation.SubCommand;

@Data
@EqualsAndHashCode(callSuper=false)
public final class SubCommandData extends CommandData<SubCommand> {

    private final SubCommand subCommand;

    public SubCommandData(Method method, Object commandObject) {
        this.method = method;
        this.commandObject = commandObject;
        this.subCommand = method.getAnnotation(SubCommand.class);
        super.annotation = method.getAnnotation(SubCommand.class);
    }

    public final boolean isSubCommandOf(Command command) {
        return subCommand.mainCommand().equalsIgnoreCase(command.label());
    }
}