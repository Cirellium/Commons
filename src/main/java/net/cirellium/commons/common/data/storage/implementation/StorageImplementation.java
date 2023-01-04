/**
* Copyright (C) 2022 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Sun Jan 01 2023 10:37:06
*
* StorageImplementation.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.common.data.storage.implementation;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import net.cirellium.commons.common.data.user.AbstractCirelliumUser;

/**
 * This interface is used to provide methods for the storage of users.
 * It is implemented by the {@link SqlStorage} class and
 */
public interface StorageImplementation {
    
    AbstractCirelliumUser<?> getUser(UUID uuid);

    AbstractCirelliumUser<?> getUser(String name);

    void saveUser(AbstractCirelliumUser<?> user);

    void deleteUser(AbstractCirelliumUser<?> user);

    Map<UUID, AbstractCirelliumUser<?>> getUsers(Set<UUID> uuids);

    Map<UUID, AbstractCirelliumUser<?>> getUsers();

    void saveUsers(Set<AbstractCirelliumUser<?>> users);

    void deleteUsers(Set<AbstractCirelliumUser<?>> users);

    UUID getPlayerUUID(String name);

    String getPlayerName(UUID uuid);

    // ! Log getLog();
    // ! void logAction(Action logAction);

}