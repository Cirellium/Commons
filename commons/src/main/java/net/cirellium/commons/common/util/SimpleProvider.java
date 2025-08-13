package net.cirellium.commons.common.util;

import java.util.function.Supplier;

/**
 * A simple provider is a supplier that provides an object.
 * 
 * @param <T> The type of the object to provide
 */
@FunctionalInterface
public interface SimpleProvider<T> extends Supplier<T> {
    
    T provide();

    @Override
    default T get() {
        return get();
    }
}