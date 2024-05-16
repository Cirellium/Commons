package net.cirellium.commons.common.util;

import net.cirellium.commons.common.exception.InitializationException;
import net.cirellium.commons.common.exception.ShutdownException;

public interface Lifecycle<T> extends Initializable<T>, Shutdownable<T> {
    
    @Override
    public void initialize(T t) throws InitializationException;

    @Override
    public void shutdown(T t) throws ShutdownException;

    public static interface Controller<C> extends Lifecycle<C> {}
}