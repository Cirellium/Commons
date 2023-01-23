/**
* Copyright (C) 2023 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Sat Dec 31 2022 09:37:03
*
* PostgresqlConnector.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.common.data.storage.implementation.sql.connection.sql;

import java.util.Map;
import java.util.function.Function;

import com.zaxxer.hikari.HikariConfig;

import net.cirellium.commons.common.data.storage.credentials.SQLOptions;
import net.cirellium.commons.common.data.storage.credentials.SQLOptions.HikariCredentials;

public class PostgresqlConnector extends HikariConnector {

    public PostgresqlConnector(SQLOptions credentials) {
        super(credentials);
    }

    @Override
    protected void configureDatabase(HikariConfig config, HikariCredentials credentials) {
        config.setDataSourceClassName("org.postgresql.ds.PGSimpleDataSource");

        config.addDataSourceProperty("serverName", credentials.getAddress());
        config.addDataSourceProperty("portNumber", credentials.getPort());
        config.addDataSourceProperty("databaseName", credentials.getDatabase());
        config.addDataSourceProperty("user", credentials.getUser());
        config.addDataSourceProperty("password", credentials.getPassword());
    }

    @Override
    protected void defaultProperties(Map<String, String> properties) {
        super.defaultProperties(properties);

        properties.remove("useUnicode");
        properties.remove("characterEncoding");
    }

    @Override
    public String getName() {
        return "PostgreSQL";
    }

    @Override
    protected String defaultPort() {
        return "5432";
    }

    @Override
    public Function<String, String> process(String query) {
        return s -> s.replace('\'', '"');
    }
}