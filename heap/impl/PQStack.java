package impl;
/**
 * Stack.java
 *
 * Class to implement a stack using a priority queue.
 * 
 * CS 345, Wheaton College
 * Originally for CSCI 245, Spring 2007
 * Revised Jan 4, 2016
 */

import java.util.HashMap;
import java.util.Comparator;
import java.util.NoSuchElementException;

import adt.FullContainerException;
import adt.Stack;

public class PQStack<E> implements Stack<E> {

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
     * @param maxSize The capacity of this stack.
     */
    public PQStack(int maxSize) {
        arrivalTimes = new HashMap<E, Integer>();
        Comparator<E> compy = new Comparator<E>(){
        	public int compare(E c1, E c2){
        		if(arrivalTimes.get(c1) > arrivalTimes.get(c2)) return 1;
        		if(arrivalTimes.get(c1) < arrivalTimes.get(c2)) return -1;
        		return 0;
        	}
        };

        pq = new HeapPriorityQueue<E>(maxSize, compy);
        priority = 0;

    }

    /**
     * Is this stack empty? It is if the pq is empty.
     * @return True if this is empty, false otherwise.
     */
    public boolean isEmpty() { return pq.isEmpty(); }

    /**
     * Is this stack full? It is if the pq is full.
     * @return True if this is full, false otherwise.
     */
    public boolean isFull() { return pq.isFull(); }

    /**
     * Retrieve (but do not remove) the top element of this stack.
     * @return The top element.
     */
    public E top() { 
    	if(pq.isEmpty()) throw new NoSuchElementException();
    	return pq.max();    }

    /**
     * Retreive and remove the top element of this stack.
     * @return The top element.
     */
    public E pop() {
    	if(pq.isEmpty()) throw new NoSuchElementException();
    	E toReturn = pq.extractMax();
    	arrivalTimes.remove(toReturn);
    	return toReturn;    }

    /**
     * Add an element to this stack.
     * @param x The element to add.
     */
    public void push(E x) {
    	if(pq.isFull()) throw new FullContainerException();
    	arrivalTimes.put(x, priority ++);
    	pq.insert(x);    }

    public String toString() { return pq.toString(); }
    
}


