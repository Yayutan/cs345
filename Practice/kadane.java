import java.util.*;
import java.lang.*;
import java.io.*;

class GFG {
  static class FastReader{
    private BufferedReader br;
    private StringTokenizer st;
    public FastReader(){
      br = new BufferedReader(new InputStreamReader(System.in));
    }

    private String next(){
      if(st == null || !st.hasMoreElements()){
        try{
          st = new StringTokenizer(br.readLine());
        }catch(IOException e){
          e.printStackTrace();
        }
      }
        return st.nextToken();
      }

      private int nextInt(){
        return Integer.parseInt(this.next());
      }

      private String nextLine(){
        String toReturn = "";
        try{
          toReturn = br.readLine();
        }catch(IOException e){
          e.printStackTrace();
        }
        return toReturn;
      }

    }

    public static void main (String[] args) {
      FastReader fr = new FastReader();
      int numTC = fr.nextInt();
      int size;
      for(;numTC > 0; numTC--){
        size = fr.nextInt();
        int[] array =  new int[size];
        for(int i = 0; i < size; i++){
          array[i] = fr.nextInt();
        }
        int[][] matrix = new int[size][size];
        int gap = 0;
        int j;
        for(;gap < size; gap ++){
          for(int i = 0;i < size - gap; i ++){
            j = i + gap;
            if(gap == 0){
              matrix[i][j] = array[i];
            }else{
              int case1 = matrix[i][j-1];
              int case2 = matrix[i+1][j];
              int case3 = (gap == 1) ? case1+case2 : case1+case2-matrix[i+1][j-1];
                if(case1 > case2 && case1 > case3){
                  matrix[i][j] = case1;
                }else if(case2 > case1 && case2 > case3){
                  matrix[i][j] = case2;
                }else{
                  matrix[i][j] = case3;
                }

            }
          }
        }
        System.out.println(matrix[0][size-1]);
      }
    }
  }
