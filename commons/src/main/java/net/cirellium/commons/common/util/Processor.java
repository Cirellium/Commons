package net.cirellium.commons.common.util;

/**
 * A processor that processes an object
 * 
 * @author Fear
 * 
 * @param <T> The type of the object to process
 * @param <R> The type of the processed object
 */
@FunctionalInterface
public interface Processor<T, R> {

    /**
     * Process the given object
     * 
     * @param t The object to process
     * @return The processed object
     */
    R process(T t);
}