/**
* Copyright (C) 2022 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Sun Jan 22 2023 18:40:48
*
* LoadableUser.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.common.data.user;

import java.util.UUID;

import net.cirellium.commons.common.collection.CList;
import net.cirellium.commons.common.data.Data;
import net.cirellium.commons.common.data.Loadable;
import net.cirellium.commons.common.file.PluginFile;

/**
 * Represents a user that can be loaded, saved and unloaded.
 */
public interface LoadableUser extends Loadable<Data> {

    CList<Data> getData();

    void saveUser();

    void loadUser();

    UUID getUniqueId();

    String getName();

    void saveTo(PluginFile<?> file);

}