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

/**
 * An enum that represents the type of a service.
 * This is used to map services to their type.
 * 
 * If a service has another type, it uses the OTHER type.
 */
public enum ServiceType {
    
    CACHE("Cache"),
    CONFIG("Config"),
    COMMAND("Command"),
    DATABASE("Database"),
    FILE("File"),
    LISTENER("Listener"),
    TASK("Task"),
    PLAYER("Player"),
    ENTITY("Entity"),
    WORLD("World"),
    INVENTORY("Inventory"),
    ITEM("Item"),
    CHAT("Chat"),
    MESSAGE("Message"),
    PERMISSION("Permission"),
    SCOREBOARD("Scoreboard"),
    OTHER("Other"),

    // This is used for services that have no dependencies
    NONE("No dependency");

    private String name;

    ServiceType(String name) {
        this.name = name;
    }

    public String getName() { return name + "Service"; }

    public void setName(String newName) { this.name = newName; }

}