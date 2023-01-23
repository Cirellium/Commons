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

import lombok.Getter;
import lombok.Setter;
import net.cirellium.commons.common.file.CPluginFile;

public class DatabaseOptions {

    @Getter
    @Setter
    private int syncInterval, saveInterval;

    @Getter
    @Setter
    private String username, address, password, database, tablePrefix;

    @Getter
    @Setter
    private boolean useSSL;

    public DatabaseOptions(
            int syncInterval,
            int saveInterval,
            String databaseUser,
            String databaseAddress,
            String databasePassword,
            String databaseName,
            String tablePrefix,
            boolean useSSL) {
        this.syncInterval = syncInterval;
        this.saveInterval = saveInterval;
        this.username = databaseUser;
        this.address = databaseAddress;
        this.password = databasePassword;
        this.database = databaseName;
        this.tablePrefix = tablePrefix;
        this.useSSL = useSSL;
    }

    public static DatabaseOptions from(CPluginFile<?> databaseFile) {
        return new DatabaseOptions(
                databaseFile.getValueAs(int.class, "sync-interval").orElse(0),
                databaseFile.getValueAs(int.class, "save-interval").orElse(0),
                databaseFile.getValueAs(String.class, "user").orElse("user"),
                databaseFile.getValueAs(String.class, "address").orElse("address"),
                databaseFile.getValueAs(String.class, "password").orElse("password"),
                databaseFile.getValueAs(String.class, "database").orElse("database"),
                databaseFile.getValueAs(String.class, "table-prefix").orElse("table-prefix"),
                databaseFile.getValueAs(boolean.class, "use-ssl").orElse(false));
    }
}