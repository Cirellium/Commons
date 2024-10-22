/**
* Copyright (C) 2022 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Sun Jan 22 2023 18:59:00
*
* Connector.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.common.data.storage.implementation.sql.connection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Function;

import net.cirellium.commons.common.plugin.CirelliumPlugin;
import net.cirellium.commons.common.util.Lifecycle;
import net.cirellium.commons.common.util.Processor;

public interface SqlConnector extends Processor<String, Function<String, String>>, Lifecycle<CirelliumPlugin<?>>  {
    
    Connection getConnection() throws SQLException;

    String getName();

    Function<String, String> process(String query);

    @Override
    void initialize(CirelliumPlugin<?> plugin);

    @Override
    void shutdown(CirelliumPlugin<?> plugin);
}