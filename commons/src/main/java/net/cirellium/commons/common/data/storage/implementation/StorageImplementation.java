/**
* Copyright (C) 2023 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Sun Jan 01 2023 10:37:06
*
* StorageImplementation.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.common.data.storage.implementation;

import java.util.Set;
import java.util.UUID;

import net.cirellium.commons.common.collection.CMap;
import net.cirellium.commons.common.data.storage.implementation.sql.SqlStorage;
import net.cirellium.commons.common.data.user.AbstractCirelliumUser;

/**
 * This interface is used to provide methods for the storage of users.
 * It is implemented by the {@link SqlStorage} class and
 */
public interface StorageImplementation<CUser extends AbstractCirelliumUser> {
    
    public CMap<UUID, CUser> getUsers();

    public CUser getUser(UUID uuid);
    
    public CMap<UUID, CUser> getUsers(Set<UUID> uuids);

    public CUser getUser(String name);

    public void saveUser(CUser user);

    public void saveUsers(Set<CUser> users);

    public void deleteUser(CUser user);

    public void deleteUsers(Set<CUser> users);

    public UUID getPlayerUUID(String name);

    public String getPlayerName(UUID uuid);

    // ! Log getLog();
    // ! void logAction(Action logAction);

}