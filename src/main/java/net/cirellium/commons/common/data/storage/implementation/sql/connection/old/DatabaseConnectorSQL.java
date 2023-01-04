/**
* Copyright (C) 2022 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Fri Dec 30 2022 17:29:13
*
* DatabaseConnectorSQL.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.common.data.storage.implementation.sql.connection.old;

import net.cirellium.commons.common.data.config.DatabaseOptions;
import net.cirellium.commons.common.plugin.CirelliumPlugin;

/**
 * @author FearMyShotz
 * 
 * This class implements the {@link AbstractDatabaseConnector} for MySQL databases.
 */
public class DatabaseConnectorSQL extends AbstractDatabaseConnector {

    public DatabaseConnectorSQL(CirelliumPlugin<?> plugin, DatabaseOptions options) {
        this(plugin, options.getDatabaseHost(), options.getDatabaseName(), options.getDatabaseUser(), options.getDatabasePassword());
    }

    public DatabaseConnectorSQL(CirelliumPlugin<?> plugin,
                                String host,
                                String database,
                                String username,
                                String password) {
        super(plugin, "jdbc:mysql://" + host + "/" + database + "?allowPublicKeyRetrieval=true&useSSL=false", username, password);
    }
}