package quicksort;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.println("Quicksort");
        int[] in = new int[]{92, 4, 6, 6, 3, 7, 8, 52, 2, 5, 8, 9, 6, 42, 5, 8, 4, 6, 53, 467};
        System.out.println(Arrays.toString(in));
        Sort.sort(in);
        System.out.println(Arrays.toString(in));
    }
}
