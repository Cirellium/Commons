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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

import net.cirellium.commons.common.collection.CMap;
import net.cirellium.commons.common.data.storage.implementation.Storage;
import net.cirellium.commons.common.data.storage.implementation.sql.connection.SqlConnector;
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

    protected SqlConnector connector;

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

    public void getResultSet(String query, Consumer<ResultSet> resultSet) {
        execute(connection -> {
            try {
                try (PreparedStatement pStatement = connection.prepareStatement(query)) {
                    try (ResultSet rSet = pStatement.executeQuery()) {
                        resultSet.accept(rSet);
                        rSet.close();
                    } finally {
                        pStatement.close();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public CompletableFuture<Void> getResultSetAsync(String query, Consumer<ResultSet> resultSet) {
        return getAsync(() -> getResultSet(query, resultSet));
    }

    public void executeUpdate(String update) {
        execute(connection -> {
            try {
                connection.createStatement().executeUpdate(update);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void execute(Consumer<Connection> consumer) {
        try (Connection connection = connector.getConnection()) {
            consumer.accept(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public CompletableFuture<Void> getAsync(Runnable runnable) {
        return CompletableFuture.runAsync(runnable);
    }

    public CompletableFuture<?> getAsync(Supplier<?> supplier) {
        return CompletableFuture.supplyAsync(supplier);
    }

    public Object getAsyncDirect(Supplier<?> supplier) {
        return getAsync(supplier).join();
    }
    
}