/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uppgift3;

import javax.print.attribute.standard.Finishings;
import java.util.Iterator;

/**
 * @param <K>
 * @param <V>
 * @author bfelt
 */
public class HashTableBucket<K, V> {


    private static class Entry<K, V> {

        public K key;
        public V value;

        public Entry(K k, V v) {
            key = k;
            value = v;
        }
    }

    private SingleLinkedList<Entry<K, V>>[] table;

    @SuppressWarnings("unchecked")
    public HashTableBucket(int initialSize) {
        table = new SingleLinkedList[initialSize];
    }

    public V get(K key) {
        if (table[key.hashCode() % table.length] == null)
            return null;

        for (Entry<K, V> e : table[key.hashCode() % table.length]) {
            if (e.key.equals(key))
                return e.value;
        }
        return null;
    }

    public V put(K key, V value) {
        if (table[key.hashCode() % table.length] == null)
            table[key.hashCode() % table.length] = new SingleLinkedList<>();

        V oldValue = null;
        for (Entry<K, V> e : table[key.hashCode() % table.length]) {
            if (e.key.equals(key)) {
                oldValue = e.value;
                e.value = value;
                return oldValue;
            }
        }
        table[key.hashCode() % table.length].add(new Entry<>(key, value));
        return oldValue;
    }

}
