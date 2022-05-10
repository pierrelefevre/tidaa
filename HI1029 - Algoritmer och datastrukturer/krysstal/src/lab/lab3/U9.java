package lab.lab3;

import java.util.Random;

public class U9 {
    public static void main(String[] args) {
        System.out.println("Lab uppgift 9");
        Random rng = new Random();
        int[] numbers = new int[1000000];
        for (int i = 0; i < numbers.length; i++)
            numbers[i] = rng.nextInt(numbers.length);

        long start = System.nanoTime();
        Sort.radix(numbers);
        long stop = System.nanoTime();
        System.out.println("Time: " + ((stop - start) / 1000000) + "ms");

        for (int i = 0; i < numbers.length - 1; i++) {
            if (numbers[i] > numbers[i + 1]) {
                System.out.println("Not properly sorted!");
                return;
            }
        }
        System.out.println("Sorted ok!");
    }
}
