package net.cirellium.commons.common.util;

import javax.inject.Provider;

@FunctionalInterface
public interface SimpleProvider<T> extends Provider<T> {
    
    /**
     * Provides a fully-constructed instance of type {@code T}.
     * 
     * @return The instance of type {@code T}.
     */
    T provide();

    @Override
    default T get() {
        return provide();
    }
}