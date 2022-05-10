package f.f9;

import java.util.Arrays;
import java.util.Random;

public class Tester {
    private static Random rng;
    private static int size;
    private static int[] a;

    public static void main(String[] args) {
        System.out.println("Int array sort");
        rng = new Random();

        size = 25;

        System.out.println("\nSelection sort");
        a = generate();
        System.out.println(Arrays.toString(a));
        System.out.println(Arrays.toString(ArraySort.selectionSort(a)));

        System.out.println("\nInsertion sort");
        a = generate();
        System.out.println(Arrays.toString(a));
        System.out.println(Arrays.toString(ArraySort.insertionSort(a)));

        System.out.println("\nShell sort");
        a = generate();
        System.out.println(Arrays.toString(a));
        System.out.println(Arrays.toString(ArraySort.shellSort(a)));

        System.out.println("\nMerge sort");
        a = generate();
        System.out.println(Arrays.toString(a));
        System.out.println(Arrays.toString(ArraySort.mergeSort(a)));

        System.out.println("\nQuick sort");
        a = generate();
        System.out.println(Arrays.toString(a));
        System.out.println(Arrays.toString(ArraySort.quickSort(a)));
    }

    private static int[] generate() {
        int[] a = new int[size];
        for (int i = 0; i < size; i++) {
            a[i] = rng.nextInt(100);
        }
        return a;
    }
}
