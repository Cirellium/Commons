/**
* Copyright (C) 2023 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Fri Dec 30 2022 17:01:05
*
* DatabaseConnector.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.common.data;

import net.cirellium.commons.common.data.config.DatabaseCredentials;
import net.cirellium.commons.common.data.storage.StorageType;
import net.cirellium.commons.common.plugin.CirelliumPlugin;
import net.cirellium.commons.common.service.AbstractService;
import net.cirellium.commons.common.service.ServiceType;

public abstract class DatabaseService<P extends CirelliumPlugin<P>> extends AbstractService<P> {

    protected DatabaseCredentials options;
    protected StorageType storageType;

    // protected AbstractDatabaseConnector connector;

    public DatabaseService(P plugin) {
        this(plugin, StorageType.MYSQL);
    }

    public DatabaseService(P plugin, StorageType storageType) {
        super(plugin, ServiceType.DATABASE);

        this.storageType = storageType;

    }

    @Override
    public void initialize(P plugin) {


    }

    @Override
    public void shutdown(P plugin) {
        
    }  
}