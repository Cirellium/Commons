package net.cirellium.commons.common.data;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

import net.cirellium.commons.common.util.Identifiable;

public interface Loadable<D extends Data> extends Identifiable<UUID>, DataHolder<D, UUID> {

    @Override
    UUID getId();

    @Override
    void setId(UUID uuid);

    @Override
    Collection<D> getData();

    @Override
    void setData(Collection<D> data);

    default public <K extends D> K findData(Class<K> clazz) {
        return getData()
                .stream()
                .filter(Objects::nonNull)
                .filter(data -> clazz.isAssignableFrom(data.getClass()))
                .map(clazz::cast)
                .findFirst()
                .orElse(null);
    }

    default boolean hasData(Class<? extends D> clazz) {
        return findData(clazz) != null;
    }
}