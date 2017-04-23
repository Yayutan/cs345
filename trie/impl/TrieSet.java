package impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.NoSuchElementException;

import adt.Set;

/**
 * TrieSet
 * 
 * Implementation of the Set ADT for strings using a trie.
 * 
 * @author Thomas VanDrunen
 * CSCI 345, Wheaton College
 * April 22, 2015, revise April 19, 2016
 */
public class TrieSet implements Set<String> {

	/**
	 * Class for nodes in this Trie.
	 * Invariant: No node except the root can be empty; i.e., For any
	 * node (except the root), either it is terminal or it has
	 * at least one non-null child (which in turn must either be
	 * non-terminal or have at least one non-null child...)
	 */
	private class TrieNode {
		/**
		 * Children nodes in this trie
		 */
		TrieNode[] children;

		/**
		 * Is the string which would end at this node (not
		 * descend into any child node) in this set?
		 */
		boolean terminal;

		/**
		 * Constructor for a node initially empty
		 * (which, apart from the root, would break the invariant)
		 */
		TrieNode() {
			children = new TrieNode[26];
			terminal = false;
		}

		/**
		 * Count the number of string (suffixes) in the
		 * subtrie rooted at this node.
		 * @return
		 */
		public int size() {
			int size = 0;
			TrieNode current = this;

			// If this is a terminal node, it represents a new found string
			if(this.terminal) size = 1;

			// Adding up the size of all subtries
			for(int i = 0; i <26; i++){
				current = this.children[i];
				if(current != null)
					size += current.size();
			}
			return size;
		}

		/**
		 * Remove a String (suffix) from the subtrie rooted here.
		 * If that removal empties this subtrie, then the subtrie
		 * should be pruned.
		 * @param item The string to be removed from this subtrie, which
		 * is a suffix of the string to be removed from the entire trie.
		 * @return this, if the subtrie rooted here is still live after the 
		 * removal, or null otherwise
		 */
		public TrieNode remove(String item) {
			// Base case
			// A dead node: all Nodes in the subtrie is not a terminal, return null
			if(item.length() == 0){
				this.terminal = false;
				if(this.size()==0) return null;
				else return this;
			}
			char letter = item.charAt(0);
			TrieNode current = this.children[c2i(letter)];

			// If item is not in the set
			if(current == null) return this;

			TrieNode pre = current.remove(item.substring(1)); 
			this.children[c2i(letter)] = pre;
			if(pre == null && this.size() == 0)
				return null;
			else
				return this;
		}


		/**
		 * Iterator for the strings in this subtrie.
		 * @param prefix The prefix to prepend to the suffixes found
		 * in this subtrie to make them strings in the main trie
		 * @return The iterator
		 */
		public Iterator<String> iterator(final String prefix) {
			// TODO (BONUS)
			return null;
		}

		/**
		 * Iterator for the strings in this subtrie that match the given
		 * pattern, each with the given prefix prepended to them.
		 * The pattern is made of letters and . as a wildcard.
		 * For example, if prefix = "ABC" and pattern = ".D.E", then
		 * this iterator would return such words as "ABCZDXE" and 
		 * "ABCTDFE" if this subtrie contained the strings "ZDXE" and 
		 * "TDFE".
		 * @param prefix The prefix to prepend to the suffixes found
		 * in this subtrie to make them strings in the main trie
		 * @param pattern The pattern to match strings in this subtrie
		 * against, make up of letters (interpreted literally) and .
		 * interpreted as a wildcard.
		 */
		public Iterator<String> matchIterator(final String prefix, final String pattern) {
			// TODO (BONUS)
			return null;
		}
	}

	// --- Exception classes ---

	public static class BadCharException extends RuntimeException {
		public BadCharException(char c) {
			super("Bad character: " + c);
		}

		private static final long serialVersionUID = -3495608442105421490L;
	}

	public class BadModeException extends RuntimeException {
		private static final long serialVersionUID = -7783643567574205891L;

		public BadModeException(int mode) {
			super("Unknown Trie mode: " + mode);
		}

	}


	// --- The main parts of the TrieSet class start here ---

	/**
	 * The root of the trie. This is never null, even when
	 * the trie is empty (apart from the root, we assume that
	 * (non-null) nodes are never empty).
	 */
	private TrieNode root;

