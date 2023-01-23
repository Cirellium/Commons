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

public abstract class DatabaseControllerService<P extends CirelliumPlugin<P>> extends AbstractService<P> {

    protected DatabaseOptions options;

    // protected AbstractDatabaseConnector connector;

    public DatabaseControllerService(P plugin) {
        this(plugin, StorageType.MYSQL);
    }

    public DatabaseControllerService(P plugin, StorageType storageType) {
        super(plugin, ServiceType.DATABASE, ServiceType.FILE);


        // switch (storageType) {
        //     case MYSQL:
        //     //case MONGODB:
        //     case MARIADB:
        //         this.connector = new DatabaseConnectorSQL(plugin, options);
        //         break;
        //     case SQLITE:
        //         this.connector = new DatabaseConnectorSQLite(plugin, options);
        //         break;
        //     case YAML:
        //         this.connector = null;


        //         break;
        //     case JSON:
        //         this.connector = null;

                
        //         break;
        //     case POSTGRESQL:
        //         this.connector = null;

                
        //         break;
        //     default:
        //         break;
        // }
            

    }

    @Override
    public void initialize() {


    }

    @Override
    public void shutdown() {
        
    }

    // public abstract DatabaseFile<?> getDatabaseFile();    
}