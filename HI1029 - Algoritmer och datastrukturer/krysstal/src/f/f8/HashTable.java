package f.f8;

import java.util.Arrays;

public class HashTable<K, V> {
    int count;
    Entry<K, V>[] table;

    public HashTable() {
        count = 0;
        table = new Entry[10];
    }

    private int findIndex(K key) {
        int index = key.hashCode() % table.length;
        if (index < 0)
            index += table.length;

        if (table[index] == null)
            return -1;

        if (table[index].key == null)
            return -1;

        while (!table[index].key.equals(key)) {
            index++;
            index %= table.length;

            if (table[index] == null)
                return -1;

            if (index == table.length - 1)
                return -1;
        }
        return index;
    }

    public V put(K key, V value) {
        int index = key.hashCode() % table.length;
        if (index < 0)
            index += table.length;

        if (get(key) != null) {
            table[index] = new Entry<>(key, value);
            return value;
        }

        //If key not already present
        count++;
        if (count >= table.length * 0.75)
            resize();

        while (isOccupied(index)) {
            index++;
            index %= table.length;
        }

        table[index] = new Entry(key, value);
        return value;
    }

    private boolean isOccupied(int index) {
        if (table[index] == null)
            return false;
        if (table[index].key == null)
            return false;
        return true;
    }

    public V get(K key) {
        int index = findIndex(key);
        if (index < 0)
            return null;
        if (table[index] != null)
            return table[findIndex(key)].value;
        return null;
    }

    public V remove(K key) {
        int index = findIndex(key);

        if (index < 0)
            return null;

        if (table[index] != null) {
            V val = table[index].value;
            table[index].key = null;
            table[index].value = null;
            return val;
        }

        return null;
    }

    private void resize() {
        var oldTable = table;
        count = 0;
        table = new Entry[table.length * 2];
        for (var e : oldTable) {
            if (e != null && e.key != null)
                put(e.key, e.value);
        }
    }

    @Override
    public String toString() {
        return "HashTable{" +
                "count=" + count +
                ", table=" + Arrays.toString(table) +
                '}';
    }

    private static class Entry<K, V> {
        public K key;
        public V value;

        public Entry(K k, V v) {
            key = k;
            value = v;
        }

        @Override
        public String toString() {
            return "[" + key + "," + value + "]";
        }
    }
}
