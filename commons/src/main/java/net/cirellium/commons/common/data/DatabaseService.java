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

import net.cirellium.commons.common.data.config.DatabaseOptions;
import net.cirellium.commons.common.data.storage.StorageType;
import net.cirellium.commons.common.plugin.CirelliumPlugin;
import net.cirellium.commons.common.service.AbstractService;
import net.cirellium.commons.common.service.ServiceType;

public abstract class DatabaseService<P extends CirelliumPlugin<P>> extends AbstractService<P> {

    protected DatabaseOptions options;

    // protected AbstractDatabaseConnector connector;

    public DatabaseService(P plugin) {
        this(plugin, StorageType.MYSQL);
    }

    public DatabaseService(P plugin, StorageType storageType) {
        super(plugin, ServiceType.DATABASE, ServiceType.FILE);

    }

    @Override
    public void initialize() {


    }

    @Override
    public void shutdown() {
        
    }  
}