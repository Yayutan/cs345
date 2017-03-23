package test;

import static org.junit.Assert.*;

import org.junit.Test;

import alg.Knapsack;

public class KnapsackTest {

    private void check(int[] weights, int[] values, int capacity, boolean result[], int max) {
        assertEquals(weights.length, result.length);
        int totalWeight = 0, totalValue = 0;
        for (int i = 0; i < weights.length; i++) 
            if (result[i]) {
                totalValue += values[i];
                totalWeight += weights[i];
            }
        assertEquals(max, totalValue);
        assertTrue(totalWeight <= capacity);
        
    }
    
    @Test
    public void dmfp() {
        // DMFP, pg 502
        int[] weights = new int[]{2, 3, 4, 7, 8, 13, 15};
        int[] values = new int[]{1, 4, 6, 10, 11, 20, 21};
        check(weights, values, 15, Knapsack.knapsack(weights, values, 15), 21);
    }

    @Test
    public void clrs() {
        // CLRS, pg 427
        int[] weights = new int[]{10, 20 , 30};
        int[] values = new int[]{60, 100, 120};
        check(weights, values, 50, Knapsack.knapsack(weights, values, 50), 220);
       
    }
    
    @Test
    public void dpv() {
        // Dasgupta et al, pg 164
        int[] weights = new int[]{6, 3, 4, 2};
        int[] values = new int[]{30, 14, 16, 9};
        check(weights, values, 10, Knapsack.knapsack(weights, values, 10), 46);
       
    }
    
    @Test
    public void fromClass() {
        int[] weights = new int[]{1, 2, 4, 5};
        int[] values = new int[]{20, 15, 90, 100};
        check(weights, values, 7, Knapsack.knapsack(weights, values, 7), 125);
    }
}
