package f.f5;

import java.util.Random;

public class NB11 {
    public static void main(String[] args) {
        int[] items = new int[5];
        Random rand = new Random();
        for (int i = 0; i < items.length; i++) {
            items[i] = rand.nextInt(10);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < items.length - 1; i++)
            sb.append(items[i] + ", ");
        sb.append(items[items.length - 1] + "]");
        System.out.println(sb.toString());
        System.out.println(largest(items));
    }

    public static int largest(int[] items) {
        return findLargest(items, items.length);
    }

    public static int findLargest(int[] items, int length) {
        if (length == 1)
            return items[0];

        return Math.max(items[length - 1], findLargest(items, length - 1));
    }
}