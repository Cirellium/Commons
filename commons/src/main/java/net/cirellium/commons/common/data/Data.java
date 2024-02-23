package net.cirellium.commons.common.data;

import net.cirellium.commons.common.data.cache.FastCache;

public interface Data {

    public FastCache<?, ?> getDataCache();

}