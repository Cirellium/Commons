/**
* Copyright (C) 2022 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Sun Jan 01 2023 10:46:31
*
* SqlStorage.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.common.data.storage.implementation.sql;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import net.cirellium.commons.common.collection.CMap;
import net.cirellium.commons.common.data.storage.implementation.Storage;
import net.cirellium.commons.common.data.storage.implementation.sql.connection.Connector;
import net.cirellium.commons.common.data.user.AbstractCirelliumUser;

/**
 * A class that implements the {@link Storage} interface, representing a SQL storage.
 * 
 * Subclasses of this class must implement the user methods.
 * 
 * @param <CUser> The type of user that this storage will store. (extends {@link AbstractCirelliumUser})
 * 
 * @author Fear
 * @version 1.0
 * @see Storage
 */
public abstract class SqlStorage<CUser extends AbstractCirelliumUser> implements Storage<CUser> {

    protected Connector connector;

    @Override
    public abstract CMap<UUID, CUser> getUsers();

    @Override
    public abstract CUser getUser(UUID uuid);

    @Override
    public abstract CMap<UUID, CUser> getUsers(Set<UUID> uuids);

    @Override
    public abstract CUser getUser(String name);

    @Override
    public abstract void saveUser(CUser user);

    @Override
    public abstract void saveUsers(Set<CUser> users);

    @Override
    public abstract void deleteUser(CUser user);

    @Override
    public abstract void deleteUsers(Set<CUser> users);

    @Override
    public abstract UUID getPlayerUUID(String name);

    @Override
    public abstract String getPlayerName(UUID uuid);

    public void runAsync(Runnable runnable) {
        CompletableFuture.runAsync(runnable);
    }

    public CompletableFuture<?> getAsync(Supplier<?> supplier) {
        return CompletableFuture.supplyAsync(supplier);
    }

    public Object getAsyncDirect(Supplier<?> supplier) {
        return getAsync(supplier).join();
    }
    
}