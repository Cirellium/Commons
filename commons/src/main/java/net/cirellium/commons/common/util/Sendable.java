package net.cirellium.commons.common.util;

/**
 * A functional interface that sends something to a receiver
 * 
 * @param <R> The type of the receiver
 * @author Fear
 */
@FunctionalInterface
public interface Sendable<R> {
   
    /**
     * Send the given object to the receiver
     * 
     * @param receiver The receiver
     */
    void send(R receiver);
}