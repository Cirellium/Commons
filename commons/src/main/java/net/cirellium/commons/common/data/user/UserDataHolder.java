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

import java.util.UUID;

import net.cirellium.commons.common.collection.CMap;
import net.cirellium.commons.common.util.Provider;

public interface UserDataHolder<L extends LoadableUser> extends Provider<L, UUID> {

    L getUser(UUID uuid);

    @Override
    default L provide(UUID uuid) {
        return getUser(uuid);
    }

    CMap<UUID, L> getUsers();

    void addUser(L user);

    void removeUser(UUID uuid);
}