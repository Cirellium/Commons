/**
* Copyright (C) 2022 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Sun Jan 22 2023 12:10:35
*
* Registry.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.common.collection;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 * A registry is a map of elements that can be registered and accessed by a key.
 * 
 * @author Fear
 * @param <K> The key type
 * @param <E> The element type
 */
public class Registry<K, E> implements Iterable<E> {

    protected BiMap<K, E> elements;

    public Registry() {
        this.elements = HashBiMap.create();
    }

    public Registry(Map<K, E> m) {
        this.elements = HashBiMap.create(m);
    }

    public static <K, E> Registry<K, E> create() {
        return new Registry<K, E>();
    }

    public void register(K key, E element) {
        elements.put(key, element);
    }

    public void unregister(K key) {
        elements.remove(key);
    }

    public E get(K key) {
        return elements.get(key);
    }

    public K getKey(E element) {
        return elements.inverse().get(element);
    }

    public E getFirstIf(Predicate<E> predicate) {
        for (E element : elements.values()) {
            if (predicate.test(element)) {
                return element;
            }
        }
        return null;
    }

    public Collection<E> getAllIf(Predicate<E> predicate) {
        Collection<E> elements = new HashSet<>();
        for (E element : this.elements.values()) {
            if (predicate.test(element)) {
                elements.add(element);
            }
        }
        return elements;
    }

    public Collection<E> getAllElements() {
        return new HashSet<E>(elements.values());
    }

    public CList<E> getElementList() {
        return new CList<E>(elements);
    }

    public boolean containsKey(K key) {
        return elements.containsKey(key);
    }

    public boolean containsValue(E element) {
        return elements.containsValue(element);
    }

    public int size() {
        return elements.size();
    }

    public void clear() {
        elements.clear();
    }

    public Map<K, E> getMap() {
        return elements;
    }

    public Stream<E> stream() {
        return elements.values().stream();
    }

    @Override
    public Iterator<E> iterator() {
        return elements.values().iterator();
    }
}