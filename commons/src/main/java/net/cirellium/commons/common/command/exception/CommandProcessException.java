package net.cirellium.commons.common.command.exception;

/**
 * Represents an exception that occurs during the processing of a command.
 * This exception is a subclass of the {@link RuntimeException} class.
 */
public class CommandProcessException extends RuntimeException {

    public CommandProcessException(String message) {
        super(message);
    }
    
}