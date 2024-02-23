package net.cirellium.commons.common.command.annotation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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

    public enum SenderType {
        PLAYER, CONSOLE, BOTH;

        public boolean matches(Object sender) {
            if (this == BOTH) {
                return true;
            }
            if (this == PLAYER) {
                return sender.getClass().getSimpleName().contains("Player");
            }
            if (this == CONSOLE) {
                return sender.getClass().getSimpleName().contains("Console");
            }
            return false;
        }
    }

}