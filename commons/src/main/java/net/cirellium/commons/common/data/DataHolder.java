package net.cirellium.commons.common.data;

import java.util.Collection;

public abstract class DataHolder {
    
    protected final Collection<Data> data;

    public DataHolder(Collection<Data> data) {
        this.data = data;
    }

}