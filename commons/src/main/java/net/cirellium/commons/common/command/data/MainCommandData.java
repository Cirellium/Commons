package net.cirellium.commons.common.command.data;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.cirellium.commons.common.command.annotation.annotations.Command;
import net.cirellium.commons.common.command.annotation.annotations.SubCommand;

@Data
@EqualsAndHashCode(callSuper=false)
public final class MainCommandData extends CommandData<Command> {

    private Command command;

    private List<SubCommandData> subCommands;

    public MainCommandData(Method method, Object commandObject) {
        this.method = method;
        this.commandObject = commandObject;
        this.command = method.getAnnotation(Command.class);        
        super.annotation = method.getAnnotation(Command.class);

        this.subCommands = new ArrayList<SubCommandData>();
    }

    public final SubCommandData findSubCommand(String passedArgument) {
        return getSubCommands().stream()
                .filter(subCmd -> subCmd.getSubCommand().label().equalsIgnoreCase(passedArgument)
                        || Arrays.stream(subCmd.getSubCommand().aliases())
                                .anyMatch(alias -> alias.equalsIgnoreCase(passedArgument)))
                .findFirst().orElse(null);
    }

    public final boolean hasSubCommand(String commandLabel) {
        return getSubCommands().stream()
                .anyMatch(subCmd -> subCmd.getSubCommand().label().equalsIgnoreCase(commandLabel)
                        || Arrays.stream(subCmd.getSubCommand().aliases()).anyMatch(alias -> alias.equalsIgnoreCase(commandLabel)));
    }

    public final boolean hasSubCommand(SubCommand subCommand) {
        return subCommand.mainCommand().equalsIgnoreCase(command.label());
    }
}