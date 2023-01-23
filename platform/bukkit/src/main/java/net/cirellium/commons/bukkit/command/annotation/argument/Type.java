package net.cirellium.commons.bukkit.command.annotation.argument;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.PARAMETER })
@SuppressWarnings("rawtypes")
public @interface Type {
    Class<? extends ArgumentTypeHandler> value();
}