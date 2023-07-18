package bstmap;

import java.util.Iterator;
import java.util.Set;


public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private BSTNode root;

    @Override
    public void clear() {
        root = null;
    }


    public boolean containsKey(K key) {
        return containsKey(root, key);
    }

    private boolean containsKey(BSTNode bst, K k) {
        if (k == null || bst == null) {
            return false;
        }
        if (k.compareTo(bst.key) < 0) {
            return containsKey(bst.left, k);
        } else if (k.compareTo(bst.key) > 0) {
            return containsKey(bst.right, k);
        }
        return true;

    }


    public V get(K key) {
        return get(root, key);
    }

    private V get(BSTNode bst, K k) {
        if (k == null || bst == null) {
            return null;
        }
        if (k.compareTo(bst.key) < 0) {
            return get(bst.left, k);
        } else if (k.compareTo(bst.key) > 0) {
            return get(bst.right, k);
        }
        return bst.val;

    }

    @Override
    public int size() {
        return size(root);
    }

    private int size(BSTNode bst) {
        if (bst == null) return 0;
        else return bst.size;
    }


    public void put(K key, V val) {
        if (key == null) throw new IllegalArgumentException("calls put() with a null key");
        root = put(root, key, val);
    }

    private BSTNode put(BSTNode bst, K key, V val) {
        if (bst == null) return new BSTNode(key, val, 1);
        int cmp = key.compareTo(bst.key);
        if (cmp < 0) bst.left = put(bst.left, key, val);
        else if (cmp > 0) bst.right = put(bst.right, key, val);
        else bst.val = val;
        bst.size = 1 + size(bst.left) + size(bst.right);
        return bst;
    }


    private void printInOrder(BSTNode bst) {
        if (bst == null) return;
        System.out.println(String.format("(%s, %s) ", bst.key, bst.val));
        printInOrder(bst.left);
        printInOrder(bst.right);

    }

    public void printInOrder() {
        printInOrder(root);
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

    private class BSTNode {
        K key;
        V val;
        BSTNode left;
        BSTNode right;
        int size;

        BSTNode(K k, V v, int size) {
            this.key = k;
            this.val = v;
            this.size = size;
        }

        /**
         * Returns the Entry in this linked list of key-value pairs whose key
         * is equal to KEY, or null if no such Entry exists.
         */


        public BSTNode put(BSTNode bst, K k, V v) {
            if (key == null) {
                return new BSTNode(k, v, 1);
            }
            if (k.equals(key)) {
                val = v;
            }
            if (k.compareTo(key) < 0) {
                put(left, k, v);
            } else if (k.compareTo(key) > 0) {
                right = put(right, k, v);
            }
            return bst;
        }
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }


}
