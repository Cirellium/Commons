/**
* Copyright (C) 2022 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Fri Dec 30 2022 17:13:17
*
* AbstractDatabaseConnector.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.common.data.storage.implementation.sql.connection.old;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import net.cirellium.commons.common.plugin.CirelliumPlugin;

public abstract class AbstractDatabaseConnector {
    
    private final CirelliumPlugin<?> plugin;

    private final String url, username, password;

    private final HikariConfig config;
    private final HikariDataSource dataSource;

    public AbstractDatabaseConnector(CirelliumPlugin<?> plugin, String url) {
        this(plugin, url, null, null);
    }

    public AbstractDatabaseConnector(CirelliumPlugin<?> plugin, String url, String username, String password) {
        this.plugin = plugin;
        this.url = url;
        this.username = username;
        this.password = password;

        this.config = new HikariConfig();
        this.config.setJdbcUrl(url);

        // If the usernames are null, don't set them (SQLite)
        if(username != null) this.config.setUsername(username);
        if(password != null) this.config.setPassword(password);

        this.config.addDataSourceProperty("autoReconnect", "true");
        this.config.addDataSourceProperty("useSSL", "false");
        // Additional properties from NexEngine
        this.config.addDataSourceProperty("cachePrepStmts", "true");
        this.config.addDataSourceProperty("prepStmtCacheSize", "250");
        this.config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        if(this instanceof DatabaseConnectorSQLite) {
            // SQLite connection only needs one connection pool
            this.config.setMaximumPoolSize(1);
            // ! Experimental properties
            this.config.addDataSourceProperty("useUnicode", "true");
            this.config.addDataSourceProperty("characterEncoding", "utf8");
        }

        this.dataSource = new HikariDataSource(config);
    }

    public void closeConnection() {
        dataSource.close();
    }

    public final Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

}