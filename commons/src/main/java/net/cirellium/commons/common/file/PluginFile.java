/**
* Copyright (C) 2022 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Sat Jan 21 2023 14:21:54
*
* IPluginFile.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.common.file;

import java.io.File;
import java.util.Optional;

/**
 * This interface represents a file that is used by a plugin.
 * It is used to load, create, save, reload and get the file.
 * 
 * @author Fear
 * @param <F> File type
 */
public interface PluginFile<F> {

    void create();
    void save();
    void reload();

    File getFile();

    F get();
    F load();

    Object getValue(String path);

    <T> Optional<T> getValueAs(Class<T> clazz, String path);

}