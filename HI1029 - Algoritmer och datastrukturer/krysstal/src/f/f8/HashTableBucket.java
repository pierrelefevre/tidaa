package f.f8;

public class HashTableBucket<K, V> {
    private SingleLinkedList<Entry<K, V>>[] table;
    private int count;

    @SuppressWarnings("unchecked")
    public HashTableBucket() {
        table = new SingleLinkedList[10];
        count = 0;
    }

    private void resize() {
        var oldTable = table;
        count = 0;
        table = new SingleLinkedList[table.length * 2];
        for (var l : oldTable) {
            if (l != null)
                for (var e : l) {
                    put(e.key, e.value);
                }
        }
    }

    public V put(K key, V value) {
        if (++count >= table.length * 0.75)
            resize();
        int index = key.hashCode() % table.length;
        if (index < 0) index += table.length;
        if (table[index] == null) {
            table[index] = new SingleLinkedList<Entry<K, V>>();
            table[index].add(new Entry<K, V>(key, value));
        } else {
            V oldValue;
            for (Entry<K, V> e : table[index]) {
                if (e.key.equals(key)) {
                    oldValue = e.value;
                    e.value = value;
                    return oldValue;
                }
            }
            table[index].add(new Entry<K, V>(key, value));
        }
        return null;
    }

    public V get(K key) {
        int index = key.hashCode() % table.length;
        if (index < 0) index += table.length;
        if (table[index] == null) return null;
        for (Entry<K, V> e : table[index]) {
            if (e.key.equals(key)) return e.value;
        }
        return null;
    }

    public V remove(K key) {
        int index = key.hashCode() % table.length;
        if (index < 0) index += table.length;
        if (table[index] == null) return null;

        V oldVal = null;
        var iterator = table[index].iterator();
        while (iterator.hasNext()) {
            Entry<K, V> test = iterator.next();
            if (test.key.equals(key)) {
                oldVal = test.value;
                iterator.remove();
            }
        }
        if (!table[index].iterator().hasNext())
            table[index] = null;
        return oldVal;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (var l : table) {
            if (l != null)
                for (var e : l) {
                    sb.append(e.key.toString());
                    sb.append(":");
                    sb.append(e.value.toString());
                    sb.append("; ");
                }
            else
                sb.append("~ ");
            sb.append("\n");
        }
        return sb.toString();
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