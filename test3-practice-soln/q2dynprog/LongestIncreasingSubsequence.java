package q2dynprog;

public class LongestIncreasingSubsequence {

    public static int longIncrSubsq(int[] A) {
        int[] L = new int[A.length];
        for (int i = L.length - 1; i >= 0;i--) {
            L[i] = 1;
            for (int j = L.length - 1; j > i; j--)
                if (A[j] > A[i] && L[j] >= L[i])
                    L[i] = L[j] + 1;
        }
        int max = L[0];
        for (int i = 1; i < L.length; i++)
            if (L[i] > max)
                max = L[i];
        return max;
    }
}
