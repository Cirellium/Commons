package net.cirellium.commons.common.data.storage.implementation.sql.implementation;

import net.cirellium.commons.common.data.storage.implementation.sql.connection.sql.HikariConnector;
import net.cirellium.commons.common.data.user.AbstractCirelliumUser;

public abstract class ForwardingStorage<CUser extends AbstractCirelliumUser<CUser>> extends HikariStorage<CUser> {

    public ForwardingStorage(HikariConnector connector) {
        super(connector);
    }
}