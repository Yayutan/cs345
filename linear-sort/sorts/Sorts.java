package sorts;

public class Sorts {

    /**
     * Interface for objects that determine a positive integer
     * for objects of a given type so that those objects
     * may be sorted by those integers.
     */
    public static interface ToInteger<T> {
        /**
         * What integer should we use for sorting purposes for
         * the given item?
         */
        int v(T item);
    }

    /**
     * Sort the given array using counting sort.
     * @param array The array (or anything) to sort, assumed non-null and
     * with no null elements.
     * @param toInt A means of determining a number (for sorting
     * purposes) for the items in the array
     */
    public static <T> void countingSort(T[] array, ToInteger<T> toInt) {
        
        // The maximum value in the array (once we calculate it)
        int maxVal = 0;
        // A. Find the maximum value in the array
        // ...
        for(int i = 0; i < array.length; i++){
        	if(toInt.v(array[i]) > maxVal) maxVal = toInt.v(array[i]);
        }
        
        // The occurrences of each value (once we calculate them)
        int[] counts = new int[maxVal+1];
        // B. tabulate the occurrences of each value
        // ...
        for(int i = 0; i < array.length; i++){
        	counts[toInt.v(array[i])]++;
        }        
        // The initial places for each value (once we calculate them)
        int[] nextPlace = new int[maxVal+1];
        // C. Determine the initial next place for each value
        nextPlace[0] = 0;
        int accum = counts[0];
        for(int j = 1; j < counts.length; j++){
        	nextPlace[j] = accum;
        	accum += counts[j];
        }
        
        // The auxiliary array into which to sort the array
        T[] aux = (T[]) new Object[array.length];
        // D. Sort the items into aux
        // ...
        for(int k = 0; k < aux.length; k++){
        	aux[nextPlace[toInt.v(array[k])]] = array[k];
        	nextPlace[toInt.v(array[k])]++;
        }
        
        // E. move them back to array
        // ...
        for(int m = 0; m < aux.length; m++){
        	array[m] = aux[m];
        }
    }

    /**
     * Sort the given array using radix sort with the given radix.
     * @param array The array to sort
     * @param r The radix to use (must be greater than 1)
     */
    public static void radixSort(Integer[] array, final int r) {
        assert r > 1;

        // The maximum number of digits for any number in array
        // (once we calculate it)
        int maxNumDigits = 0;  

        // calculate the number of digits
        for (int i = 0; i < array.length; i++){
            int numDigits = (int) Math.ceil(Math.log(array[i])/Math.log(r));
            if (numDigits > maxNumDigits)
                maxNumDigits = numDigits;
        }
        
        // sort by each digit
        int rPow = 1;
        for (int i = 0; i < maxNumDigits; i++) {
            // "Final" version of rPow that we can use inside an anonymous inner class
            final int rp = rPow;
            countingSort(array, new ToInteger<Integer>() {
                public int v(Integer item) {
                	return (item / rp) % r;
                }
            });
            rPow *= r;
        }
    }
    


}