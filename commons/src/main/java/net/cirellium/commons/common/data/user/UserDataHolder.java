/**
* Copyright (C) 2023 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Fri Dec 30 2022 17:01:13
*
* UserDataHolder.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.common.data.user;

import java.util.Collection;
import java.util.UUID;

import net.cirellium.commons.common.collection.CList;
import net.cirellium.commons.common.collection.CMap;
import net.cirellium.commons.common.data.DataHolder;

public interface UserDataHolder<L extends LoadableUser> extends DataHolder<L, UUID> {

    L getUser(UUID uuid);

    @Override
    default L getData(UUID uuid) {
        return getUser(uuid);
    }

    @Override
    void setData(Collection<L> data);

    CMap<UUID, L> getUsers();

    @Override
    default CList<L> getData() {
        return new CList<>(getUsers().values());
    }

    void addUser(L user);

    void removeUser(UUID uuid);
}