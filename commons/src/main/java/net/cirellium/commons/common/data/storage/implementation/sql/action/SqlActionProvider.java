/**
* Copyright (C) 2022 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Mon Jan 23 2023 10:58:14
*
* SqlActionProvider.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.common.data.storage.implementation.sql.action;

import net.cirellium.commons.common.collection.CMap;
import net.cirellium.commons.common.util.Provider;
import net.cirellium.commons.common.util.SimpleProvider;

/**
 * A provider for {@link SqlAction}s.
 * 
 * This class is used to provide a list of custom {@link SqlAction}s.
 * 
 * @author Fear
 * @see SqlAction
 */
public interface SqlActionProvider extends SimpleProvider<CMap<? extends SqlAction, String>> {
    
    @Override
    CMap<? extends SqlAction, String> provide();

}