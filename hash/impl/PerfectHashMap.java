package impl;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

import adt.Map;
import adt.Set;
import impl.ListSet;

/**
 * PerfectHashMap
 * 
 * Implementation of perfect hashing, that is, when the keys are known
 * ahead of time. Note that containsKey and get will work as expected
 * if used with a key that doesn't exist. However, we assume put
 * will never be called using a key that isn't supplied to the
 * constructor; behavior is unspecified otherwise.
 * 
 * @author Thomas VanDrunen
 * CSCI 345, Wheaton College
 * March 17, 2015
 * @param <K> The key-type of the map
 * @param <V>The value-type of the map
 */

public class PerfectHashMap<K, V> implements Map<K, V> {


	/**
	 * Secondary maps for the buckets
	 */
	private class SecondaryMap implements Map<K, V> {

		/**
		 * The keys in this secondary map. This is necessary to
		 * check when get and containsKey are called on spurious keys
		 * and also for the iterator.
		 */
		K[] keys;  

		/**
		 * The values in the secondary map.
		 */
		V[] values;

		/**
		 * The number of slots in the arrays, computed as the square
		 * of the number of keys that can go here.
		 */
		int m;

		/**
		 * The hash function, drawn from class Hpm
		 */
		HashFunction<Object> h;

		/**
		 * Constructor. Given a set of keys, make appropriately
		 * size arrays and a hash set that makes no collisions.
		 * @param givenKeys
		 */
		@SuppressWarnings("unchecked")
		SecondaryMap(Set<K> givenKeys) {

			// TODO
			//generating new hash functions until you find one that has no collisions for the keys
			m = givenKeys.size() * givenKeys.size();

			boolean collide = false;
			boolean found = false;
			values = (V[]) new Object[m];
			keys = (K[]) new Object[m];

			boolean[] find = new boolean[m];

			while(found == false && m != 0){
				h = UniversalHashFactory.makeHashFunction(p, m, 100);
				collide = false;
				find = new boolean[m];
				for(K key: givenKeys){
					int index = (h.hash(key) & 0x7fffffff) ;

					if(!find[index]) find[index] = true;
					else{
						collide = true;
						break;
					}

				}
				found = !collide;
			}

		}

		/**
		 * Add an association to the map. We assume the given
		 * key was known ahead of time.
		 * @param key The key to this association
		 * @param val The value to which this key is associated
		 */

		public void put(K key, V val) {
			int pos = Math.abs(h.hash(key));
			keys[pos] = key;
			values[pos] = val;
		}

		/**
		 * Get the value for a key.
		 * @param key The key whose value we're retrieving.
		 * @return The value associated with this key, null if none exists
		 */
		public V get(K key) {
			// special case that will apply only on spurious keys
			if (! containsKey(key)) return null;
			return values[Math.abs(h.hash(key))];
		}

		/**
		 * Test if this map contains an association for this key.
		 * @param key The key to test.
		 * @return true if there is an association for this key, false otherwise
		 */
		public boolean containsKey(K key) {
			// special case that will apply only on spurious keys
			if (m == 0) return false;
			int pos = Math.abs(h.hash(key));
			return keys[pos] != null 
					// next part necessary only if we assume
					// keys that can't be put may be tested
					&& keys[pos].equals(key);
		}

		/**
		 * Remove the association for this key, if it exists.
		 * @param key The key to remove
		 */
		public void remove(K key) {
			// special case that will apply only on spurious keys
			if (containsKey(key)) 
				//keys[hash(key,a,b,p,m)] = null;
				keys[Math.abs(h.hash(key))] = null;
		}

		/**
		 * The iterator for this portion of the map.
		 */
		public Iterator<K> iterator() {

			int i = 0;

			while(i < m && keys[i] == null) i++;

			final int start = i;
			// TODO
			return new Iterator<K> (){
				private int current = start;


				public boolean hasNext(){
					return current < m;
				}

				public K next(){
					K toReturn = keys[current];
					do{
						current++;
					}while(current < m && keys[current] == null);

					return toReturn;
				}


				public void remove(){
					throw new UnsupportedOperationException();
				}


			};
		}

	}

