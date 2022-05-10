package f.f5;

import java.util.Random;

public class NB14 {
    public static void main(String[] args) {
        Random rand = new Random();

        for (int i = 0; i < 10; i++) {
            int target = rand.nextInt(500);
            String result = Integer.toString(lowest(target, 1));

            if (result.equals("-1")) {
                result = "Unreachable";
            } else {
                result = "Lowest: " + result;
            }
            System.out.println("Target: " + target + "\t" + result);
        }
        System.out.println();
        System.out.println("Target: " + 109 + "\tLowest: " + lowest(109, 1));
    }

    private static int lowest(int target, int current) {
        if (current > target)
            return -1;
        if (current == target)
            return 0;

        int mult = lowest(target, current * 3);
        int add = lowest(target, current + 4);

        if (mult == -1 && add == -1)
            return -1;
        if (mult == -1)
            return add + 5;
        if (add == -1)
            return mult + 10;

        return Math.min(mult + 10, add + 5);
    }


}
