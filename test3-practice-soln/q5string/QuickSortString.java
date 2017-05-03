package q5string; 

public class QuickSortString {

 // sort the array of strings
    public static void quicksortStrings(String[] array) {
        quicksortStringsR(array, 0, array.length, 0);
    }

    /**
      * @param array - The array to sort (a portion of)
      * @param start - the beginning (inclusive) of the range to sort
      * @param stop - the ending (exclusive) of the range to sort
      * @param c - the character position in the strings on which to sort
      * PRECONDITION: All strings in this range are identical up to character c
      */
    public static void quicksortStringsR(String[] array, int start, int stop, int c) {
        if (stop - start > 1) { 
            if (array[stop-1].length() <= c) {
                String temp = array[stop-1];
                array[stop-1] = array[start];
                array[start] = temp;
            }
            else {
                int i = start - 1,  // pos before the start of eq-pivot
                    j = start - 1,  // pos before the start of after-pivot
                    k = start;      // pos after after-pivot
                char pivot = array[stop-1].charAt(c);
                while (k < stop - 1) {
                    String current = array[k];
                    char currentChar = c < current.length() ? current.charAt(c) : 0;
                    if (currentChar < pivot) {
                        array[k] = array[j+1];
                        array[j+1] = array[i+1];
                        array[i+1] = current;
                        i++;
                        j++;
                    }
                    else if (currentChar == pivot) {
                        array[k] = array[j+1];
                        array[j+1] = current;
                        j++;
                    }
                    k++;
                }
                String temp = array[stop-1];
                array[stop-1] = array[j+1];
                array[j+1] = temp;
                quicksortStringsR(array, start, i+1, c);
                quicksortStringsR(array, i+1, j+2, c+1);
                quicksortStringsR(array, j+2, stop, c);
            }
        }
    }
}