	/**
	 * Secondary maps
	 */
	private SecondaryMap[] secondaries;

	/**
	 * A prime number greater than the greatest hash value
	 */
	private int p;

	/**
	 * A parameter to the hash function; here, the number of keys
	 * known ahead of time.
	 */
	private int m;

	/**
	 * The hash function
	 */
	private HashFunction<Object> h;

	/**
	 * Constructor. Takes the keys (all known ahead of time) to
	 * set things up to guarantee no collisions.
	 * @param keys
	 */
	@SuppressWarnings("unchecked")
	public PerfectHashMap(K[] keys) {

		// TODO
		/*
        1. Find a prime number greater than all the keys. 
        	1-1 Since you don't know what the keys are you need to convert them to integers first. 
        	    Use the Java-given hashCode() method for this. (key.hashCode() & 0x7fffffff) % 100.)
		 */
		m = keys.length;
		int max = 0;
		for(int i = 0; i < m; i++){
			int hash = (keys[i].hashCode() & 0x7fffffff) % 100;
			if(hash > max) max = hash;
		}

		p = PrimeSource.nextOrEqPrime(max + 1);

		//2. Make a hash function for the primary map (see UniversalHashFactory.makeHashFunction())
		h = UniversalHashFactory.makeHashFunction(p, m, 100);

		//3. Determine which keys are going to end up in which buckets
		/*4. Make the secondary maps. Note that in making the secondary maps, 
        you need to pass the SecondaryMap constructor the keys that will end up in that secondary map---
        which requires you to calculate which keys will end up in which map. */
		secondaries = new PerfectHashMap.SecondaryMap[m];

		ListSet[] content = new ListSet[m];	

		for(int b  = 0; b < m; b++){
			content[b] = new ListSet();
		}

		for(int i = 0; i < m; i++){
			int hash = (h.hash(keys[i]) & 0x7fffffff) % m;
			content[hash].add(keys[i]);
		}
		int s = 0;
		for(int a  = 0; a < m; a++){
			secondaries[a] = new SecondaryMap(content[a]);
		}

	}

	/**
	 * Add an association to the map. We assume the given
	 * key was known ahead of time.
	 * @param key The key to this association
	 * @param val The value to which this key is associated
	 */
	public void put(K key, V val) {
		secondaries[h.hash(key)].put(key, val);
	}

	/**
	 * Get the value for a key.
	 * @param key The key whose value we're retrieving.
	 * @return The value associated with this key, null if none exists
	 */
	public V get(K key) {
		return secondaries[h.hash(key)].get(key);
	}

	/**
	 * Test if this map contains an association for this key.
	 * @param key The key to test.
	 * @return true if there is an association for this key, false otherwise
	 */
	public boolean containsKey(K key) {
		return secondaries[h.hash(key)].containsKey(key);
	}

	/**
	 * Remove the association for this key, if it exists.
	 * @param key The key to remove
	 */
	public void remove(K key) {
		secondaries[h.hash(key)].remove(key);
	}

	/**
	 * Return an iterator over this map
	 */
	public Iterator<K> iterator() {

		// TODO

		int i = 0;
		Iterator<K> it = secondaries[i].iterator();
		boolean foundNext = it.hasNext();

		while(!foundNext && i < m){
			it = secondaries[i].iterator();						
			foundNext = it.hasNext();
			if(!foundNext) i++;
		}
		final int start = i;


		return new Iterator<K>(){

			private int index = start;
			private Iterator<K> current;
			private boolean firstRun = true;

			public boolean hasNext(){
				if(index == m - 1) return current.hasNext();
				return index < m;
			}


			public K next() {
				if(firstRun){
					firstRun = false;
					current = secondaries[start].iterator();
				}
				
				K key = current.next();

				boolean foundNext = current.hasNext();
				if(!foundNext) index++;

				while(!foundNext && index < m){
					current = secondaries[index].iterator();						
					foundNext = current.hasNext();
					if(!foundNext) index++;
				}
			return key;
		}


		public void remove(){
			throw new UnsupportedOperationException();
		}

	};
}

}
