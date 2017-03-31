package q1adt;

import java.util.Iterator;

/**
 * FastBag
 * 
 * An implementation of a Bag whose keys are known 
 * ahead of time and whose main operations (add, count,
 * and remove) operate in logarithmic time.
 * 
 * The keys are given to the constructor; they all initially
 * have count 0. Behavior is undefined if add or remove is
 * called using a key besides those given to the constructor.
 * 
 * CSCI 345
 * Test 2 Practice Problem 1.
 */

public class FastBag implements Bag<String> {

    
    private String[] internal;
    
    private int[] count;

    /**
     * Constructor that takes an iterator that gives
     * the keys in sorted order and the number of keys.
     * Behavior is undefined if the number of items returned
     * by the iterator differs from numKeys.
     * @param keys An iterator that gives the potential keys
     * in sorted order.
     * @param numKeys The number of keys
     */
	public FastBag(Iterator<String> keys, int numKeys) {
		internal = new String[numKeys];
		for(int i =  0; i < numKeys; i ++)
			internal[i] = keys.next();

		count = new int[numKeys];
		for(int j =  0; j < numKeys; j ++)
			count[j] = 0;
	}


	/**
	 * Add an item to the bag, increasing its count by 1.
	 * Behavior undefined if the given item is not one of the
	 * keys supplied to the constructor.
	 * @param item The item to add
	 */
	public void add(String item) {
		int low = 0;
		int high = internal.length;
		int mid = (low + high) / 2;
		int gap = high - low;
		
		while(gap >= 0){
			int compare = item.compareTo(internal[mid]);
			if(compare == 0){
				count[mid] ++;
				return;
			}
			if(compare > 0){
				low = mid + 1;
			}else{
				high = mid - 1;				
			}
			mid = (low + high) / 2;
			gap = high - low;	
		}
	}

    /**
     * How many times does this bag contain this item?
     * Items supplied to the constructor but either have never been
     * added or have been delete have count 0.
     * Behavior is undefined for items not supplied to the
     * constructor.
     * @param item The item to check
     * @return The count for the given item.
     */
	public int count(String item) {
		int low = 0;
		int high = internal.length;
		int mid = (low + high) / 2;
		int gap = high - low;
		
		while(gap >= 0){
			int compare = item.compareTo(internal[mid]);
			if(compare == 0){
				return count[mid];
			}
			if(compare > 0){
				low = mid + 1;
			}else{
				high = mid - 1;			
			}
			mid = (low + high) / 2;
			gap = high - low;	
		}	
		return 0;
	}

	/**
	 * Remove the given item, resetting its count to 0.
	 * Behavior is undefined for items not supplied to the
	 * constructor.
	 * @param The item to remove
	 */
	public void remove(String item) {
		int low = 0;
		int high = internal.length;
		int mid = (low + high) / 2;
		int gap = high - low;

		while(gap >= 0){
			int compare = item.compareTo(internal[mid]);
			if(compare == 0){
				count[mid] = 0;
				return;
			}
			if(compare > 0){
				low = mid + 1;
			}else{
				high = mid - 1;			
			}
			mid = (low + high) / 2;
			gap = high - low;	
		}	
	}

	/**
	 * The number of items in the bag. This is the sum
	 * of the counts, not the number of unique items.
	 * @return The number of items.
	 */
	public int size() {
		int toReturn = 0;
		for(int i = 0; i < count.length; i ++)
			toReturn += count[i];
		
		return toReturn;
		
	}

    /**
     * Is the set empty?
     * @return True if the set is empty, false otherwise.
     */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Make an iterator over the items in this bag which will
	 * return each item the number times indicated by its count.
	 * @return An iterator over the bag
	 */
	public Iterator<String> iterator() {
		 int i = 0;
		 while(i < internal.length && count[i] == 0)
			 i++;

		final int j = i;

		return new Iterator<String>(){
			private int index = j;
			private int itemLeft = index == internal.length ? 0 : count[index];

			public String next(){
				String toReturn = internal[index];
				itemLeft --;
				if(itemLeft == 0){
					do{
						index ++;
					}while(index < internal.length && count[index] == 0);
					if(index != internal.length)
						itemLeft = count[index];
				}
				return toReturn;
			}
			
			public boolean hasNext(){
				return (index < internal.length);
			}
			
			public void remove(){
				   throw new UnsupportedOperationException();
			   } 
			
		};
	}

}
