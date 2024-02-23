/**
* Copyright (C) 2023 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Sat Dec 31 2022 09:36:34
*
* HikariConnector.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.common.data.storage.implementation.sql.connection.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.ImmutableList;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import net.cirellium.commons.common.data.storage.credentials.SQLOptions;
import net.cirellium.commons.common.data.storage.credentials.SQLOptions.HikariCredentials;
import net.cirellium.commons.common.data.storage.implementation.sql.connection.Connector;
import net.cirellium.commons.common.logger.CirelliumLogger;
import net.cirellium.commons.common.plugin.CirelliumPlugin;

public abstract class HikariConnector implements Connector {

    private final SQLOptions options;

    private HikariDataSource dataSource;

    public HikariConnector(SQLOptions options) {
        this.options = options;
    }

    @Override
    public void initialize(CirelliumPlugin<?> plugin) {
        HikariConfig config;

        try {
            config = new HikariConfig();
        } catch (LinkageError e) {
            handleClassloadingError(e, plugin);
            throw e;
        }

        config.setPoolName(options.getDatabase() + "-hikari");

        String username = options.getUsername();
        String[] fullAddress = options.getAddress().split(":");
        String address = fullAddress[0];
        String port = fullAddress.length > 1 ? fullAddress[1] : defaultPort();
        String password = options.getPassword();
        String database = options.getDatabase();
        String tablePrefix = options.getTablePrefix();

        HikariCredentials credentials = HikariCredentials.builder()
                .address(address)
                .port(port)
                .user(username)
                .password(password)
                .database(database)
                .tablePrefix(tablePrefix)
                .build();

        try {
            configureDatabase(config, credentials);
        } catch (LinkageError e) {
            handleClassloadingError(e, plugin);
            throw e;
        }

        Map<String, String> properties = options.getProperties();

        defaultProperties(properties);

        setProperties(config, properties);

        config.setMaximumPoolSize(options.getMaxPoolSize());
        config.setMinimumIdle(options.getMinIdle());
        config.setMaxLifetime(options.getMaxLifetime());
        config.setConnectionTimeout(options.getConnectionTimeout());
        config.setKeepaliveTime(options.getKeepaliveTime());

        config.setInitializationFailTimeout(-1);

        this.dataSource = new HikariDataSource(config);

        postInitialize(plugin);
    }

    @Override
    public void shutdown(CirelliumPlugin<?> plugin) {
        if (this.dataSource != null) {
            this.dataSource.close();
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (this.dataSource == null) {
            throw new SQLException("Unable to get a connection from the pool. (dataSource is null)");
        }

        Connection connection = this.dataSource.getConnection();
        if (connection == null) {
            throw new SQLException("Unable to get a connection from the pool. (getConnection returned null)");
        }

        return connection;
    }

    protected void defaultProperties(Map<String, String> properties) {
        properties.putIfAbsent("socketTimeout", TimeUnit.SECONDS.toMillis(30) + "");
    }

    protected void setProperties(HikariConfig config, Map<String, String> properties) {
        properties.forEach(config::addDataSourceProperty);
    }

    protected abstract String defaultPort();

    protected abstract void configureDatabase(HikariConfig config, HikariCredentials credentials);
    
    protected void postInitialize(CirelliumPlugin<?> plugin) {
    }

    private static void handleClassloadingError(Throwable throwable, CirelliumPlugin<?> plugin) {
        List<String> noteworthyClasses = ImmutableList.of(
                "org.slf4j.LoggerFactory",
                "org.slf4j.ILoggerFactory",
                "org.apache.logging.slf4j.Log4jLoggerFactory",
                "org.apache.logging.log4j.spi.LoggerContext",
                "org.apache.logging.log4j.spi.AbstractLoggerAdapter",
                "org.slf4j.impl.StaticLoggerBinder",
                "org.slf4j.helpers.MessageFormatter"
        );

        CirelliumLogger logger = plugin.getLogger();
        logger.warning("HikariCP has failed to load (" + throwable.getClass().getSimpleName() + "). This is usually caused by another plugin interfering with the classpath.");
        logger.warning("Try loading "  + plugin.getPluginName() + " without any of the plugins listed below to see if the issue persists.");
        logger.warning("The following plugins were found to be interfering with HikariCP:");

        for (String className : noteworthyClasses) {
            Class<?> clazz;
            try {
                clazz = Class.forName(className);
            } catch (Exception e) {
                continue;
            }

            ClassLoader loader = clazz.getClassLoader();
            String loaderName;
            try {
                loaderName = " (" + loader.toString() + ")";
            } catch (Throwable e) {
                loaderName = loader.toString();
            }

            logger.warning("Class " + className + " has been loaded by: " + loaderName);
        }
    }
}