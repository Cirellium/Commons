/**
* Conet.cirellium.commons.common.data.storage.implementation.sqlllium Network - All Rights Reserved
*
* Created by FearMyShotz on Mon Jan 23 2023 10:54:46
*
* SqlActionRegistry.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.common.data.storage.implementation.sql.action;

import net.cirellium.commons.common.collection.Registry;
import net.cirellium.commons.common.data.storage.implementation.sql.action.SqlAction.DefaultSqlAction;
import net.cirellium.commons.common.plugin.CirelliumPlugin;
import net.cirellium.commons.common.util.Initializable;

public abstract class SqlActionRegistry implements Initializable<CirelliumPlugin<?>> {
    
    protected Registry<Enum<? extends SqlAction>, String> defaultActions;

    protected SqlActionProvider provider;

    public SqlActionRegistry(SqlActionProvider provider) {
        this.provider = provider;
    }

    @Override
    public void initialize(CirelliumPlugin<?> plugin) {
        this.registerDefaultActions();
        this.registerCustomActions();
    }

    public void registerDefaultActions() {
        this.defaultActions = Registry.create();
        
        for (Enum<? extends SqlAction> actionEnum : DefaultSqlAction.values()) {
            if (actionEnum instanceof SqlAction action) {
                this.defaultActions.register(actionEnum, action.getStatement());
            }
        }
    }

    public abstract void registerCustomActions();
}