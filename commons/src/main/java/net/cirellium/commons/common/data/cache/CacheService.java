package net.cirellium.commons.common.data.cache;

import java.util.logging.Logger;

import net.cirellium.commons.common.collection.CList;
import net.cirellium.commons.common.logger.SimpleCirelliumLogger;
import net.cirellium.commons.common.plugin.CirelliumPlugin;
import net.cirellium.commons.common.service.AbstractService;

public class CacheService<P extends CirelliumPlugin<P>> extends AbstractService<P> {

    protected CList<FastCache<?, ?>> caches;

    public CacheService(P plugin) {
        super(plugin);
    }

    @Override
    public void initialize() {
        
    }

    @Override
    public void shutdown() {
        
    }

    @Override
    public Logger getLogger() {
        return new SimpleCirelliumLogger(getClass().getSimpleName());
    }

    public CList<FastCache<?, ?>> getCaches() {
        return caches;
    }
    
}