/**
* Copyright (C) 2023 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Tue Jan 10 2023 14:12:01
*
* Command.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.bukkit.command.annotation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.cirellium.commons.bukkit.command.abstraction.AbstractCommand.SenderType;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface Command {

    String[] names() default {};

    String permission() default "cirellium.command.other";

    String description() default "";

    SenderType senderType() default SenderType.BOTH;

    boolean hidden() default false;

    boolean tabComplete() default true;

    boolean async() default false;

    boolean debug() default false;

}