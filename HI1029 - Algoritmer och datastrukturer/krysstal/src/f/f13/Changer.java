package f.f13;

import java.util.Arrays;

public class Changer {
    public static int[] change(int[] values, int amount) {
        return change(values, amount, 0);
    }

    public static int[] change(int[] values, int amount, int from) {
        if (amount < 0) return null;
        if (amount == 0) return new int[values.length];

        int[] bestChange = new int[values.length];
        int bestAmount = Integer.MAX_VALUE;
        for (int i = from; i < values.length; i++) {
            int[] result = change(values, amount - values[i], i);
            if (result == null) continue;
            result[i]++;
            int sum = Arrays.stream(result).sum();
            if (sum < bestAmount) {
                bestChange = result;
                bestAmount = sum;
            }
        }

        return bestChange;
    }
}
