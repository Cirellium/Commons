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
import java.util.function.BiConsumer;
import java.util.stream.Stream;

import net.cirellium.commons.common.data.cache.FastCache;
import net.cirellium.commons.common.util.TriConsumer;

/**
 * The {@link CMap} class is a subclass of {@link FastCache} that represents a map with additional utility methods.
 * It provides various methods for manipulating and accessing key-value pairs in the map.
 * 
 * @author @FearMyShotz
 * @param <Key>   the type of keys maintained by this map
 * @param <Value> the type of mapped values
 */
public class CMap<Key, Value> extends FastCache<Key, Value> {

    public CMap() {
        super(100);
    }
    /**
     * Constructs a new {@link CMap} object with the specified map.
     * 
     * @param m the map to initialize the {@link CMap} with
     */
    public CMap(Map<Key, Value> m) {
        super(m);
    }

    /**
     * Constructs a new {@link CMap} object with the specified list of keys.
     * 
     * @param l the list of keys to initialize the {@link CMap} with
     */
    public CMap(CList<Key> l) {
        super(l.size());
        for (Key k : l) {
            put(k, null);
        }
    }

    /**
     * Constructs an empty {@link CMap} with the specified initial capacity and load factor.
     * @param initialCapacity
     */
    public CMap(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Creates a copy of the current {@link CMap} object.
     *
     * @return A new {@link CMap} object that is a copy of the current {@link CMap}.
     */
    public CMap<Key, Value> copy() {
        return new CMap<Key, Value>(this);
    }

    /**
     * Adds all the key-value pairs from the specified map to this {@link CMap}.
     * 
     * @param map the map containing the key-value pairs to be added
     * @return the updated {@link CMap} object
     */
    public CMap<Key, Value> put(Map<Key, Value> map) {
        putAll(map);

        return this;
    }

    /**
     * Inserts the specified key-value pair into the map and returns the updated map.
     *
     * @param key   the key to be inserted
     * @param value the value to be associated with the key
     * @return the updated map after inserting the key-value pair
     */
    public CMap<Key, Value> cput(Key key, Value value) {
        put(key, value);

        return this;
    }

    /**
     * Associates the specified value with the specified key in this map if the value is not null.
     * 
     * @param key the key with which the specified value is to be associated
     * @param value the value to be associated with the specified key
     * @return the current map instance
     */
    public CMap<Key, Value> putNonNull(Key key, Value value) {
        if (value != null || key != null) {
            put(key, value);
        }

        return this;
    }

    /**
     * Returns the value to which the specified key is mapped, or associates the specified value with the specified key in this map if the key is not already associated with a value.
     * 
     * @param key the key with which the specified value is to be associated
     * @param value the value to be associated with the specified key
     * @return the value to which the specified key is mapped, if the key is already associated with a value; otherwise, the specified value
     */
    public Value putOrGet(Key key, Value value) {
        if (!containsKey(key)) {
            put(key, value);
        }

        return get(key);
    }

    /**
     * Rewrites the elements of the map by applying the specified consumer function to each key-value pair.
     * The consumer function receives the key, value, and the current map instance as parameters.
     * 
     * @param consumer the consumer function to apply to each key-value pair
     * @return the current map instance after rewriting
     */
    public CMap<Key, Value> rewrite(TriConsumer<Key, Value, CMap<Key, Value>> consumer) {
        CMap<Key, Value> map = copy();

        for (Key key : map.k()) {
            consumer.accept(key, get(key), this);
        }

        return this;
    }

    /**
     * Applies the given consumer function to each key-value pair in the map.
     * 
     * @param consumer the consumer function to apply to each key-value pair
     * @return the CMap instance itself
     */
    public CMap<Key, Value> each(BiConsumer<Key, Value> consumer) {
        for (Key key : k()) {
            consumer.accept(key, get(key));
        }

        return this;
    }

    /**
     * Returns a list of keys in the CMap.
     *
     * @return a list of keys
     */
    public CList<Key> k() {
        return getKeys();
    }

    /**
     * Returns a list of keys in the CMap.
     *
     * @return a list of keys in the CMap
     */
    public CList<Key> getKeys() {
        return new CList<>(keySet());
    }

    /**
     * Returns a list of values in the map.
     *
     * @return a list of values in the map
     */
    public CList<Value> v() {
        return getValues();
    }

    /**
     * Returns a {@link CList} of values in the map.
     *
     * @return a {@link CList} of values in the map
     */
    public CList<Value> getValues() {
        return new CList<>(values());
    }

    /**
     * Converts the map to a list of key-value pairs.
     *
     * @return a list of key-value pairs representing the map
     */
    public CList<KeyPair<Key, Value>> toKeyPairList() {
        CList<KeyPair<Key, Value>> list = new CList<>();
        for (Entry<Key, Value> entry : entrySet()) {
            list.add(new KeyPair<>(entry.getKey(), entry.getValue()));
        }
        return list;
    }

    /**
     * Returns a sequential Stream of the keys contained in this CMap.
     *
     * @return a sequential Stream of the keys contained in this CMap
     */
    public Stream<Key> streamKeys() {
        return k().stream();
    }

    /**
     * Returns a sequential Stream with the values of this CMap.
     *
     * @return a sequential Stream with the values of this CMap
     */
    public Stream<Value> streamValues() {
        return v().stream();
    }

    /**
     * Returns a stream of KeyPair objects representing the key-value pairs in this CMap.
     *
     * @return a stream of KeyPair objects
     */
    public Stream<KeyPair<Key, Value>> streamKeyPairs() {
        return toKeyPairList().stream();
    }

    /**
     * Returns a set view of the mappings contained in this map.
     *
     * @return a set view of the mappings contained in this map
     */
    @Override
    public Set<Entry<Key, Value>> entrySet() {
        return super.entrySet();
    }
}