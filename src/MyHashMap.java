public class MyHashMap<K, V> {

    private static final int MIN_CAPACITY = 16;

    private Entry<K, V>[] table;
    private int size;
    private int capacity;
    private final float loadFactor;
    private final float shrinkFactor;

    public MyHashMap(int initialCapacity, float loadFactor, float shrinkFactor) {
        this.capacity = Math.max(initialCapacity, MIN_CAPACITY);
        this.loadFactor = loadFactor;
        this.shrinkFactor = shrinkFactor;
        this.table =  new Entry[this.capacity];
        this.size = 0;
    }

    private int hash(K key) {

        return (key.hashCode() & 0x7fffffff) % this.capacity;
    }

    public V get(K key) {
        int index = hash(key);
        Entry<K, V> entry = table[index];
        while (entry != null) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
            entry = entry.next;
        }
        return null;
    }

    public void put(K key, V value) {
        int index = hash(key);
        Entry<K, V> entry = table[index];

        while (entry != null) {
            if (entry.key.equals(key)) {
                entry.value = value;
                return;
            }
            entry = entry.next;
        }

        Entry<K, V> newEntry = new Entry<>(key, value, table[index]);
        table[index] = newEntry;
        size++;

        if ((float) size / capacity > shrinkFactor) {
            resize(capacity * 2);
        }
    }

    public boolean remove(K key) {
        int index = hash(key);
        Entry<K, V> entry = table[index];
        Entry<K, V> prev = null;

        while (entry != null) {
            if (entry.key.equals(key)) {
                if (prev == null) {
                    table[index] = entry.next;
                } else {
                    prev.next = entry.next;
                }

                size--;

                if (capacity > MIN_CAPACITY && (float) size / capacity < shrinkFactor) {
                    resize(capacity / 2);
                }

                return true;
            }
            prev = entry;
            entry = entry.next;
        }

        return false;
    }

    private void resize(int i) {
        i = Math.max(i, MIN_CAPACITY);
        Entry<K, V>[] oldTable = table;
        table = new Entry[i];
        int oldCapacity = capacity;
        capacity = i;
        size = 0;

        for (int j = 0; j < oldCapacity; j++) {
            Entry<K, V> entry = oldTable[j];
            while (entry != null) {
                put(entry.key, entry.value);
                entry = entry.next;
            }
        }
    }

    public int size() {
        return size;
    }

    private static class Entry<K, V> {
        private final K key;
        private V value;
        Entry<K, V> next;

        public Entry(K key, V value, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
}


