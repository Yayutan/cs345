package q4hashing;

import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * LinProbHashMap
 * 
 * A reduced form of the linear probing hash map
 * 
 * CSCI 345
 * Test 3 Practice Problem 4.
 */
public class LinProbHashMap<K, V> implements Map<K, V> {

    
    /**
     * Array containing keys.
     */
    private K[] keys;

    /**
     * Parallel array containing values. Invariant:
     * keys.length == values.length;
     */
    private V[] values;
    
    
    /**
     * Constructor allowing the original table size
     * to be specified.
     * @param initKeyCap The initial capacity of the number of keys.
     */
    @SuppressWarnings("unchecked")
    public LinProbHashMap(int initKeyCap) {
        initKeyCap = PrimeSource.nextOrEqPrime(initKeyCap);
        keys = (K[]) new Object[initKeyCap];
        values = (V[]) new Object[initKeyCap];
    }

    /**
     * Compute a hash appropriate for the number of keys.
     * This follows Sedgewick's advice on avoiding
     * negative keys from Java's hashCode() (see pg 461
     * and 478-479).
     * @param key The key whose ideal index to compute
     * @return The index where they key ideally would be.
     */
    private int hash(K key) {
        return (key.hashCode() & 0x7fffffff) % keys.length;
    }
    
    /**
     * Compute the index where the key is or could be.
     * @param key The key whose index to find.
     * @return The index containing this key, if it exists, or
     * one where the key could go, if it doesn't yet exist.
     */
    private int findIndex(K key) {
        int i = hash(key),
            j = 0;  // counts iterations, used for assertion
        while (keys[i] != null && ! key.equals(keys[i])) {
            i = (i + 1) % keys.length;
            j++;
            // we should find an empty spot before lapping the array
            assert j < keys.length;
        }
        return i;
    }

    /**
     * Add an association to the map.
     * @param key The key to this association
     * @param val The value to which this key is associated
     */
    public void put(K key, V val) {
        int index = findIndex(key);
        // the real reason we bother to check is 
        // to know whether or not to increment pairs.
        if (keys[index] == null) {
            keys[index] = key;
            values[index] = val;
        }
        else
            values[index] = val;
    }

    /**
     * Get the value for a key.
     * @param key The key whose value we're retrieving.
     * @return The value associated with this key, null if none exists
     */
    public V get(K key) {
        int index = findIndex(key);
        if (keys[index] == null)
            return null;
        else
            return values[index];
    }

    /**
     * Test if this map contains an association for this key.
     * @param key The key to test.
     * @return true if there is an association for this key, false otherwise
     */
    public boolean containsKey(K key) {
        return keys[findIndex(key)] != null;
    }
    
    
    /**
     * Compute the average size of all clusters.
     * @return The average cluster size.
     */
    public double aveClusterSize() {
        return -1.0;
    }
}
