package org.example;

import java.util.Objects;

public class HashMap<K, V> {

    private static class Entry<K, V> {
        final int hash;
        final K key;
        V value;
        Entry<K, V> next;

        Entry(int hash, K key, V value, Entry<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    private Entry<K, V>[] table;
    private int size;
    private int capacity;
    private final float loadFactor;
    private int threshold;

    public HashMap() {
        this.capacity = 16;
        this.loadFactor = 0.75f;
        this.threshold = (int) (capacity * loadFactor);
        this.table = (Entry<K, V>[]) new Entry[capacity];
    }

    private int hash(Object key) {
        return key == null ? 0 : key.hashCode() ^ (key.hashCode() >>> 16);
    }

    private int indexFor(int hash) {
        return hash & (capacity - 1);
    }

    public V put(K key, V value) {
        int hash = hash(key);
        int index = indexFor(hash);

        for (Entry<K, V> e = table[index]; e != null; e = e.next) {
            if (e.hash == hash && Objects.equals(e.key, key)) {
                V oldValue = e.value;
                e.value = value;
                return oldValue;
            }
        }

        Entry<K, V> newEntry = new Entry<>(hash, key, value, table[index]);
        table[index] = newEntry;
        size++;

        if (size > threshold) {
            resize();
        }

        return null;
    }

    public V get(Object key) {
        int hash = hash(key);
        int index = indexFor(hash);

        for (Entry<K, V> e = table[index]; e != null; e = e.next) {
            if (e.hash == hash && Objects.equals(e.key, key)) {
                return e.value;
            }
        }

        return null;
    }

    public boolean containsKey(Object key) {
        return get(key) != null;
    }

    public V remove(Object key) {
        int hash = hash(key);
        int index = indexFor(hash);
        Entry<K, V> prev = null;
        Entry<K, V> e = table[index];

        while (e != null) {
            if (e.hash == hash && Objects.equals(e.key, key)) {
                if (prev == null) {
                    table[index] = e.next;
                } else {
                    prev.next = e.next;
                }
                size--;
                return e.value;
            }
            prev = e;
            e = e.next;
        }

        return null;
    }

    public int size() {
        return size;
    }

    private void resize() {
        int newCapacity = capacity * 2;
        Entry<K, V>[] newTable = (Entry<K, V>[]) new Entry[newCapacity];

        for (Entry<K, V> entry : table) {
            while (entry != null) {
                Entry<K, V> next = entry.next;
                int index = entry.hash & (newCapacity - 1);
                entry.next = newTable[index];
                newTable[index] = entry;
                entry = next;
            }
        }

        table = newTable;
        capacity = newCapacity;
        threshold = (int) (capacity * loadFactor);
    }
}
