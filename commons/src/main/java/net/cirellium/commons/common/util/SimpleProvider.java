package net.cirellium.commons.common.util;

import javax.inject.Provider;

@FunctionalInterface
public interface SimpleProvider<T> extends Provider<T> {
    
    @Override
    T get();

    default T provide() {
        return get();
    }
}