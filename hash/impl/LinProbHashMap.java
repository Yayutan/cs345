package impl;

import java.util.Iterator;
import java.util.NoSuchElementException;

import adt.Map;

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
	 * The number of pairs, needed to know when to
	 * rehash.
	 */
	private int pairs;

	/**
	 * Constructor allowing the original table size
	 * to be specified.
	 * @param initKeyCap The initial capacity of the number of keys.
	 */
	@SuppressWarnings("unchecked")
	public LinProbHashMap(int initKeyCap) {

		keys = (K[]) new Object[initKeyCap];
		values = (V[]) new Object[initKeyCap];
		pairs = 0;
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
		int best = hash(key);
		if(keys[best] == null) return best;

		while(keys[best] != null){
			if(keys[best] == key) break;
			best++;
			if(best == keys.length) best = 0;	    	   	
		}
		return best;

	}

	/**
	 * Add an association to the map. (If the map is full,
	 * the rehash.)
	 * @param key The key to this association
	 * @param val The value to which this key is associated
	 */
	public void put(K key, V val) {
		keys[findIndex(key)] = key;
		values[findIndex(key)] = val;
		pairs++;
	}

	/**
	 * Make the arrays grow, and reinsert the keys.
	 */
	private void rehash() {

		int newSize = PrimeSource.nextOrEqPrime(keys.length + 1);

		K[] oldKeys = keys;
		V[] oldValues = values;

		keys = (K[]) new Object[newSize];
		values = (V[]) new Object[newSize];
		pairs = 0;

		for (int i = 0; i < oldKeys.length; i++)
			if (oldKeys[i] != null)
				put(oldKeys[i], oldValues[i]);
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
	 * Compare where the key in the given position should go
	 * relative to the gap, using the "compareTo" protocol.
	 * If the ideal place of the key in the given position is
	 * somewhere between the given gap and the position
	 * (traveling from the gap by incrementing index and 
	 * wrapping around), then the key should come after the
	 * gap, so return a positive value. If the gap itself
	 * is the key's ideal position then return 0. If the ideal
	 * position is not in the range "between" (increasing index
	 * with wrap-around) the gap and the position, then return -1,
	 * that is, the key ideally goes "before" the gap.
	 * @param gap The place we're measuring relative to; assumed to hold null
	 * @param position The position of the key we're measuring; assumed
	 * to hold a nonnull value.
	 * @return A negative, zero, or positive value depending on whether
	 * the key in the given position ideally comes before, exactly at,
	 * or after the gap.
	 */
	private int compareIdealPlace(int gap, int position) {
		K target = keys[position];
		int best = hash(target);
		if(best == gap) return 0;


		if(gap < position){
			if(best > gap && best < position) return 1;
		}else{
			if(best > gap || best < position ) return 1;
		}

		return -1;

	}

	/**
	 * Remove the association for this key, if it exists.
	 * @param key The key to remove
	 */
	public void remove(K key) {
		if(!containsKey(key)) return;
		pairs --;
		int gap = findIndex(key);
		keys[gap] = null;
		values[gap] = null;

		int i = gap + 1;
		if(i == keys.length) i = 0;
		while(keys[i] != null){
			if(compareIdealPlace(gap, i) == 0){
				keys[gap] = keys[i];
				values[gap] = values[i];
				keys[i] = null;
				values[i] = null;
				gap = i;
			}
			i++;
			if(i == keys.length) i = 0;

		}
	}

	/**
	 * Return an iterator over the keys of this map.
	 * @return The iterator.
	 */
	public Iterator<K> iterator() {
		int index = 0;
		while(index < keys.length && keys[index] == null) index++;
		final int first = index;

		return new Iterator<K> (){
			private int index = first;

			public boolean hasNext(){
				return index < keys.length;
			}

			public K next(){
				K toReturn = keys[index];
				do{
					index ++;
				}while(index < keys.length && keys[index] == null);


				return toReturn;	
			}

			public void remove(){
				throw new UnsupportedOperationException();
			}


		};


	}

}
