package net.cirellium.commons.common.util;

/**
 * An object that can be initialized
 * 
 * @author Fear
 * 
 * @param <I> The parameter type to pass to the {@link #initialize(Object)} method
 */
@FunctionalInterface
public interface Initializable<I> {

    /**
     * Initialize the object
     * 
     * @param i The required object to initialize a process (e.g. used for dependency injection)
     */
    void initialize(I i);
    
}