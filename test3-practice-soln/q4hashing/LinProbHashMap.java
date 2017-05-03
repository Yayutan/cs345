package q4hashing;

import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * LinProbHashMap
 * 
 * Quick hack-up of a linear-probing approach to
 * a hash map.
 *
 * @author Thomas VanDrunen
 * CSCI 345, Wheaton College
 * July 30, 2014
 * @param <K> The key-type of the map
 * @param <V> The value-type of the map
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

    public LinProbHashMap() {
        this(19);
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
    
    public double aveClusterSize() {
        int clusters = 0, count = 0;
        boolean inCluster = false;
        for (int i = 0; i < keys.length; i++) {
            if (keys[i] != null) {
                count++;
                if (!inCluster) {
                    inCluster = true;
                    clusters++;
                }
            }
            else
                inCluster = false;
        }
        if (keys[0] != null && keys[keys.length-1] != null)
            clusters--;
        return ((double) count) / clusters;
    }
    
    public String toString() {
        String toReturn = "[";
        for (K key : keys)
            if (key == null) toReturn += ".";
            else toReturn += "#";
        return toReturn + "]";
    }
    
    public static void main(String[] args) {
        LinProbHashMap<String,String> map = new LinProbHashMap<String,String>(51);
        String[] keys =  { "CONSTANCE", "HELEN", "JUSTIN", "JON", "JOHN", "CONSTANTIUS",
                "HELENA", "HELLA", "JONATHAN", "CONSTANTINE", "JUSTINIAN",
                "CLEMENS", "HEROD", "ANN", "JOHANA", "JUSTINIANUS",
                "CONSTANTINUS", "ELLEN", "ELAINE", "ELLIE", "ELLA",
                "HERODIAS", "HERODIAN", "JOHNA", "ANNALISE", "ANNETTE",
                "ANNIKA", "CLEMENT", "CAESAR", "AUGUSTUS", "ANNE", "ANNMARIE",
                "JUSTINMARTYR", "JOHNBAPTIST", "ANNIE", "ANNA", "CAESARION",
                "CAESAREA", "AUGUSTA", "ANNMARGARET", "AUGUST", "AUGUSTINE",
                "CLEMENZO", "JONATHANIAN", "CAESARINA", "AUGUSTINUS", "CONSTANS",
                "HELENE" };
        System.out.println(keys.length);

        for (String k : keys)
            map.put(k, null);
        System.out.println(map);
    }
}
