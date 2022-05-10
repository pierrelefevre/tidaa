package f.f12;

import java.util.ArrayList;
import java.util.Random;

public class NB40 {
    public static void main(String[] args) {
        System.out.println("NB40 - Number line");
        Random rng = new Random();

        var numbers = new ArrayList<Double>();

        for (int i = 0; i < 10; i++) {
            numbers.add(rng.nextDouble() * 10);
        }

        System.out.println("Minimum steps of 2: " + NumberLine.steps(numbers));
    }
}
