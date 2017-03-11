package impl;

import java.util.Comparator;

/**
 * Heap.java
 * 
 * Abstract class to provide the basic functionality of a heap, to
 * be used, for example, in heapsort or in a priority queue.
 *
 * @author Thomas VanDrunen
 * CSCI 345, Wheaton College   
 * Originally for CSCI 245, Spring 2007
 * Revised June 2, 2015
 */

public abstract class Heap<E> {

	/**
	 * The array containing the internal data of the heap.
	 */
	protected E[] internal;

	/**
	 * The portion of the array currently used to store the heap.
	 */
	protected int heapSize;

	/**
	 * Comparator to determine the priority of keys.
	 */
	protected Comparator<E> compy;

	/**
	 * Find the index of the parent of the node at a given index.
	 * @param i The index whose parent we want.
	 * @return The index of the parent.
	 */
	protected int parent(int i) { return (i - 1) / 2; }

	/**
	 * Find the index of the left child of the node at a given index.
	 * @param i The index whose left child we want.
	 * @return The index of the left child.
	 */
	protected int left(int i ) { return 2 * i + 1; }

	/**
	 * Find the index of the right child of the node at a given index.
	 * @param i The index whose right child we want.
	 * @return The index of the right child.
	 */
	protected int right(int i) { return 2 * i + 2; }

	/**
	 * Force the (max-) heap property on the subtree rooted at
	 * index i.
	 * @param i The index where we want to make a heap.
	 * PRECONDITION: The subtrees rooted at the left and right
	 * children of i are already heaps.
	 * POSTCONDITION: The subtree rooted at i is a heap.
	 */
	protected void heapify(int i) {
		E temp;
		E left = null;
		E right = null;
		E current = internal[i];

		if(left(i) < heapSize) left = internal[left(i)];
		if(right(i) < heapSize ) right = internal[right(i)];

		if(left != null && right != null){
			if(compy.compare(current, right) < 0 && compy.compare(current, left) < 0){
				temp  = current;
				if(compy.compare(right, left) < 0){
					internal[i] = left;
					internal[left(i)] = temp;
					heapify(left(i));
				}else{
					internal[i] = right;
					internal[right(i)] = temp;
					heapify(right(i));
				}
			}else if(compy.compare(current, left) < 0){
				temp  = current;
				internal[i] = internal[left(i)];
				internal[left(i)] = temp;
				heapify(left(i));
			}else if(compy.compare(current, right) < 0){
				temp  = current;
				internal[i] = internal[right(i)];
				internal[right(i)] = temp;
				heapify(right(i));
			}
		}else if(left != null){
			if(compy.compare(current, left) < 0){
				temp  = current;
				internal[i] = internal[left(i)];
				internal[left(i)] = temp;
				heapify(left(i));
			}
		}else if(right != null){
			if(compy.compare(current, right) < 0){
				temp  = current;
				internal[i] = internal[right(i)];
				internal[right(i)] = temp;
				heapify(right(i));
			}
		}


	}

	/**
	 * Display the state of the heap as an array. The entire 
	 * array is displayed; a vertical bar (pipe) indicates the 
	 * end of the heap.
	 * @return A string displaying the state of the heap.     * 
	 */
	@Override
	public String toString() {
		String toReturn = "[";
		for (int i = 0; i < internal.length; i++) {
			if (i == heapSize)
				toReturn += "| ";
			toReturn += internal[i] + " ";
		}
		if (heapSize == internal.length) 
			toReturn += "|";
		toReturn += "]";
		return toReturn;

	}

}
