package impl;
/**
 * PQQueue.java
 *
 * Class to implement a queue using a priority queue.
 * 
 * CS 345, Wheaton College
 * Originally for CSCI 245, Spring 2007
 * Revised Jan 4, 2016
 */

import java.util.HashMap;
import java.util.Comparator;
import java.util.NoSuchElementException;

import adt.FullContainerException;
import adt.Queue;

public class PQQueue<E> implements Queue<E> {

    /**
     * The priority queue to use as an internal representation.
     */
    private HeapPriorityQueue<E> pq;

    /**
     * Place to store data associated with representative
     * values in the priority queue.
     */
    private HashMap<E, Integer> arrivalTimes;

    /**
     * Keeps track of the next priority that will be used
     */
    private int priority;
    /**
     * Constructor.
     * @param maxSize The capacity of this queue.
     */
    public PQQueue(int maxSize) {
        arrivalTimes = new HashMap<E, Integer>();
        Comparator<E> compy = new Comparator<E>(){
        	public int compare(E c1, E c2){
        		if(arrivalTimes.get(c1) < arrivalTimes.get(c2)) return 1;
        		if(arrivalTimes.get(c1) > arrivalTimes.get(c2)) return -1;
        		return 0;
        	}
        };

        pq = new HeapPriorityQueue<E>(maxSize, compy);
        priority = 0;
    }

    /**
     * Is this queue empty? It is if the pq is empty.
     * @return True if this is empty, false otherwise.
     */
    public boolean isEmpty() { return pq.isEmpty(); }

    /**
     * Is this queue full? It is if the pq is full.
     * @return True if this is full, false otherwise.
     */
    public boolean isFull() { return pq.isFull(); }

    /**
     * Retrieve (but do not remove) the front element of this queue.
     * @return The front element.
     */
    public E front() { 
    	if(pq.isEmpty()) throw new NoSuchElementException();
    	return pq.max();
    }

    /**
     * Retrieve and remove the front element of this queue.
     * @return The front element.
     */
    public E remove() {
    	if(pq.isEmpty()) throw new NoSuchElementException();
    	E toReturn = pq.extractMax();
    	arrivalTimes.remove(toReturn);
    	return toReturn;
		}

    /**
     * Add an element to the back of this queue.
     * @param x The element to add.
     */
    public void enqueue(E x) {
    	if(pq.isFull()) throw new FullContainerException();
    	arrivalTimes.put(x, priority ++);
    	pq.insert(x);
    }

}
