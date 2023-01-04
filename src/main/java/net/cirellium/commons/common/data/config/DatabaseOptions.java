/**
* Copyright (C) 2022 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Fri Dec 30 2022 17:01:23
*
* SQLCredentials.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.common.data.config;

import org.bukkit.configuration.ConfigurationSection;

public class DatabaseOptions {
    
    private int syncInterval, saveInterval;

    private String databaseUser, databaseHost, databasePassword, databaseName, tablePrefix, sqliteFileName;

    private boolean useSSL;

    public DatabaseOptions(int syncInterval, int saveInterval, String databaseUser, String databaseHost, String databasePassword, String tablePrefix, String sqliteFileName, boolean useSSL) {
        this.syncInterval = syncInterval;
        this.saveInterval = saveInterval;
        this.databaseUser = databaseUser;
        this.databaseHost = databaseHost;
        this.databasePassword = databasePassword;
        this.tablePrefix = tablePrefix;
        this.sqliteFileName = sqliteFileName;
        this.useSSL = useSSL;
    }

    public int getSyncInterval() {
        return syncInterval;
    }

    public void setSyncInterval(int syncInterval) {
        this.syncInterval = syncInterval;
    }

    public int getSaveInterval() {
        return saveInterval;
    }

    public void setSaveInterval(int saveInterval) {
        this.saveInterval = saveInterval;
    }

    public String getDatabaseUser() {
        return databaseUser;
    }

    public void setDatabaseUser(String databaseUser) {
        this.databaseUser = databaseUser;
    }

    public String getDatabaseHost() {
        return databaseHost;
    }

    public void setDatabaseHost(String databaseHost) {
        this.databaseHost = databaseHost;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public void setDatabasePassword(String databasePassword) {
        this.databasePassword = databasePassword;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getTablePrefix() {
        return tablePrefix;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    public String getSqliteFileName() {
        return sqliteFileName;
    }

    public void setSqliteFileName(String sqliteFileName) {
        this.sqliteFileName = sqliteFileName;
    }

    public boolean useSSL() {
        return useSSL;
    }

    public void setUseSSL(boolean useSSL) {
        this.useSSL = useSSL;
    }

    public static DatabaseOptions from(ConfigurationSection configurationSection) {
        return new DatabaseOptions(
            configurationSection.getInt("sync-interval"),
            configurationSection.getInt("save-interval"),
            configurationSection.getString(".sql.database-user"),
            configurationSection.getString(".sql.database-host"),
            configurationSection.getString(".sql.database-password"),
            configurationSection.getString("table-prefix"),
            configurationSection.getString("sqlite-file-name"),
            configurationSection.getBoolean("use-ssl")
        );
    }
}