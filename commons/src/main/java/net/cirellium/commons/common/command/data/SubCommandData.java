package net.cirellium.commons.common.command.data;

import java.lang.reflect.Method;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.cirellium.commons.common.command.annotations.Command;
import net.cirellium.commons.common.command.annotations.SubCommand;

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