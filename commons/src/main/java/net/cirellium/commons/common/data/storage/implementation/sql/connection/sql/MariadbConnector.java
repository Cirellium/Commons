/**
* Copyright (C) 2023 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Sat Dec 31 2022 09:36:51
*
* MariadbConnector.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.common.data.storage.implementation.sql.connection.sql;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.zaxxer.hikari.HikariConfig;

import net.cirellium.commons.common.data.storage.credentials.SQLOptions;
import net.cirellium.commons.common.data.storage.credentials.SQLOptions.HikariCredentials;

public class MariadbConnector extends HikariConnector {

    public MariadbConnector(SQLOptions credentials) {
        super(credentials);
    }

    @Override
    protected void configureDatabase(HikariConfig config, HikariCredentials credentials) {
        config.setDataSourceClassName("org.mariadb.jdbc.MariaDbDataSource");
        config.addDataSourceProperty("serverName", credentials.getAddress());
        config.addDataSourceProperty("port", credentials.getPort());
        config.addDataSourceProperty("databaseName", credentials.getDatabase());

        config.setUsername(credentials.getUser());
        config.setPassword(credentials.getPassword());
    }

    @Override
    protected void setProperties(HikariConfig config, Map<String, String> properties) {
        String propertiesString = properties.entrySet()
                .stream()
                .map(e -> e.getKey() + "=" + e.getValue())
                .collect(Collectors.joining(";"));
        
        config.addDataSourceProperty("properties", propertiesString);
    }

    @Override
    public String getName() {
        return "MariaDB";
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