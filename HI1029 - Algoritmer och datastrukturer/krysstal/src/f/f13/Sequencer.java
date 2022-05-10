package f.f13;

public class Sequencer {
    static int calls = 0;

    public static int largestSequence(int[] numbers) {
        return largestSequence(numbers, 0, numbers.length - 1);
    }

    public static int largestSequence(int[] numbers, int left, int right) {
        calls++;
        if (left > right) return 0;
        if (left == right) return Math.max(0, numbers[left]);
        int center = (left + right) / 2;
        int sum = 0, maxLeft = 0;
        for (int i = center; i >= left; i--) {
            sum += numbers[i];
            maxLeft = Math.max(maxLeft, sum);
        }
        sum = 0;
        int maxRight = 0;
        for (int i = center + 1; i < numbers.length; i++) {
            sum += numbers[i];
            maxRight = Math.max(maxRight, sum);
        }
        int maxMiddle = maxLeft + maxRight;
        return Math.max(maxMiddle, Math.max(largestSequence(numbers, left, center), largestSequence(numbers, center + 1, right)));
    }
}
