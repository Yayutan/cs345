package impl;

import java.util.Iterator;
import java.util.NoSuchElementException;

import adt.Map;

/**
 * ArrayMap
 * 
 * Class to implement the Map ADT using an array. * 
 * 
 * (Unlike Stack and Queue, Map is not specified to
 * throw NoSuchElementException when get() or remove()
 * are called with non-existent keys. Instead get()
 * returns null and remove() does nothing. The only
 * reason for this decision is that that's what the tests
 * for Maps that I already had assumed. Similarly put() doesn't
 * throw a FullContainerException.)
 * 
 * CSCI 345, Wheaton College
 * Spring 2016
 * @param <K> The key-type of the map
 * @param <V> The value-type of the map
 */
public class ArrayMap<K, V> implements Map<K, V> {

    /**
     * Class for key-value pairs. This map implementation
     * is essentially an array of these.
     */
    private static class Association<K, V> {
        K key;
        V val;
        Association(K key, V val) {
            this.key = key;
            this.val = val;
        }
    }

    /**
     * An array of key-value associations, the internal
     * representation of this map.
     */
    private Association<K,V>[] internal;
    private int size;

    /**
     * Plain constructor. 
     */
    @SuppressWarnings("unchecked")
    public ArrayMap() {
        // 100 as length of the initial array is an arbitrary choice.
        internal = (Association<K,V>[]) new Association[100];
        size = 0;
    }

    /**
     * Cause the internal array to double in size.
     */
    private void grow() {
        @SuppressWarnings("unchecked")
        Association[] temp = (Association<K,V>[]) new Association[internal.length * 2];
        for (int i = 0; i < internal.length; i++)
            temp[i] = internal[i];
        internal = temp;
    }

    /**
     * Return an iterator over this collection (remove() is
     * unsupported, nor is concurrent modification checked).
     */
    public Iterator<K> iterator() {
        return new Iterator<K>(){        	
        	
        	private int current = 0;
        	public boolean hasNext(){
        		if (current < size) return true;
        		else return false;
        	}
        	
        	public K next(){
        		return internal[current++].key;
        	}
        	
        	public void remove(){
        		throw new UnsupportedOperationException();
        	}
        };
    }

    
    /**
     * Add an association to the map.
     * @param key The key to this association
     * @param val The value to which this key is associated
     */
    public void put(K key, V val) {
    	int found = find(key);
    	if(found >= 0) internal[found].val = val;
    	else{
    		if(size == internal.length){
    			grow();
    		}
    		internal[size++] = new Association<K, V> (key, val);
    	}
    }

    /**
     * Get the value for a key.
     * @param key The key whose value we're retrieving.
     * @return The value associated with this key, null if none exists
     */
    public V get(K key) {
    	int found =  find(key);
    	if(found < 0) return null;
    	V toReturn = internal[found].val;
    	return toReturn;
    }
    
    /**
     * Method that finds the index of a given key.
     * Returns -1 if key not found.
     * @param key the key to look for
     * @return the index of that key, -1 if key not found
     */
    public int find(K key){
    	int found = -1;
    	for(int i = 0; i < size; i++)
    		if(internal[i].key.equals(key)) found = i;
    	return found;	
    }

    /**
     * Test if this map contains an association for this key.
     * @param key The key to test.
     * @return true if there is an association for this key, false otherwise
     */
    public boolean containsKey(K key) {
    	if(find(key) >= 0) return true;
    	return false;
    }

    /**
     * Remove the association for this key, if it exists.
     * @param key The key to remove
     */
    public void remove(K key) {
    	int found = find(key);
    	if(found < 0) return;
    	if(found == size - 1){
    		size--;
    		return;
    	}
    	
    	Association<K, V> temp;
    	for(int i = found; i < size - 1; i++){
    		temp = internal[i + 1];
    		internal[i] = temp;
    	}
    		size --;
    }

}
