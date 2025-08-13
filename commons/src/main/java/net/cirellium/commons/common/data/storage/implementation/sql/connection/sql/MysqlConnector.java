/**
* Copyright (C) 2023 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Sat Dec 31 2022 09:36:57
*
* MysqlConnector.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.common.data.storage.implementation.sql.connection.sql;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Map;
import java.util.function.Function;

import com.zaxxer.hikari.HikariConfig;

import net.cirellium.commons.common.data.storage.credentials.SQLOptions;
import net.cirellium.commons.common.data.storage.credentials.SQLOptions.HikariCredentials;
import net.cirellium.commons.common.plugin.CirelliumPlugin;

public class MysqlConnector extends HikariConnector {

    public MysqlConnector(SQLOptions options) {
        super(options);
    }

    @Override
    protected void configureDatabase(HikariConfig config, HikariCredentials credentials) {
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setJdbcUrl("jdbc:mysql://" + credentials.getAddress() + ":" + credentials.getPort() + "/"
                + credentials.getDatabase());

        config.setUsername(credentials.getUser());
        config.setPassword(credentials.getPassword());
    }

    @Override
    protected void defaultProperties(Map<String, String> p) {
        // Taken from https://github.com/brettwooldridge/HikariCP/wiki/MySQL-Configuration
        p.putIfAbsent("cachePrepStmts", "true");
        p.putIfAbsent("prepStmtCacheSize", "250");
        p.putIfAbsent("prepStmtCacheSqlLimit", "2048");
        p.putIfAbsent("useServerPrepStmts", "true");
        p.putIfAbsent("useLocalSessionState", "true");
        p.putIfAbsent("rewriteBatchedStatements", "true");
        p.putIfAbsent("cacheResultSetMetadata", "true");
        p.putIfAbsent("cacheServerConfiguration", "true");
        p.putIfAbsent("elideSetAutoCommits", "true");
        p.putIfAbsent("alwaysSendSetIsolation", "false");
        p.putIfAbsent("cacheCallableStmts", "true");
        p.putIfAbsent("maintainTimeStats", "false");

        p.putIfAbsent("serverTimezone", "UTC");

        super.defaultProperties(p);
    }

    @Override
    protected void postInitialize(CirelliumPlugin<?> plugin) {
        super.postInitialize(plugin);

        // Unregister the driver, because we don't need it
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            if (driver.getClass().getName().equals("com.mysql.cj.jdbc.Driver")) {
                try {
                    DriverManager.deregisterDriver(driver);
                } catch (SQLException e) {
                }
            }
        }
    }

    @Override
    public String getName() {
        return "MySQL";
    }

    @Override
    protected String defaultPort() {
        return "3306";
    }

    @Override
    public Function<String, String> process(String query) {
        return s -> s.replace('\'', '`');
    }
}