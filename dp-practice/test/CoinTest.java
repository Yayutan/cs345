package test;

import static org.junit.Assert.*;

import org.junit.Test;

import alg.Coin;

public class CoinTest {

    private static final int[] american = { 1, 5, 10, 25};
    private static final int[] micomicona = { 1, 12, 13};
    private static final int[] wizarding = {1, 29, 493};
    private static final int[] primian = { 1, 2, 3, 5, 7, 11, 13, 17, 19};
    
    private void check(int[] system, int[] change, int amount, int min) {
        assertEquals(system.length, change.length);
        int changedAmount = 0;
        int numCoins = 0;
        for (int i = 0; i < change.length; i++) {
            changedAmount += change[i] * system[i];
            numCoins += change[i];
        }
        assertEquals(amount, changedAmount);
        assertEquals(min, numCoins);
    }
    
    @Test
    public void americanSmall() {
        check(american, Coin.makeChange(american, 15), 15, 2);
    }

    @Test
    public void americanMedium() {
        check(american, Coin.makeChange(american, 74), 74, 8);
        check(american, Coin.makeChange(american, 76), 76, 4);
    }
    
    @Test
    public void americanLarge() {
        check(american, Coin.makeChange(american, 99), 99, 9);
    }
    
    @Test
    public void micomiconanSmall() {
        check(micomicona, Coin.makeChange(micomicona, 11), 11, 11);
        check(micomicona, Coin.makeChange(micomicona, 12), 12, 1);
        check(micomicona, Coin.makeChange(micomicona, 13), 13, 1);
        check(micomicona, Coin.makeChange(micomicona, 14), 14, 2);
    }

    @Test
    public void micomiconaMed() {
        check(micomicona, Coin.makeChange(micomicona, 24), 24, 2);
        check(micomicona, Coin.makeChange(micomicona, 25), 25, 2);
    }

    @Test
    public void micomiconanBig() {
        check(micomicona, Coin.makeChange(micomicona, 51), 51, 4);
    }

    @Test
    public void wizardingBig() {
        check(wizarding, Coin.makeChange(wizarding, 1000), 1000, 16);
    }
    
    
    
}
