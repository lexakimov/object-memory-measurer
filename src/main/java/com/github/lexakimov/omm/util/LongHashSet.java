package com.github.lexakimov.omm.util;

/**
 * COPIED FROM HPPC
 */
public class LongHashSet {

    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    private static final float MAX_LOAD_FACTOR = 99 / 100.0f;
    private static final float MIN_LOAD_FACTOR = 1 / 100.0f;
    private static final int DEFAULT_EXPECTED_ELEMENTS = 4;
    private static final int MAX_HASH_ARRAY_LENGTH = 0x80000000 >>> 1;
    private static final long PHI_C64 = 0x9e3779b97f4a7c15L;

    private long[] keys;
    private int assigned;
    private int mask;
    private int resizeAt;
    private boolean hasEmptyKey;
    private double loadFactor;

    public LongHashSet() {
        this(DEFAULT_EXPECTED_ELEMENTS, DEFAULT_LOAD_FACTOR);
    }

    private LongHashSet(int expectedElements, double loadFactor) {
        this.loadFactor = verifyLoadFactor(loadFactor);
        ensureCapacity(expectedElements);
    }

    private void ensureCapacity(int expectedElements) {
        if (expectedElements > resizeAt || keys == null) {
            final long[] prevKeys = this.keys;
            allocateBuffers(minBufferSize(expectedElements, loadFactor));
            if (prevKeys != null && !isEmpty()) {
                rehash(prevKeys);
            }
        }
    }

    public boolean add(long key) {
        if (((key) == 0)) {
            assert ((keys[mask + 1]) == 0);
            boolean added = !hasEmptyKey;
            hasEmptyKey = true;
            return added;
        } else {
            final long[] keys = this.keys;
            final int mask = this.mask;
            int slot = hashKey(key) & mask;

            long existing;
            while (!((existing = keys[slot]) == 0)) {
                if (((key) == (existing))) {
                    return false;
                }
                slot = (slot + 1) & mask;
            }

            if (assigned == resizeAt) {
                allocateThenInsertThenRehash(slot, key);
            } else {
                keys[slot] = key;
            }

            assigned++;
            return true;
        }
    }

    public boolean contains(long key) {
        if (((key) == 0)) {
            return hasEmptyKey;
        } else {
            final long[] keys = this.keys;
            final int mask = this.mask;
            int slot = hashKey(key) & mask;
            long existing;
            while (!((existing = keys[slot]) == 0)) {
                if (((key) == (existing))) {
                    return true;
                }
                slot = (slot + 1) & mask;
            }
            return false;
        }
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return assigned + (hasEmptyKey ? 1 : 0);
    }

    private static int minBufferSize(int elements, double loadFactor) {
        if (elements < 0) {
            throw new IllegalArgumentException("Number of elements must be >= 0: " + elements);
        }

        long length = (long) Math.ceil(elements / loadFactor);
        if (length == elements) {
            length++;
        }
        length = Math.max(4, nextHighestPowerOfTwo(length));

        if (length > 0x80000000 >>> 1) {
            throw new AssertionError(String.format(
                    "Maximum array size exceeded for this load factor (elements: %d, load factor: %f)",
                    elements, loadFactor));
        }

        return (int) length;
    }

    private static long nextHighestPowerOfTwo(long v) {
        v--;
        v |= v >> 1;
        v |= v >> 2;
        v |= v >> 4;
        v |= v >> 8;
        v |= v >> 16;
        v |= v >> 32;
        v++;
        return v;
    }

    private static int nextHighestPowerOfTwo(int v) {
        v--;
        v |= v >> 1;
        v |= v >> 2;
        v |= v >> 4;
        v |= v >> 8;
        v |= v >> 16;
        v++;
        return v;
    }

    private int hashKey(long key) {
        assert !((key) == 0); // Handled as a special case (empty slot marker).
        return mixPhi(key);
    }

    private static int mixPhi(long k) {
        final long h = k * PHI_C64;
        return (int) (h ^ (h >>> 32));
    }

    private double verifyLoadFactor(double loadFactor) {
        checkLoadFactor(loadFactor, MIN_LOAD_FACTOR, MAX_LOAD_FACTOR);
        return loadFactor;
    }

    private static void checkLoadFactor(
            double loadFactor, double minAllowedInclusive, double maxAllowedInclusive) {
        if (loadFactor < minAllowedInclusive || loadFactor > maxAllowedInclusive) {
            throw new AssertionError(String.format(
                    "The load factor should be in range [%.2f, %.2f]: %f",
                    minAllowedInclusive, maxAllowedInclusive, loadFactor));
        }
    }

    private void rehash(long[] fromKeys) {
        assert checkPowerOfTwo(fromKeys.length - 1);

        // Rehash all stored keys into the new buffers.
        final long[] keys = this.keys;
        final int mask = this.mask;
        long existing;
        for (int i = fromKeys.length - 1; --i >= 0; ) {
            if (!((existing = fromKeys[i]) == 0)) {
                int slot = hashKey(existing) & mask;
                while (!((keys[slot]) == 0)) {
                    slot = (slot + 1) & mask;
                }
                keys[slot] = existing;
            }
        }
    }

    private static boolean checkPowerOfTwo(int arraySize) {
        // These are internals, we can just assert without retrying.
        assert arraySize > 1;
        assert nextHighestPowerOfTwo(arraySize) == arraySize;
        return true;
    }

    private void allocateBuffers(int arraySize) {
        assert Integer.bitCount(arraySize) == 1;

        // Ensure no change is done if we hit an OOM.
        long[] prevKeys = this.keys;
        try {
            int emptyElementSlot = 1;
            this.keys = (new long[arraySize + emptyElementSlot]);
        } catch (OutOfMemoryError e) {
            this.keys = prevKeys;
            throw new AssertionError(String.format(
                    "Not enough memory to allocate buffers for rehashing: %,d -> %,d",
                    e,
                    this.keys == null ? 0 : size(),
                    arraySize));
        }

        this.resizeAt = expandAtCount(arraySize, loadFactor);
        this.mask = arraySize - 1;
    }

    private static int expandAtCount(int arraySize, double loadFactor) {
        assert checkPowerOfTwo(arraySize);
        // Take care of hash container invariant (there has to be at least one empty slot to ensure
        // the lookup loop finds either the element or an empty slot).
        return Math.min(arraySize - 1, (int) Math.ceil(arraySize * loadFactor));
    }

    private void allocateThenInsertThenRehash(int slot, long pendingKey) {
        assert assigned == resizeAt
               && ((keys[slot]) == 0)
               && !((pendingKey) == 0);

        // Try to allocate new buffers first. If we OOM, we leave in a consistent state.
        final long[] prevKeys = this.keys;
        allocateBuffers(nextBufferSize(mask + 1, size(), loadFactor));
        assert this.keys.length > prevKeys.length;

        // We have succeeded at allocating new data so insert the pending key/value at
        // the free slot in the old arrays before rehashing.
        prevKeys[slot] = pendingKey;

        // Rehash old keys, including the pending key.
        rehash(prevKeys);
    }

    private static int nextBufferSize(int arraySize, int elements, double loadFactor) {
        assert checkPowerOfTwo(arraySize);
        if (arraySize == MAX_HASH_ARRAY_LENGTH) {
            throw new AssertionError(String.format(
                    "Maximum array size exceeded for this load factor (elements: %d, load factor: %f)",
                    elements, loadFactor));
        }

        return (int) arraySize << 1;
    }

}
