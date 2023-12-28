package net.cirellium.commons.bukkit.command.annotation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface SubCommand {
    
    String name();

    String[] aliases() default {};

    String mainCommand();

    String permission() default "cirellium.command.other";

    String description() default "";

}