/**
* Copyright (C) 2022 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Mon Jan 23 2023 10:59:32
*
* Provider.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.common.util;

import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;

/**
 * A provider extends the functionality of a {@link Supplier} interface by providing an object of type T while also taking a parameter of type P.
 * 
 * @author Fear
 * @param <T> The type of the object to provide
 * @param <P> The type of the parameter that is passed to the provider
 */
@FunctionalInterface
public interface Provider<T, @Nullable P> extends Supplier<T> {

    T provide(@Nullable P param);

    @Override
    default T get() {
        return provide(null);
    }
}