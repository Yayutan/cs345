package alg;

import adt.StringSorter;

/**
 * StringMergeSort
 * 
 * Plain old merge sort, using String's compareTo method.
 * 
 * @author Thomas VanDrunen
 * CSCI 345, Wheaton College
 */
public class StringThreeWayQuickSort implements StringSorter {


	public static final StringSorter sorter = new StringThreeWayQuickSort();

	private StringThreeWayQuickSort() {}

	public synchronized void sort(String[] array) {
		sortR(array, 0, array.length, 0);
	}

	/**
	 * Sort a given sub-array by the given prefix. 
	 * PRECONDITION: All the strings in the range [start, stop)
	 * are identical up to character pre.
	 * @param array The array to sort
	 * @param start The inclusive beginning of the range to sort
	 * @param stop The exclusive end of the range to sort
	 * @param pre The number of characters in this range for which
	 * all the characters are identical.
	 */
	private void sortR(String[] array, int start, int stop, int pre) {

		if (stop-start > 1 && pre < array[0].length()) {

			// TODO
			int i = start - 1;
			int j = start - 1;
			String pivot = array[stop - 1];
			String temp, temp2;
			for (int k = start; k < stop; k++) {
				if (array[k].charAt(pre) > pivot.charAt(pre)){
					k++;
				}else if(array[k].charAt(pre) == pivot.charAt(pre)){
					temp = array[j+1];
					array[j+1] = array[k];
					array[k] = temp;
					j++;
					k++;
				}else{

					i++;
				}
			}
			// Recursion


		}

	}


}
