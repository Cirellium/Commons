package net.cirellium.commons.common.command.data;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import lombok.Data;
import net.cirellium.commons.common.command.CommandHandler;
import net.cirellium.commons.common.command.annotation.annotations.Argument;
import net.cirellium.commons.common.command.annotation.annotations.SubCommand;

@Data
public abstract class CommandData<A extends Annotation> {

    protected Method method;

    protected Object commandObject;

    protected A annotation;

    public String getUsage(String label) {
        final StringBuilder builder = new StringBuilder().append("/" + label + " ");
        Parameter[] parameters = method.getParameters();

        if (isSubCommand())
            builder.append(method.getAnnotation(SubCommand.class).label() + " ");

        for (int i = 0; i < parameters.length; i++) {
            final Parameter parameter = parameters[i];
            final Argument argument = parameter.getAnnotation(Argument.class);
            if (argument == null)
                continue;

            builder.append("<" + argument.name() + ">");
            if (i < parameters.length - 1)
                builder.append(" ");
        }

        return builder.toString();
    }

    public boolean isSubCommand() {
        return method.getAnnotation(SubCommand.class) != null;
    }

    public A getAnnotation() {
        return (A) annotation;
    }

    public void log(String msg) {
        CommandHandler.getInstance().getLogger().info(msg);
    }
}