package net.cirellium.commons.bukkit.command.annotation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.cirellium.commons.bukkit.command.abstraction.AbstractCommand.SenderType;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface Command {

    String label();

    String[] aliases() default {};

    String usage() default "";

    String permission() default "cirellium.command.other";

    String description() default "";

    SenderType senderType() default SenderType.BOTH;

    boolean hidden() default false;

    boolean tabComplete() default true;

    boolean async() default false;

    boolean debug() default false;

}