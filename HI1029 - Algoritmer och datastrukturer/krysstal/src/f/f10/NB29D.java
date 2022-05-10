package f.f10;

import java.util.Arrays;

public class NB29D {
    public static void main(String[] args) {
        System.out.println("NB29d - Heap of Integers");
        int[] test = new int[]{9, 1, 2, 8, 3, 1, 1, 4, 6, 2, 7};

        System.out.println(Arrays.toString(test));
        Heap.sort(test);
        System.out.println(Arrays.toString(test));
    }
}
