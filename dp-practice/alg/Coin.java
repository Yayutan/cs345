package alg;

/**
 * Coin
 * 
 * Placeholder for solution to the coin-changing problem:
 * 
 * Given a currency system represented as an array of increasing
 * coin denomination values (for example, the US system of pennies,
 * nickels, dimes, and quarters would be [1, 5, 10, 25]) and an amount, 
 * return a set of coins whose value sums to the given amount but is
 * minimal in terms of number of coins. 
 * 
 * @author Thomas VanDrunen
 * CSCI 345, Wheaton College
 * July 13, 2015
 */
public class Coin {

	public static int[] makeChangeUS(int amount) {
		int[] coinSet = new int[4];
		coinSet[3] = amount / 25; // quarters
		amount %= 25;  // remaining amount after quarters
		coinSet[2] = amount / 10; // dimes
		amount %= 10;  // remaining amount after dimes
		coinSet[1] = amount / 5;  // nickels
		amount %= 5;   // remaining amount after nickels
		coinSet[0] = amount;   // pennies;
		return coinSet;
	}

	/**
	 * Compute the minimum set of coins to make change for a given
	 * amount in a given currency system.
	 * @param denomVals An array containing the values of the coins
	 * in the currency system from least to greatest; the least valued
	 * coin must be worth one unit (denomVals[0] == 1).
	 * @param amount The amount of money to make change for.
	 * @return An array parallel to the given denomVals indicating
	 * the number of each kind of coin in a minimal set of coins
	 * summing to the given amount.
	 */
	public static int[] makeChangeNaiveWrong(int[] denomVals, int amount) {
		int[] coinSet = new int[denomVals.length];
		for (int i = denomVals.length - 1; i > 0; i--) {
			coinSet[i] = amount / denomVals[i];
			amount %= denomVals[i];
		}
		coinSet[0] = amount;
		return coinSet;
	}

	/**
	 * Compute the minimum set of coins to make change for a given
	 * amount in a given currency system.
	 * @param denomVals An array containing the values of the coins
	 * in the currency system from least to greatest; the least valued
	 * coin must be worth one unit (denomVals[0] == 1).
	 * @param amount The amount of money to make change for.
	 * @return An array parallel to the given denomVals indicating
	 * the number of each kind of coin in a minimal set of coins
	 * summing to the given amount.
	 */
	public static int[] makeChangeNaiveSlow(int[] denomVals, int amount) {
		return makeChangeNaiveHelper(denomVals, amount, denomVals.length);
	}

	public static int[] makeChangeNaiveHelper(int[] denomVals, int amount, int denomBound) {
		if (denomBound == 1) {
			int[] toReturn = new int[denomVals.length];
			toReturn[0] = amount;
			return toReturn;
		}
		else {
			int[] bestSet = null;
			int fewestCoins = Integer.MAX_VALUE;
			for (int i = 0; i * denomVals[denomBound-1] <= amount; i++) {
				int[] setHere = makeChangeNaiveHelper(denomVals, amount - i * denomVals[denomBound-1], denomBound-1);
				setHere[denomBound-1] = i;
				int coinsHere = 0;
				for (int x: setHere) coinsHere += x;
				if (coinsHere < fewestCoins) {
					fewestCoins = coinsHere;
					bestSet = setHere;
				}
			}
			return bestSet;
		}
	}

	/**
	 * Compute the minimum set of coins to make change for a given
	 * amount in a given currency system.
	 * @param denomVals An array containing the values of the coins
	 * in the currency system from least to greatest; the least valued
	 * coin must be worth one unit (denomVals[0] == 1).
	 * @param amount The amount of money to make change for.
	 * @return An array parallel to the given denomVals indicating
	 * the number of each kind of coin in a minimal set of coins
	 * summing to the given amount.
	 */
	public static int[] makeChange(int[] denomVals, int amount) {
		assert denomVals[0] == 1;

		// The number of coins of to take of a given denomination
		// when making change for a given amount.
		// takeTable[i][j] means the number of coins of denomination
		// j (of value denomVals[j]) would be included in a minimal
		// set of coins summing to i but using only coins through
		// denomination j.
		int[][] takeTable = new int[amount+1][denomVals.length];

		// The fewest number of coins needed to make a given amount
		// using coins of a given denomination and less.
		// fewestTable[i] means the total number of coins that would
		// be included in a minimal set of coins summing to i but using 
		// only coins through denomination j.
		int[][] fewestTable = new int[amount+1][denomVals.length];


		// -----------------------------------
		// TODO: populate the takeTable and the fewestTable
		// -----------------------------------

		for(int i = 0; i <= amount; i++){
			takeTable[i][0] = i;
			fewestTable[i][0] = i;
			for(int j = 1; j < denomVals.length; j++){
				if(i == 0){
				takeTable[0][j] = 0;
				fewestTable[0][j] = 0;
				continue;
				}
				
				int minSoFar = 0;
				int max = i / denomVals[j];
				int current;
				int minTake = fewestTable[i][j-1];
				for(int k = 0; k <= max; k++){
					current = k + fewestTable[i - (k * denomVals[j])][j - 1];
					if(current < minTake){
						minSoFar = k;
						minTake = current;
					}
				}
				takeTable[i][j] = minSoFar;
				fewestTable[i][j] = minTake;

			}
		}





		// array containing our answer
		int[] result = new int[denomVals.length];
		int monLeft = amount;
		for(int m = result.length - 1; m >= 0; m--){
			result[m] = takeTable[monLeft][m];
			monLeft -= result[m] * denomVals[m];
		}
		
		// -----------------------------------
		// TODO: populate the result array
		// -----------------------------------


		return result;
	}

}
