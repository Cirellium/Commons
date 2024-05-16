/**
* Copyright (C) 2023 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Fri Dec 30 2022 17:01:23
*
* SQLCredentials.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.common.data.config;

import org.jetbrains.annotations.Nullable;

import net.cirellium.commons.common.file.PluginFile;
import net.cirellium.commons.common.util.Provider;

public record DatabaseCredentials
    (int syncInterval, int saveInterval, String username, String address, String password, String database, String tablePrefix, boolean useSSL)
    implements Provider<DatabaseCredentials, PluginFile<?>> {

    public static DatabaseCredentials from(PluginFile<?> dF) {
        return new DatabaseCredentials(
                dF.getValue(int.class, "sync-interval"),
                dF.getValue(int.class, "save-interval"),
                dF.getValue(String.class, "user"),
                dF.getValue(String.class, "address"),
                dF.getValue(String.class, "password"),
                dF.getValue(String.class, "database"),
                dF.getValue(String.class, "table-prefix"),
                dF.getValue(boolean.class, "use-ssl"));
    }

    @Override
    public DatabaseCredentials provide(@Nullable PluginFile<?> param) {
        return from(param);
    }
}