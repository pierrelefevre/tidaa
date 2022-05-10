package lab.lab3;

public class Sort {
    public static void radix(int[] numbers) {
        int max = findMax(numbers);
        for (int exp = 1; max / exp > 0; exp *= 10) {
            countSort(numbers, exp);
        }
    }

    private static void countSort(int[] numbers, int exp) {
        int[] buckets = new int[10];
        for (int j : numbers) {
            buckets[j / exp % 10]++;
        }

        int total = 0, tmp;
        for (int i = 0; i < 10; i++) {
            tmp = buckets[i];
            buckets[i] = total;
            total += tmp;
        }

        int[] output = new int[numbers.length];

        for (int number : numbers) {
            output[buckets[number / exp % 10]++] = number;
        }

        System.arraycopy(output, 0, numbers, 0, numbers.length);
    }

    private static int findMax(int[] numbers) {
        int max = numbers[0];
        for (int i : numbers)
            if (i > max) max = i;
        return max;
    }
}
