/**
 * Copyright (C) 2023 Cirellium Network - All Rights Reserved
 *
 * Created by FearMyShotz on Mon Dec 24 2022 14:31:28
 *
 * ServiceType.java is part of Cirellium Commons
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 */

package net.cirellium.commons.common.service;

import net.cirellium.commons.common.data.DatabaseService;
import net.cirellium.commons.common.data.cache.CacheService;
import net.cirellium.commons.common.data.user.UserDataHandler;

/**
 * An enum that represents the type of a service.
 * This is used to map services to their type.
 * 
 * If a service has another type, it uses the OTHER type.
 */
public enum ServiceType {
    
    CACHE(CacheService.class),
    COMMAND(null),
    USER_DATA(UserDataHandler.class),
    DATABASE(DatabaseService.class),
    FILE(null),
   

    // This is used for services that have no dependencies
    NONE(null);

    private Class<?> serviceClass;

    ServiceType(Class<?> serviceClass) {
        this.serviceClass = serviceClass;
    }

    public Class<?> getServiceClass() { return serviceClass; }

}