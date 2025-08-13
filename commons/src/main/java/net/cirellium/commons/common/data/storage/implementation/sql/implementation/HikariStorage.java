package net.cirellium.commons.common.data.storage.implementation.sql.implementation;

import net.cirellium.commons.common.data.storage.implementation.sql.SqlStorage;
import net.cirellium.commons.common.data.storage.implementation.sql.connection.sql.HikariConnector;
import net.cirellium.commons.common.data.user.AbstractCirelliumUser;

public abstract class HikariStorage<CUser extends AbstractCirelliumUser<CUser>> extends SqlStorage<CUser> {

    public HikariStorage(HikariConnector connector) {
        super(connector);
    }
}