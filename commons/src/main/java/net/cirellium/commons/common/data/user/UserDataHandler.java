package net.cirellium.commons.common.data.user;

import java.util.UUID;

import net.cirellium.commons.common.collection.CMap;
import net.cirellium.commons.common.data.Data;
import net.cirellium.commons.common.data.DataHandler;

public abstract class UserDataHandler<A extends AbstractCirelliumUser<?>> extends DataHandler<A, Data> implements UserDataHolder<A>/*, Lifecycle.Controller<CirelliumPlugin>*/ {

    public UserDataHandler() {
        super();
    }

    @Override
    public void load(A loadable) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'load'");
    }

    @Override
    public void loadAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'loadAll'");
    }

    @Override
    public CMap<UUID, A> getUsers() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUsers'");
    }

    @Override
    public A getUser(UUID uuid) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUser'");
    }

    @Override
    public void addUser(A user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addUser'");
    }

    @Override
    public void removeUser(UUID uuid) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeUser'");
    }
}