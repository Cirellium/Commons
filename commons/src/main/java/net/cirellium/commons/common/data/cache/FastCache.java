package net.cirellium.commons.common.data.cache;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A <code>FastCache</code> is a map implemented with soft references,
 * optimistic copy-on-write updates, and approximate count-based
 * pruning. It is intended for scalable multi-threaded caches. It
 * sacrifices recall of mappings for speed of put and get operations
 * along with conservative memory guarantees.
 *
 * <p>
 * <i>Note:</i>The class {@linKey HardFastCache} is nearly identical
 * to this class, but with no soft references around hash buckets.
 *
 * <p>
 * The basis of the cache is a fixed-size hash map, based on values
 * returned by objects' <code>hashCode()</code> and
 * <code>equals(Object)</code> methods.
 *
 * <p>
 * The map entries in the hash map are stored in buckets held by
 * soft references. Thus entries in the mapping may be garbage
 * collected. In the implementation, whole hash buckets are
 * collected, which is safe and highly efficient but may require some
 * additional recomputation of values that were removed by pruning.
 *
 * <p>
 * Entries are stored in the map using a very optimistic update.
 * No synchronization at all is performed on the cache entries or
 * their counts. A copy-on-write strategy coupled with Java's memory
 * model for references ensures that the cache remains consistent, if
 * not complete. What this means is that multiple threads may both
 * try to cache a mapping and only one will be saved and/or
 * incremented in count.
 *
 * <p>
 * When the table approximately exceeds the specified load factor,
 * the thread performing the add will perform a garbage collection by
 * reducing reference counts by half, rounding down, and removing
 * entries with zero counts. Pruning is subject to the caveats
 * mentioned in the last paragraph. Counts are not guaranteed to be
 * accurate. Pruning itself is synchronized and more conservatively
 * copy-on-write. By setting the load factor to
 * <code>Double.POSITIVE_INFINITY</code> there will be never be any
 * pruning done by this class; all pruning will take place by soft
 * reference garbage collection.
 *
 * <p>
 * A fast cache acts as a mapping with copy-on-write semantics.
 * Equality and iteration are defined as usual, with the caveat that
 * the snapshot taken of the elements may not be up to date. Iterators
 * may be used concurrently, but their remove methods do not affect
 * the underlying cache.
 *
 * <p>
 * <b>Serialization</b>
 * </p>
 *
 * <p>
 * A fast cache may be serialized if the keys and values it
 * contains are serializable. It may always be serialized if
 * it is first cleared using {@linKey #clear()}.
 *
 * <p>
 * <b>References</b>
 * </p>
 *
 * <p>
 * For more information on soft references, see:
 *
 * <ul>
 * <li>Peter Hagar. 2002.
 * <a href="http://www-128.ibm.com/developerworks/library/j-refs/">Guidelines
 * for using the Java 2 reference classes</a>. IBM DeveloperWorks.
 * <li>Jeff Friesen. 2002. <a href=
 * "http://www.javaworld.com/javaworld/jw-01-2002/jw-0104-java101.html">Trash
 * talk, part 2: reference objects API</a>. JavaWorld.
 * </ul>
 *
 * For information on copy-on-write and optimistic updates,
 * see section 2.4 of:
 *
 * <ul>
 * <li>Doug Lea. 2000.
 * <i>Concurrent Programming in Java. Second Edition.</i>
 * Addison-Wesley.
 * </ul>
 *
 * @author Bob Carpenter, Fear
 * @version 3.8.3
 * @since LingPipe2.2
 * @param <K> the type of keys in the map
 * @param <V> the type of values in the map
 */
public class FastCache<Key, Value> extends ConcurrentHashMap<Key, Value> {

    static final long serialVersionUID = 3003326726041067827L;

    private static final double DEFAULT_LOAD_FACTOR = 0.5;

    private final SoftReference<Record<Key, Value>>[] mBuckets;

    private volatile int mNumEntries = 0;

    private int maxEntries;

    /**
     * Constrcut a fast cache of the specified size and default load
     * factor. The default load factor is 0.5. See {@link
     * #FastCache(int,double)} for more information.
     *
     * @param size Number of buckets in cache
     * @throws IllegalArgumentException if the size is less than 2.
     */
    public FastCache(int size) {
        this(size, DEFAULT_LOAD_FACTOR);
    }

    /**
     * Construct a {@link FastCache} from the specified map. The cache has the
     * initial size of 100 and the default load factor.
     * 
     * If any, the mappings will be added to the cache.
     * 
     * @param m the map to create the cache from
     */
    public FastCache(Map<Key, Value> m) {
        this(100, DEFAULT_LOAD_FACTOR);

        m.forEach(this::put);
    }

    /**
     * Constructs a new FastCache with the specified size and number of buckets.
     *
     * @param size       the maximum number of entries the cache can hold
     * @param numBuckets the number of buckets to use for storing the cache entries
     * @param ignoreMe   ignore this parameter, it is only used to differentiate this constructor from the others
     */
    FastCache(int size, int numBuckets, boolean ignoreMe) {
        this.maxEntries = size;
        // cut-and-paste from below, must be in-constructor for final
        @SuppressWarnings({ "unchecked" })
        SoftReference<Record<Key, Value>>[] bucketsTemp = (SoftReference<Record<Key, Value>>[]) new SoftReference[numBuckets];
        mBuckets = bucketsTemp;
    }

    /**
     * Construct a fast cache of the specified size (measured in
     * number of hash buckets) and load factor. The size times the
     * load factor must be greater than or equal to 1. When the
     * (approximate) number of entries exceeds the load factor times
     * the size, the cache is pruned.
     *
     * @param size       Number of buckets in the cache.
     * @param loadFactor Load factor of the cache.
     * @throws IllegalArgumentException If the size is less than one or the load
     *                                  factor is not a positive finite value.
     */
    public FastCache(int size, double loadFactor) {
        if (size < 1) {
            String msg = "Cache size must be at least 1."
                    + " Found cache size=" + size;
            throw new IllegalArgumentException(msg);
        }
        if (loadFactor < 0.0 || Double.isNaN(loadFactor) || Double.isInfinite(loadFactor)) {
            String msg = "Load factor must be finite and positive."
                    + " found loadFactor=" + loadFactor;
            throw new IllegalArgumentException(msg);
        }
        maxEntries = (int) (loadFactor * (double) size);
        if (maxEntries < 1) {
            String msg = "size * loadFactor must be > 0."
                    + " Found size=" + size
                    + " loadFactor=" + loadFactor;
            throw new IllegalArgumentException(msg);
        }
        // required for array
        @SuppressWarnings({ "unchecked" })
        SoftReference<Record<Key, Value>>[] bucketsTemp = (SoftReference<Record<Key, Value>>[]) new SoftReference[size];
        mBuckets = bucketsTemp;
    }

    Record<Key, Value> getFirstRecord(int bucketId) {
        SoftReference<Record<Key, Value>> ref = mBuckets[bucketId];
        return ref == null ? null : ref.get();
    }

    void setFirstRecord(int bucketId, Record<Key, Value> record) {
        SoftReference<Record<Key, Value>> ref = new SoftReference<Record<Key, Value>>(record);
        mBuckets[bucketId] = ref;
    }

    /**
     * Returns the value of the specified key or <code>null</code> if
     * there is no value attached. Note that the argument is not
     * the generic <code>&lt;K&gt;</code> key type, but <code>Object</code>
     * to match the requirements of <code>java.util.Map</code>.
     *
     * <p>
     * <i>Warning:</i> Because of the approximate cache-like
     * behavior of this class, key-value pairs previously added
     * by the {@linKey #put(Object,Object)} method may disappear.
     *
     * @param key Mapping key.
     * @return The value for the specified key.
     */
    @Override
    public Value get(Object key) {
        int bucketId = bucketId(key);
        for (Record<Key, Value> record = getFirstRecord(bucketId); record != null; record = record.mNextRecord) {

            if (record.mKey.equals(key)) {
                ++record.mCount;
                return record.mValue;
            }
        }
        return null;
    }

    int bucketId(Object key) {
        return java.lang.Math.abs(key.hashCode() % mBuckets.length);
    }

    /**
     * Sets the value of the specified key to the specified value.
     * If there is already a value for the specified key, the count
     * is incremented, but the value is not replaced.
     *
     * <p>
     * <i>Warning:</i> Because of the approximate cache-like
     * behavior of this class, setting the value of a key with this
     * method is not guaranteed to replace older values or remain in
     * the mapping until the next call to {@linKey #get(Object)}.
     *
     * @param key   Mapping key.
     * @param value New value for the specified key.
     * @return <code>null</code>, even if there is an existing
     *         value for the specified key.
     */
    @Override
    public Value put(Key key, Value value) {
        int bucketId = bucketId(key);
        Record<Key, Value> firstRecord = getFirstRecord(bucketId);
        for (Record<Key, Value> record = firstRecord; record != null; record = record.mNextRecord) {
            if (record.mKey.equals(key)) {
                ++record.mCount; // increment instead
                return null; // already there
            }
        }
        prune();
        firstRecord = getFirstRecord(bucketId); // may've been pruned
        Record<Key, Value> record = new Record<Key, Value>(key, value, firstRecord);
        setFirstRecord(bucketId, record);
        ++mNumEntries;
        return null;
    }

    /**
     * Removes all entries from this cache.
     */
    public void clear() {
        synchronized (this) {
            for (SoftReference<Record<Key, Value>> ref : mBuckets)
                if (ref != null)
                    ref.clear();
        }
    }

    /**
     * Prunes this cache by (approximately) dividing the counts of
     * entries by two and removing the ones with zero counts. This
     * operation is approximate in the sense that the optimistic
     * update strategy applied is not guaranteed to actually do any
     * pruning or decrements of counts.
     */
    public void prune() {
        // only synchronized versus other prunes;
        // other puts, etc. may interfere, which is OK
        synchronized (this) {
            if (mNumEntries < maxEntries)
                return;
            int count = 0;
            for (int i = 0; i < mBuckets.length; ++i) {
                Record<Key, Value> record = getFirstRecord(i);
                Record<Key, Value> prunedRecord = prune(record);
                setFirstRecord(i, prunedRecord);
                for (Record<Key, Value> r = prunedRecord; r != null; r = r.mNextRecord)
                    ++count;
            }
            mNumEntries = count;
        }
    }

    final Record<Key, Value> prune(Record<Key, Value> inRecord) {
        Record<Key, Value> record = inRecord;
        while (record != null && (record.mCount = (record.mCount >>> 1)) == 0)
            record = record.mNextRecord;
        if (record == null)
            return null;
        record.mNextRecord = prune(record.mNextRecord);
        return record;
    }

    /**
     * Returns a snapshot of the entries in this map.
     * This set is not backed by this cache, so that changes
     * to the cache do not affect the cache and vice-versa.
     *
     * @return The set of entries in this cache.
     */
    @Override
    public Set<Map.Entry<Key, Value>> entrySet() {
        HashSet<Map.Entry<Key, Value>> entrySet = new HashSet<Map.Entry<Key, Value>>();
        for (int i = 0; i < mBuckets.length; ++i)
            for (Record<Key, Value> record = getFirstRecord(i); record != null; record = record.mNextRecord)
                entrySet.add(record);
        return entrySet;
    }

    /**
     * Creates a FastCache from a HashMap.
     * This is useful for creating a FastCache from a HashMap that was created by a
     * previous call.
     * 
     * @param map The map to copy.
     * @return A FastCache with the same entries as the map.
     */
    public static <Key, Value> FastCache<Key, Value> fromHashMap(HashMap<Key, Value> map) {
        FastCache<Key, Value> cache = new FastCache<Key, Value>(map.size());
        for (Map.Entry<Key, Value> entry : map.entrySet()) {
            cache.put(entry.getKey(), entry.getValue());
        }
        return cache;
    }

    /**
     * Returns a HashMap with the same entries as this FastCache.
     * 
     * @return a new hashmap
     */
    public HashMap<Key, Value> toHashMap() {
        HashMap<Key, Value> map = new HashMap<Key, Value>();
        for (Map.Entry<Key, Value> entry : this.entrySet()) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }

    static final class Record<Key, Value> implements Map.Entry<Key, Value> {
        final Key mKey;
        final Value mValue;
        volatile Record<Key, Value> mNextRecord;
        volatile int mCount;

        Record(Key key, Value value) {
            this(key, value, null);
        }

        Record(Key key, Value value, Record<Key, Value> nextRecord) {
            this(key, value, nextRecord, 1);
        }

        Record(Key key, Value value, Record<Key, Value> nextRecord, int count) {
            mKey = key;
            mValue = value;
            mNextRecord = nextRecord;
            mCount = count;
        }

        public Key getKey() {
            return mKey;
        }

        public Value getValue() {
            return mValue;
        }

        @Override
        public int hashCode() {
            return (mKey == null ? 0 : mKey.hashCode()) ^
                    (mValue == null ? 0 : mValue.hashCode());
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry))
                return false;
            Map.Entry<?, ?> e2 = (Map.Entry<?, ?>) o;
            return (mKey == null
                    ? e2.getKey() == null
                    : mKey.equals(e2.getKey()))
                    && (mValue == null
                            ? e2.getValue() == null
                            : mValue.equals(e2.getValue()));
        }

        public Value setValue(Value value) {
            String msg = "Cache records may not be set.";
            throw new UnsupportedOperationException(msg);
        }
    }
}