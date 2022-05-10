package f.f14;

import java.util.Arrays;

public class nb46 {
    static int stupid;
    static int dynamic;
    static int bottomUp;

    public static void main(String[] args) {
        System.out.println("NB46");
        stupid = 0;
        dynamic = 0;

//        int[][] triangle = new int[][]{{7}, {3, 8}, {8, 1, 0}, {2, 7, 4, 4}, {4, 5, 2, 6, 5}};
        int[][] triangle = new int[][]{{7}, {3, 8}, {8, 1, 0}, {2, 7, 4, 4}, {4, 5, 2, 6, 5}, {5, 3, 5, 6, 8, 1}, {3, 2, 6, 5, 4, 9, 3}, {9, 1, 0, 2, 3, 8, 4, 6}, {4, 5, 2, 6, 5, 4, 5, 2, 6}, {9, 1, 0, 2, 3, 8, 4, 6, 6, 1}};

        for (int[] level : triangle) {
            System.out.println(Arrays.toString(level) + " " + level.length);
        }

        long time1 = System.nanoTime();
        System.out.println("Stupid   " + sum(triangle) + " my dude 🦍");
        long time2 = System.nanoTime();
        System.out.println("Dynamic  " + sumDyn(triangle) + " my dude 🦍");
        long time3 = System.nanoTime();
        System.out.println("BottomUp " + sumBottomUp(triangle) + " my dude 🦍");
        long time4 = System.nanoTime();

        System.out.println("Stupid: " + stupid + " " + (time2 - time1) / 1000 + "us");
        System.out.println("Dynamic: " + dynamic + " " + (time3 - time2) / 1000 + "us");
        System.out.println("BottomUp:" + bottomUp + " " + (time4 - time3) / 1000 + "us");
    }


    public static int sumBottomUp(int[][] triangle) {
        bottomUp++;

        int[][] dp = new int[triangle.length][triangle.length];
        for (int i = 0; i < triangle.length; i++)
            Arrays.fill(dp[i], Integer.MIN_VALUE);

        for (int i = 0; i < triangle.length; i++)
            dp[triangle.length - 1][i] = triangle[triangle.length - 1][i];

        for (int level = triangle.length - 2; level >= 0; level--) {
            for (int index = 0; index < level + 1; index++) {
                dp[level][index] = Math.max(dp[level + 1][index], dp[level + 1][index + 1]) + triangle[level][index];
            }
        }

        return dp[0][0];
    }

    public static int sumDyn(int[][] triangle) {
        int[][] dp = new int[triangle.length][triangle.length];
        for (int i = 0; i < triangle.length; i++)
            Arrays.fill(dp[i], Integer.MIN_VALUE);

        return sumDyn(triangle, dp, 0, 0);
    }

    public static int sumDyn(int[][] triangle, int[][] dp, int level, int index) {
        dynamic++;
        if (level == triangle.length - 1)
            return triangle[level][index];

        int left = triangle[level][index];
        int right = triangle[level][index];

        //SKIP THE BIG CURZ
        if (dp[level][index] != Integer.MIN_VALUE)
            return dp[level][index];

        //THE BIG CURSE
        left += sumDyn(triangle, dp, level + 1, index);
        right += sumDyn(triangle, dp, level + 1, index + 1);

        //Cache the best value
        dp[level][index] = Math.max(left, right);
        return Math.max(left, right);
    }

    public static int sum(int[][] triangle) {
        return sum(triangle, 0, 0);
    }

    public static int sum(int[][] triangle, int level, int index) {
        stupid++;
        if (level == triangle.length - 1)
            return triangle[level][index];

        int left = triangle[level][index];
        int right = triangle[level][index];

        left += sum(triangle, level + 1, index);
        right += sum(triangle, level + 1, index + 1);

        return Math.max(left, right);
    }
}