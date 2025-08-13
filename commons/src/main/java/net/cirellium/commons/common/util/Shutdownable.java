package net.cirellium.commons.common.util;

import net.cirellium.commons.common.exception.ShutdownException;

/**
 * An object that can be shutdown
 * 
 * @author Fear
 * 
 * @param <S> The parameter type to pass to the {@link #shutdown(Object)} method
 */
@FunctionalInterface
public interface Shutdownable<S> {

    /**
     * Shutdown the object
     * 
     * @param s The required object to shutdown a process (e.g. used for dependency injection)
     */
    void shutdown(S s) throws ShutdownException;
    
}