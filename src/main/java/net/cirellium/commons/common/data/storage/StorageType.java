/**
* Copyright (C) 2022 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Fri Dec 30 2022 14:32:52
*
* StorageType.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.common.data.storage;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * This enum represents the different storage types.
 * Additionally, it provides a method to get the storage type by name.
 */
public enum StorageType {   

    // Remote database types
    MYSQL("MySQL"),
    //MONGODB("MongoDB"),
    MARIADB("MariaDB"),
    POSTGRESQL("PostgreSQL"),

    // Local database types
    SQLITE("SQLite"),
    // H2("H2"),

    // Local config types
    YAML("YAML"),
    JSON("JSON"),
    ;

    private String name;

    StorageType(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the storage type.
     *
     * @return the name of the storage type
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the storage type by name.
     *
     * @param name the name of the storage type
     * @return the storage type
     */
    public static Optional<StorageType> parse(String name) {
        return Stream.of(values()).filter(type -> type.getName().equalsIgnoreCase(name)).findFirst();
    }
}