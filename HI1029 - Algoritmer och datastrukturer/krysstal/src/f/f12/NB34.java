package f.f12;

import java.util.Arrays;

public class NB34 {
    public static void main(String[] args) {
        System.out.println("NB34 - Values");
        System.out.println(Arrays.toString(ChangeCalculator.calculateChange(789, new int[]{1000, 500, 100, 50, 20, 10, 5, 1})));
    }
}
