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

public class CMap<Key, Value> extends ConcurrentHashMap<Key, Value> {

    public CMap() {
        super();
    }

    public CMap(Map<Key, Value> m) {
        super(m);
    }

    public CMap(CList<Key> l) {
        super(l.size());
        for (Key k : l) {
            put(k, null);
        }
    }

    public CMap(int initialCapacity) {
        super(initialCapacity);
    }

    public CMap<Key, Value> copy() {
        return new CMap<Key, Value>(this);
    }

    public CMap<Key, Value> put(Map<Key, Value> map) {
        putAll(map);

        return this;
    }

    public CMap<Key, Value> cput(Key key, Value value) {
        put(key, value);

        return this;
    }

    public CMap<Key, Value> putNonNull(Key key, Value value) {
        if (value != null || key != null) {
            put(key, value);
        }

        return this;
    }
    
    public Value putOrGet(Key key, Value value) {
        if (!containsKey(key)) {
            put(key, value);
        }
        
        return get(key);
    }

    public CMap<Key, Value> rewrite(TriConsumer<Key, Value, CMap<Key, Value>> consumer) {
        CMap<Key, Value> map = copy();

        for (Key key : map.k()) {
            consumer.accept(key, get(key), this);
        }

        return this;
    }

    public CMap<Key, Value> each(BiConsumer<Key, Value> consumer) {
        for (Key key : k()) {
            consumer.accept(key, get(key));
        }

        return this;
    }

    public CList<Key> k() { return getKeys(); }

    public CList<Key> getKeys() {
        return new CList<>(keySet());
    }

    public CList<Value> v() { return getValues(); }

    public CList<Value> getValues() {
        return new CList<>(values());
    }

    public CList<KeyPair<Key, Value>> toKeyPairList() {
        CList<KeyPair<Key, Value>> list = new CList<>();
        for (Entry<Key, Value> entry : entrySet()) {
            list.add(new KeyPair<>(entry.getKey(), entry.getValue()));
        }
        return list;
    }

    @Override
    public Set<Entry<Key, Value>> entrySet() {
        return super.entrySet();
    }


    
    
}