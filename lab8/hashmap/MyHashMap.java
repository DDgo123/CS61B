package hashmap;

import java.util.*;

/**
 * A hash table-backed Map implementation. Provides amortized constant time
 * access to elements via get(), remove(), and put() in the best case.
 * <p>
 * Assumes null keys will never be inserted, and does not resize down upon remove().
 *
 * @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {


    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    private double loadFactor;
    private int size;
    private int m;
    // You should probably define some more!

    /**
     * Constructors
     */
    public MyHashMap() {
        this(16, 0.75);
    }

    public MyHashMap(int initialSize) {
        this(initialSize, 0.75);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad     maximum load factor
     */

    //初始化
    public MyHashMap(int initialSize, double maxLoad) {
        this.loadFactor = maxLoad;
        this.m = initialSize;
        this.size = 0;
        this.buckets = createTable(initialSize);
    }


    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     * <p>
     * The only requirements of a hash table bucket are that we can:
     * 1. Insert items (`add` method)
     * 2. Remove items (`remove` method)
     * 3. Iterate through items (`iterator` method)
     * <p>
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     * <p>
     * Override this method to use different data structures as
     * the underlying bucket type
     * <p>
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     * <p>
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        Collection<Node>[] table = new Collection[tableSize];
        for (int i = 0; i < tableSize; i++) {
            table[i] = createBucket();
        }
        return table;
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!
    @Override
    public void clear() {
        //清空buckets，size为0
        buckets = null;
        size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        V v = get(key);
        if (v != null) return true;
        return false;
    }

    @Override
    //get value，如果不存在返回null
    public V get(K key) {
        int index = hash(key);
        Node node = find(index, key);
        if (node == null) return null;
        return node.value;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void put(K key, V value) {
        if (buckets == null) {
            buckets = createTable(m); //如果不存在，初始化buckets
        }
        int index = hash(key);
        Node node = find(index, key);
        if (node == null) {
            buckets[index].add(createNode(key, value)); //不存在的话就添加node
            size += 1;
            if ((double) size / m > loadFactor) resize(); //超出负载resize
            return;
        }
        node.value = value;//如果node存在就更新value
    }

    private void resize() {
        //创建一个两倍大小的newmap，把当前的buckets只想newmap的buckets，m同理
        MyHashMap<K, V> newMap = new MyHashMap(m * 2, loadFactor);
        for (Collection<Node> bucket : buckets) {
            for (Node node : bucket) {
                newMap.put(node.key, node.value);
            }
        }
        this.buckets = newMap.buckets;
        this.m = newMap.m;

    }


    private Node find(int index, K key) {
        if (buckets == null) return null;
        for (Node n : buckets[index]) {
            if (n.key.equals(key)) {
                return n;
            }
        }
        return null;
    }

    @Override
    public Set<K> keySet() {
        Set<K> keySet = new HashSet<>();

        for (Collection<Node> bucket : buckets) {
            for (Node node : bucket) {
                keySet.add(node.key);
            }
        }

        return keySet;
    }

    @Override
    public V remove(K key) {
        int index = hash(key);
        Node node = find(index, key);
        if (node != null) {
            V v = node.value;
            buckets[index].remove(node);
            this.size -= 1;
            return v;
        }
        return null;
    }

    @Override
    public V remove(K key, V value) {
        int index = hash(key);
        Node node = find(index, key);
        if (node != null && value.equals(node.value)) {
            V v = node.value;
            buckets[index].remove(index);
            this.size -= 1;
            return v;
        }
        return null;
    }

    private int hash(K key) {
        return (key.hashCode() & 0x7fffffff) % m;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }
}
