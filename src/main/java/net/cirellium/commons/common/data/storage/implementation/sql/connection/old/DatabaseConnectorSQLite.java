/**
* Copyright (C) 2022 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Fri Dec 30 2022 17:29:44
*
* DatabaseConnectorSQLite.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.common.data.storage.implementation.sql.connection.old;

import net.cirellium.commons.common.data.config.DatabaseOptions;
import net.cirellium.commons.common.plugin.CirelliumPlugin;

/**
 * @author FearMyShotz
 * This class implements the {@link AbstractDatabaseConnector} for SQLite databases.
 */
public class DatabaseConnectorSQLite extends AbstractDatabaseConnector {

    public DatabaseConnectorSQLite(CirelliumPlugin<?> plugin, DatabaseOptions options) {
        this(plugin, options.getSqliteFileName());
    }

    public DatabaseConnectorSQLite(CirelliumPlugin<?> plugin, String path) {
        super(plugin, "jdbc:sqlite:" + path);
    }
}