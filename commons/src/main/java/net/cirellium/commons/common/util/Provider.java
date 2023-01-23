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

/**
 * A provider is a supplier that provides an object.
 * 
 * @author Fear
 * @param <T> The type of the object to provide
 */
@FunctionalInterface
public interface Provider<T> extends Supplier<T> {

    @Override
    default T get() {
        return provide();
    }

    T provide();
}