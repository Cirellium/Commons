/**
 * Copyright (C) 2022 Cirellium Network - All Rights Reserved
 *
 * Created by FearMyShotz on Mon Dec 24 2022 14:31:28
 *
 * ServiceType.java is part of Cirellium Commons
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 */

package net.cirellium.commons.common.service;

/**
 * An enum that represents the type of a service.
 * This is used to map services to their type.
 * 
 * If a service has another type, it uses the OTHER type.
 */
public enum ServiceType {
    
    CACHE,
    CONFIG,
    COMMAND,
    DATABASE,
    FILE,
    LISTENER,
    TASK,
    PLAYER,
    ENTITY,
    WORLD,
    INVENTORY,
    ITEM,
    CHAT,
    MESSAGE,
    PERMISSION,
    SCOREBOARD,
    OTHER;

}