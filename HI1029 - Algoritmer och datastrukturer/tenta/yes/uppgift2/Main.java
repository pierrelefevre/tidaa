/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uppgift2;

import java.util.Arrays;

/**
 * @author bfelt
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int[][] a = {{1, 3, 4, 7, 8, 3, 1},
                {2, 6, 2, 4, 6, 1, 6},
                {1, 7, 1, 2, 1, 5, 1},
                {1, 6, 1, 5, 1, 8, 2},
                {1, 1, 8, 8, 1, 9, 1}};

        System.out.println(findSmallestSum(a));
    }

    public static int findSmallestSum(int[][] a) {
        int[][] dp = new int[a.length][a[0].length];
        for (int[] row : dp) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        return findSmallestSum(a, dp, 0, 0);
    }

    public static int findSmallestSum(int[][] a, int[][] dp, int row, int col) {
        if (row > a.length - 1 || col > a[0].length - 1)
            return Integer.MAX_VALUE/2;

        if (dp[row][col] != Integer.MAX_VALUE)
            return dp[row][col];

        if (row == a.length - 1 && col == a[0].length - 1)
            return a[row][col];

        int right = a[row][col] + findSmallestSum(a, dp, row, col + 1);
        int down = a[row][col] + findSmallestSum(a, dp, row + 1, col);

        dp[row][col] = Math.min(right, down);
        return Math.min(right, down);
    }

}
