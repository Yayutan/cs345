package q1dynprog;

public class HeroHall {

    public static int bestTreasure(int[] s, int[] t, int[] g) {
        int[][] v = new int[s.length][2];
        v[0][0] = s[0];
        v[0][1] = t[0];
        for (int i = 1; i < s.length; i++) {
            v[i][0] = s[i] + (v[i-1][0] > v[i-1][1] - g[i-1]? v[i-1][0] : v[i-1][1] - g[i-1]);
            v[i][1] = t[i] + (v[i-1][1] > v[i-1][0] - g[i-1]? v[i-1][1] : v[i-1][0] - g[i-1]);
        }
        return v[s.length-1][0] > v[s.length-1][1] ? v[s.length-1][0] : v[s.length-1][1];
            
    }
    
}
