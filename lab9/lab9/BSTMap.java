package lab9;

import java.util.Iterator;
import java.util.Set;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Will Zhao
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /* One Node BTSMap Constructor(not necessary actually) */
    public BSTMap(K key, V value) {
        root = new Node(key, value);
        size = 1;
    }


    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (p == null) {
            return null;
        }

        if (p.key.compareTo(key) == 0) {
            return p.value;
        } else if (p.key.compareTo(key) > 0) {
            return getHelper(key, p.left);
        } else {
            return getHelper(key, p.right);
        }
    }


    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(key, root);
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        Node node = new Node(key, value);

        if (p == null) {
            return node;
        }

        if (p.key.compareTo(key) == 0) {
            p.value = value;
        } else if (p.key.compareTo(key) > 0) {
            p.left = putHelper(key, value, p.left);
        } else if (p.key.compareTo(key) < 0) {
            p.right = putHelper(key, value, p.right);
        }
        return p;
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        if (root == null) {
            root = putHelper(key, value, root);
            size += 1;
        }
        if (get(key) != null) {
            putHelper(key, value, root);
        } else {
            putHelper(key, value, root);
            size += 1;
        }

    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    private Node findSuccessor(Node p) {
        Node successor = p.left;
        while (successor.right != null) {
            successor = successor.right;
        }
        return successor;
    }

    private Node removeHelper(K key, Node p) {
        if (p == null) {
            //implicitly contain case 1: the deleted node is leave
            return null;
        }

        if (p.key.compareTo(key) == 0) {
            //case 2: the deleted node has one child
            if (p.right == null) {
                return p.left;
            }
            if (p.left == null) {
                return p.right;
            }

            //case 3: the deleted node has two child
            Node temp = p;
            Node successor = findSuccessor(p);
            p = successor;
            p.right = temp.right;
            p.left = removeHelper(successor.key, temp);

        } else if (p.key.compareTo(key) > 0) {
            p.right = removeHelper(key, p.left);
        } else {
            p.left = removeHelper(key, p.right);
        }
        return p;
    }


    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key) {
        if (get(key) == null) {
            return null;
        }

        V returnVal = get(key);
        removeHelper(key, root);
        size -= 1;
        return returnVal;
    }

    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        if (get(key) == null || (get(key) != value)) {
            return null;
        }
        removeHelper(key, root);
        size -= 1;
        return value;
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

    public static void main(String[] args) {
        BSTMap<String, Integer> bstmap = new BSTMap<>();
        bstmap.put("hello", 5);
        bstmap.put("cat", 10);
        bstmap.put("fish", 22);
        bstmap.put("zebra", 90);
    }
}
