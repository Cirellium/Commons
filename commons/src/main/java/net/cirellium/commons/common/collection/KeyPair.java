package net.cirellium.commons.common.collection;

import java.util.Map;

public class KeyPair<Key, Value> implements Map.Entry<Key, Value> {

    private Key key;
    private Value value;

    public KeyPair(Key key, Value value) {
        this.key = key;
        this.value = value;
    }

    public Key getKey() {
        return key;
    }

    public Value getValue() {
        return value;
    }

    @Override
    public Value setValue(Value value) {
        this.value = value;
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof KeyPair) {
            KeyPair<?, ?> pair = (KeyPair<?, ?>) obj;
            return pair.getKey().equals(key) && pair.getValue().equals(value);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return key.hashCode() + value.hashCode();
    }

    @Override
    public String toString() {
        return "KeyPair [key=" + key + ", value=" + value + "]";
    }
}