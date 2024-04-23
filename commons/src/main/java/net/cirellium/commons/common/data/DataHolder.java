package net.cirellium.commons.common.data;

import java.util.Collection;

import net.cirellium.commons.common.util.Provider;

public interface DataHolder<D, I> extends Provider<D, I> {

    @Override
    default D provide(I id) {
        return getData(id);
    }

    Collection<D> getData();

    D getData(I id);

    void setData(Collection<D> data);

    default boolean hasData() {
        return !getData().isEmpty();
    }
}