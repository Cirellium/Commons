/**
* Copyright (C) 2022 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Sun Jan 22 2023 13:07:28
*
* CMap.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.common.collection;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

import net.cirellium.commons.common.util.TriConsumer;

public class CMap<K, V> extends ConcurrentHashMap<K, V> {

    public CMap() {
        super();
    }

    public CMap(Map<K, V> m) {
        super(m);
    }

    public CMap(CList<K> l) {
        super(l.size());
        for (K k : l) {
            put(k, null);
        }
    }

    public CMap(int initialCapacity) {
        super(initialCapacity);
    }

    public CMap<K, V> copy() {
        return new CMap<K, V>(this);
    }

    public CMap<K, V> put(Map<K, V> map) {
        putAll(map);

        return this;
    }

    public CMap<K, V> cput(K key, V value) {
        put(key, value);

        return this;
    }

    public CMap<K, V> putNonNull(K key, V value) {
        if (value != null || key != null) {
            put(key, value);
        }

        return this;
    }
    
    public V putOrGet(K key, V value) {
        if (!containsKey(key)) {
            put(key, value);
        }
        
        return get(key);
    }

    public CMap<K, V> rewrite(TriConsumer<K, V, CMap<K, V>> consumer) {
        CMap<K, V> map = copy();

        for (K key : map.k()) {
            consumer.accept(key, get(key), this);
        }

        return this;
    }

    public CMap<K, V> each(BiConsumer<K, V> consumer) {
        for (K key : k()) {
            consumer.accept(key, get(key));
        }

        return this;
    }

    public CList<K> k() { return getKeys(); }

    public CList<K> getKeys() {
        return new CList<>(keySet());
    }

    public CList<V> v() { return getValues(); }

    public CList<V> getValues() {
        return new CList<>(values());
    }

    public CList<KeyPair<K, V>> toKeyPairList() {
        CList<KeyPair<K, V>> list = new CList<>();
        for (Entry<K, V> entry : entrySet()) {
            list.add(new KeyPair<>(entry.getKey(), entry.getValue()));
        }
        return list;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return super.entrySet();
    }


    
    
}