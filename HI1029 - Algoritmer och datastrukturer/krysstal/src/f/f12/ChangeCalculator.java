package f.f12;

public class ChangeCalculator {
    public static int[] calculateChange(int target, int[] values) {
        int[] amounts = new int[values.length];
        while (target > 0) {
            int smallestValue = 0;
            for (int i = smallestValue; i < values.length; i++) {
                if (target >= values[i]) {
                    target -= values[i];
                    amounts[i]++;
                    smallestValue = i;
                    break;
                }
            }
        }
        return amounts;
    }

}
