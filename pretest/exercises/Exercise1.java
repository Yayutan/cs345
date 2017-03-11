package exercises;

/**
 * Exercise1.java
 * 
 * Class to hold method performing binary search on an array of Comparables.
 * 
 * CSCI 345
 * Spring 2016
 */
public class Exercise1 {

	/**
	 * Find the the location of the given item, if any, using a logarithmic
	 * number of comparisons.
	 * @param array The array in which to search, assumed to be sorted if not null
	 * @param item The item for which to search
	 * @return The location of the item, if it is there, or -1 if it does not exist
	 */
	public static <T extends Comparable<T>> int binarySearch(T[] array, T item) {
		if(array == null || array.length == 0) return -1;
		int low = 0;
		int high = array.length -1;
		int result;
		for(int gap = high - low, mid = (high + low) / 2; gap >= 0; gap = high - low, mid = (high + low) / 2){
			result = array[mid].compareTo(item);
			if(result == 0) return mid;
			else if(result > 0) high = mid - 1;
			else low = mid + 1;
		}
		return -1;
	}
}
