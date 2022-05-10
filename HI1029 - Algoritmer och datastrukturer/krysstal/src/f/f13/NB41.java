package f.f13;

import java.util.Arrays;
import java.util.Random;

public class NB41 {
    public static void main(String[] args) {
        System.out.println("NB41 - Max sequence sum");
        Random rng = new Random();

        int[] numbers = new int[20];

        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = rng.nextInt(2000) - 1000;
        }

        //int[] numbers = new int[]{24, 35, -87, 21, 80, -12, 32, -90, 45, 10};

        System.out.println(Arrays.toString(numbers));
        System.out.println("Largest sequence: " + Sequencer.largestSequence(numbers));
        System.out.println("Called " + Sequencer.calls + " times.");
    }
}