	/**
	 * 0 - capitals only; 1 - lowercase only; 2 - case insensitive
	 */
	private int mode;

	/**
	 * Convert a character to an index, according to the mode.
	 */
	private int c2i(char c) {
		if ((mode == 0 || mode == 2) && c >= 'A' && c <= 'Z')
			return c - 'A';
		else if ((mode == 1 || mode == 2) && c >= 'a' && c <= 'z')
			return c - 'a';
		else
			throw new BadCharException(c);
	}

	/**
	 * Convert an index to a character, according to the mode.
	 */
	private char i2c(int i) {
		if (mode == 0 || mode == 2) return (char) ('A' + i);
		else return (char) ('a' + i);
	}

	/**
	 * Constructor.
	 * @param mode 0 - capitals only; 1 - lowercase only; 2 - case insensitive
	 */
	public TrieSet(int mode) {
		if (mode < 0 || mode > 2)
			throw new BadModeException(mode);
		this.mode = mode;
		root = new TrieNode();
	}

	/**
	 * Constructor defaulting to capitals only
	 */
	public TrieSet() { this(0); }

	/**
	 * Add an item to this set.
	 */
	public void add(String item) {
		TrieNode current = root;
		char charNow;		

		if(this.mode == 0) item = item.toUpperCase();
		else if(this.mode == 1) item = item.toLowerCase(); 


		while(item.length() > 0){
			charNow = item.charAt(0);

			// If the next TrieNode is null(there is currently not a path in there, create one)
			if(current.children[c2i(charNow)] == null){
				current.children[c2i(charNow)] = new TrieNode();
			}
			current = current.children[c2i(charNow)];
			item = item.substring(1);
		}
		// Set the terminal letter
		current.terminal = true;
	}

	/**
	 * Does this set contain the given item?
	 */
	public boolean contains(String item) {
		if (root == null) return false;

		TrieNode current = root;
		char charNow;
		while(current != null){
			// If we find the string in the Trie, check if it is a terminal
			if(item.length() == 0){
				if(current.terminal) return true;
				return false;
			}
			charNow = item.charAt(0);
			current = current.children[c2i(charNow)];
			item = item.substring(1);
		}
		// If current TrieNode reaches end first, no word with the prefix exists
		return false;
	}

	/**
	 * Remove the given item from the set, if it exists
	 */
	public void remove(String item) {
		root.remove(item);
	}

	/**
	 * Compute the number of items in the set
	 */
	public int size() {
		return root.size();
	}

	/**
	 * Test if the set is empty. We could call size(),
	 * but it's illuminating to do it directly by looking
	 * at the root.
	 */
	public boolean isEmpty() {
		boolean emptySoFar = ! root.terminal;
		for (int i = 0; emptySoFar && i < root.children.length; i++)
			emptySoFar &= root.children[i] == null;
		return emptySoFar;
	}

	/**
	 * Make an iterator over the strings in this trie.
	 */
	public Iterator<String> iterator() {
		return root.iterator("");
	}

	/**
	 * Find and return the longest string in the set, if any,
	 * that is a prefix of the given string.
	 * @param s The string to find a prefix of
	 * @return The longest string, if any, in the set, that is
	 * a prefix of s
	 */
	public String longestPrefixOf(String s) {
		// TODO (BONUS)
		return null;
	}

	/**
	 * Return the keys with the given prefix as an iterable collection.
	 * @param s The string to find prefixes for
	 * @return
	 */
	public Iterable<String> keysWithPrefix(final String s) {
		// TODO (BONUS)
		return null;
	}

	/**
	 * Return the keys that match the given pattern as an iterable
	 * collection. The "pattern" is a string with some letters replaced
	 * with periods. Thus this will find keys of the same length as
	 * the given pattern as also having the same letters (in the same
	 * places) of those letters that exist in the pattern, but treating
	 * the periods as wildcards. This functionality is suggested by
	 * Sedgewick. Of course more interesting stuff could be done
	 * (say, regular expressions), but this is simple enough for
	 * a quick exercise.
	 * @param s
	 * @return
	 */
	public Iterable<String> keysThatMatch(final String s) {
		return new Iterable<String>() {
			public Iterator<String> iterator() {
				return root.matchIterator("", s);
			}
		};
	}




}
