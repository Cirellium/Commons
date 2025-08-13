package net.cirellium.commons.common.util;

import java.util.function.Function;

/**
 * A processor is a function that processes an object and returns its processed value.
 * 
 * @author Fear
 * 
 * @param <T> The type of the object to process
 * @param <R> The type of the processed object
 */
@FunctionalInterface
public interface Processor<T, R> extends Function<T, R> {

    /**
     * Processes the given object and returns the processed object.
     * 
     * @param t The object to process
     * @return The processed object
     */
    R process(T t);

    /**
     * Processes the given object and returns the processed object by calling the {@link #process(Object)} method.
     * 
     * @param t The object to process
     * @return The processed object
     */
    @Override
    default R apply(T t) {
        return process(t);
    }
}