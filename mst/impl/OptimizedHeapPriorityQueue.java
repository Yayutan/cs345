package impl;

import java.util.Comparator;
import java.util.NoSuchElementException;

import adt.PriorityQueue;

/**
 * OptimizedHeapPriorityQueue.java
 *
 * Class to implement a priority queue using a (max) heap
 * optimized for elements that know where they are in
 * the underlying array.
 *
 * @author Thomas VanDrunen
 * CSCI 345, Wheaton College   
 */
public class OptimizedHeapPriorityQueue<E extends HeapPositionAware> implements PriorityQueue<E>{
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
     * Constructor. Initialize this pq to empty.
     * @param maxSize The capacity of this priority queue.
     * @param compy The Comparator defining the priority of
     * these items.
     */
    @SuppressWarnings("unchecked")
    public OptimizedHeapPriorityQueue(int maxSize, Comparator<E> compy) {
        internal = (E[]) new HeapPositionAware[maxSize];
        heapSize = 0;
        this.compy = compy;
    }

    /**
     * Constructor. Initialize this pq to the keys in the
     * given iterable. The number of keys in the iterable
     * collection is taken as the capacity of the pq.
     * @param items An iterable collection of keys taken as the
     * initial contents of the pq.
     * @param compy The Comparator defining the priority of
     * these items.
     */
    @SuppressWarnings("unchecked")
    public OptimizedHeapPriorityQueue(E[] items, Comparator<E> compy) {
        internal = (E[]) new HeapPositionAware[items.length];
        this.compy = compy;
        heapSize = 0;
        for (E item : items) {
            set(heapSize, item);
            heapSize++;
        }
        for (int i = heapSize - 1; i >= 0; i--)
            heapify(i);
    }

    
    /**
     * Set the value at a position in the underlying array.
     * This also informs the value itself where it is in the
     * array.
     * @param i
     * @param item
     */
    private void set(int i, E item) {
    	internal[i] = item;
    	item.setPosition(i);
    }
    
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
					set(i, left);
					set(left(i),temp);
					heapify(left(i));
					
				}else{
					set(i, right);
					set(right(i),temp);
					heapify(right(i));
				}
			}else if(compy.compare(current, left) < 0){
				temp  = current;
				set(i, left);
				set(left(i),temp);
				heapify(left(i));
			}else if(compy.compare(current, right) < 0){
				temp  = current;
				set(i, right);
				set(right(i),temp);
				heapify(right(i));
			}
		}else if(left != null){
			if(compy.compare(current, left) < 0){
				temp  = current;
				set(i, left);
				set(left(i),temp);
				heapify(left(i));
			}
		}else if(right != null){
			if(compy.compare(current, right) < 0){
				temp  = current;
				set(i, right);
				set(right(i),temp);
				heapify(right(i));
			}
		}
    }
    
    /**
     * Is this pq empty?
     * It is if its heap size is zero.
     * @return True if this is empty, false otherwise.
     */
    public boolean isEmpty() {
        return heapSize == 0;
    }

    /**
     * Is this pq full?
     * It is if its heap size is equal to the array's size.
     * @return True if this is full, false otherwise.
     */
    public boolean isFull() {
        return heapSize == internal.length;
    }

    /**
     * Insert a new item into this pq.
     * @param x The item to insert.
     */
    public void insert(E x) {
    	if(isFull()){
    		E[] temp = (E[]) new Object [internal.length * 2];
    		for (int i = 0; i < internal.length; i++)
    			temp[i] = internal[i];
    		internal = temp;    		
    	}
    	internal[heapSize++] = x;

    	int i = heapSize - 1;
    	while(i > 0){
    		heapify((i - 1) / 2);
    		i = (i - 1) / 2;
    	}
    }

    /**
     * Return (but do not remove) the maximum element.
     * According to the (max-) heap property, the maximum element
     * should be at position 0.
     * @return The maximum element.
     */
    public E max() { return internal[0]; }


    /**
     * Return and remove the maximum element.
     * @return The maximum element.
     */
    public E extractMax() {
        if(heapSize == 0) return null;
        E toReturn = internal[0];
        set(0, internal[heapSize - 1]);
        heapSize--;
        heapify(0);
        return toReturn;        
    }

    /**
     * Determine whether this key is in the pq.
     * @param key The key to look for.
     * @return True if this key is in the pq, false otherwise.
     */
    public boolean contains(E key) {
        // is the given key the thing that is in the position
        // it thinks it is in?
        return internal[key.getPosition()].equals(key)
                && key.getPosition() < heapSize;
    }

    /**
     * Indicate that the priority of a key at a given key
     * has changed, which may affect the internal storage
     * of the pq.
     * @param key The key whose priority has changed.
     */
    public void increaseKey(E key) {
        int i = key.getPosition();
        if (i < 0 || i >= heapSize || ! internal[i].equals(key))
            throw new NoSuchElementException();
        
        while (i > 0 && compy.compare(internal[parent(i)], internal[i]) < 0) {
            E temp = internal[i];
            set(i, internal[parent(i)]);
            set(parent(i), temp);
            i = parent(i);
        }

    }


}
