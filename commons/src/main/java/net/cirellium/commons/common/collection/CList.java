/**
* Copyright (C) 2022 Cirellium Network - All Rights Reserved
*
* Created by FearMyShotz on Sun Jan 22 2023 12:55:19
*
* CList.java is part of Cirellium Commons
*
* Unauthorized copying of this file, via any medium is strictly prohibited
*/
package net.cirellium.commons.common.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

import com.google.common.collect.BiMap;

public class CList<T> extends ArrayList<T> {

    public CList() {
        super();
    }

    @SafeVarargs
    public CList(T... t) {
        super();
        for (T t1 : t) {
            this.add(t1);
        }
    }

    public CList(List<T> l) {
        super(l);
    }

    public CList(Map<T, ?> m) {
        this(m.keySet());
    }

    public CList(BiMap<?, T> m) {
        this(m.values());
    }

    public CList(Set<T> c) {
        super(c);
    }

    public CList(Collection<T> c) {
        super(c);
    }

    public CList(int initialCapacity) {
        super(initialCapacity);
    }

    public T pop() {
        return remove(size() - 1);
    }

    public T pop(int index) {
        return remove(index);
    }

    public T first() {
        return get(0);
    }

    public T middle() {
        return get(middleIndex());
    }

    public T last() {
        return get(size() - 1);
    }

    public T random() {
        return get(randomIndex());
    }

    public void push(T t) {
        add(t);
    }

    public void push(T t, int index) {
        add(index, t);
    }

    @SuppressWarnings("unchecked")
    public CList<T> add(T... t) {
        for (T i : t) {
            super.add(i);
        }

        return this;
    }

    public CList<T> add(List<T> l) {
        super.addAll(l);

        return this;
    }

    public CList<T> add(Collection<T> c) {
        addAll(c);

        return this;
    }

    public CList<T> add(Enumeration<T> e) {
        while (e.hasMoreElements()) {
            super.add(e.nextElement());
        }

        return this;
    }

    public CList<T> addNonNull(T t) {
        if (t != null) {
            super.add(t);
        }

        return this;
    }

    public CList<T> addNonNull(Collection<T> c) {
        for (T t : c) {
            addNonNull(t);
        }

        return this;
    }

    public CList<T> addNonNull(List<T> l) {
        for (T t : l) {
            addNonNull(t);
        }

        return this;
    }

    public CList<T> addNonNull(Enumeration<T> e) {
        while (e.hasMoreElements()) {
            addNonNull(e.nextElement());
        }

        return this;
    }

    public CList<T> addNonNull(Map<T, ?> m) {
        for (T t : m.keySet()) {
            addNonNull(t);
        }

        return this;
    }

    public CList<T> addNonNull(Set<T> s) {
        for (T t : s) {
            addNonNull(t);
        }

        return this;
    }

    public CList<T> addNonNull(T[] t) {
        for (T i : t) {
            addNonNull(i);
        }

        return this;
    }

    public CList<T> addIf(Predicate<T> predicate, T t) {
        if (predicate.test(t)) {
            super.add(t);
        }

        return this;
    }

    public CList<T> addIf(Predicate<T> predicate, Iterable<T> i) {
        for (T t : i) {
            addIf(predicate, t);
        }

        return this;
    }

    public CList<T> addIfMissing(T t) {
        if (!contains(t)) {
            super.add(t);
        }

        return this;
    }

    public CList<T> addIfMissing(Iterable<T> i) {
        for (T t : i) {
            addIfMissing(t);
        }

        return this;
    }

    @SafeVarargs
    public final CList<T> rewrite(T... t) {
        clear();
        add(t);

        return this;
    }

    public CList<T> rewrite(Function<T, T> function) {
        CList<T> l = copy();
        clear();

        for (T t : l) {
            add(function.apply(t));
        }

        return this;
    }

    public CList<T> copy() {
        CList<T> newList = new CList<>();
        newList.addAll(this);

        return newList;
    }

    @SuppressWarnings("unchecked")
    public T[] toArray() {
        return (T[]) super.toArray();
    }

    public CList<T> sort() {
        Collections.sort(this, (a, b) -> a.toString().compareTo(b.toString()));

        return this;
    }

    public CList<T> shuffle() {
        Collections.shuffle(this);

        return this;
    }

    public CList<T> reverse() {
        Collections.reverse(this);

        return this;
    }

    public CList<T> removeDuplicates() {
        CList<T> newList = new CList<>();
        for (T t : this) {
            if (!newList.contains(t)) {
                newList.add(t);
            }
        }

        return newList;
    }

    public <V> CList<V> convert(Function<T, V> function) {
        CList<V> newList = new CList<>();
        for (T t : copy()) {
            newList.add(function.apply(t));
        }

        return newList;
    }

    public CList<T> removeNull() {
        for (T t : this) {
            if (t == null) {
                remove(t);
            }
        }

        return this;
    }

    public CList<T> removeAllIf(Predicate<T> predicate) {
        for (T t : this) {
            if (predicate.test(t)) {
                remove(t);
            }
        }

        return this;
    }

    public boolean hasIndex(int index) {
        return index >= 0 && index < this.size();
    }

    public int middleIndex() {
        return size() % 2 == 0 ? size() / 2 : (size() / 2) + 1;
    }

    public int randomIndex() {
        return (int) (Math.random() * size());
    }
}
