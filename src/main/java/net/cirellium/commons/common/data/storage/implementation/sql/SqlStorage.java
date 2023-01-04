package net.cirellium.commons.common.data.storage.implementation.sql;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import net.cirellium.commons.common.data.storage.implementation.StorageImplementation;
import net.cirellium.commons.common.data.user.AbstractCirelliumUser;

public class SqlStorage implements StorageImplementation {

    @Override
    public AbstractCirelliumUser<?> getUser(UUID uuid) {
        return null;
    }

    @Override
    public AbstractCirelliumUser<?> getUser(String name) {
        return null;
    }

    @Override
    public void saveUser(AbstractCirelliumUser<?> user) {
        
    }

    @Override
    public void deleteUser(AbstractCirelliumUser<?> user) {
        
    }

    @Override
    public Map<UUID, AbstractCirelliumUser<?>> getUsers(Set<UUID> uuids) {
        
        return null;
    }

    @Override
    public Map<UUID, AbstractCirelliumUser<?>> getUsers() {
        
        return null;
    }

    @Override
    public void saveUsers(Set<AbstractCirelliumUser<?>> users) {
        
        
    }

    @Override
    public void deleteUsers(Set<AbstractCirelliumUser<?>> users) {
        
        
    }

    @Override
    public UUID getPlayerUUID(String name) {
        
        return null;
    }

    @Override
    public String getPlayerName(UUID uuid) {
        
        return null;
    }
    
}