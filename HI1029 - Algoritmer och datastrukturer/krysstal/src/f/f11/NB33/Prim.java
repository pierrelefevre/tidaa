/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package f.f11.NB33;


import java.util.Arrays;
import java.util.BitSet;

/**
 * @author bfelt
 */

public class Prim {

    public static void main(String[] args) {
        int X = Integer.MAX_VALUE;
        int[][] w = {
                {X, 2, X, X, X, 1, X, X},
                {2, X, 2, 2, 4, X, X, X},
                {X, 2, X, X, 3, X, X, 1},
                {X, 2, X, X, 4, 1, X, X},
                {X, 4, 3, 4, X, X, 7, X},
                {1, X, X, 1, X, X, 5, X},
                {X, X, X, X, 7, 5, X, 6},
                {X, X, 1, X, X, X, 6, X}};
        int[] p = prim(w);
        System.out.println("Node A var startnode");
        for (int i = 1; i < p.length; i++)
            System.out.println("Nod " + (char) (i + 'A') + " anslöts via " + (char) (p[i] + 'A'));
        int distance = 0;
        for (int i = 1; i < w.length; i++)
            distance += w[i][p[i]];
        System.out.println("Totalvikt: " + distance);
    }

    public static int minSpanTree(int[][] w, int[] connections) {
        int[] p = prim(w);

        System.arraycopy(p, 0, connections, 0, connections.length);

        int distance = 0;
        for (int i = 1; i < w.length; i++)
            distance += w[i][p[i]];

        return distance;
    }

    // w - förbindelsematris, prim returnerar en array p, där p[i] är den nod som noden i anslöts till (i>0)
    private static int[] prim(int[][] w) {
        BitSet visited = new BitSet(w.length);
        int[] p = new int[w.length];
        int[] cost = new int[w.length];
        Arrays.fill(cost, Integer.MAX_VALUE);

        int i = 0;
        while (i != -1) {
            visited.set(i);

            for (int j = 0; j < w.length; j++) {
                if (w[i][j] < cost[j] && !visited.get(j)) {
                    cost[j] = w[i][j];
                    p[j] = i;
                }
            }

            //get smallest cost
            int smallest = Integer.MAX_VALUE;
            int smallestIndex = -1;
            for (int j = 0; j < w.length; j++) {
                if (cost[j] < smallest && !visited.get(j)) {
                    smallest = cost[j];
                    smallestIndex = j;
                }
            }
            i = smallestIndex;
        }

        return p;
    }
}
