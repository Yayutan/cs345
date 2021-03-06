package impl;

import java.util.Comparator;


/**
 * HeapSorter.java
 *
 * Class to implement the heapsort algorithm.
 *
 * @author Thomas VanDrunen
 * CSCI 345, Wheaton College   
 * Originally for CSCI 245, Spring 2007
 * Revised June 2, 2015
 */

public class HeapSorter extends Heap<Integer> {


    /**
     * Constructor. Take an array an sets it up as a (max-) heap.
     * @param internal The array to be used for the internal representation.
     */
    private HeapSorter(int[] array) {
        internal = new Integer[array.length];
        for (int i = 0; i < array.length; i++)
            internal[i] = array[i];
        heapSize = array.length;
        

        compy = new Comparator<Integer>(){
        	public int compare(Integer c1, Integer c2){
        		if(c1 > c2) return 1;
        		if(c1 < c2) return -1;
        		return 0;
        	}
        };

        if(heapSize != 0){
        	for(int i = (array.length - 1)/2; i >= 0; i--){
        		heapify(i);
        	}
        }
    }


        
    /**
     * Sort this array, in place.
     * @param array The array to sort.
     */
    public static void sort(int[] array) {

        HeapSorter heap = new HeapSorter(array);

        // insert code for completing the heap sort algorithm,
        // with post condition that heap.internal is sorted
        int temp;
        for(int i = 0; i < array.length;i++){
        	temp = heap.internal[0];
        	heap.internal[0] = heap.internal[array.length -1 - i];
        	heap.internal[array.length -1 - i] = temp;
        	heap.heapSize--;
        	heap.heapify(0);
        }
        	
        // copy elements from internal (now sorted) back to array
        for (int i = 0; i < array.length; i++)
            array[i] = heap.internal[i];
        
        
    }
    
    public static void printHeap(HeapSorter heap){
		System.out.print("Heap:");
    	for(int i = 0; i < heap.heapSize; i++)
    		System.out.print(heap.internal[i] + " ");
		System.out.println("");
    }

}
