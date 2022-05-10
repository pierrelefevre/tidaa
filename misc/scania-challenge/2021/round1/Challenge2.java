package round1;

import java.util.*;

public class Challenge2 {
    public static void main(String[] args) {

        // Init array
        int arr[] = new int[] { 1, 9, 14, 12, 8, 4, 13, 15, 7, 18, 17, 2, 11, 10, 6, 31, 54, 77, 3, 0, 5, 16 };
        int sorted[] = Arrays.copyOf(arr, arr.length);
        Arrays.sort(sorted);

        System.out.println(Arrays.toString(arr));
        System.out.println(Arrays.toString(sorted));

        int index = 12;
        // Do first swap
        int swaps = 1;
        arr[12] = arr[11];
        arr[11] = 11;

        while (Arrays.compare(arr, sorted) != 0) {
            swaps++;
            int temp = arr[index];
            int i = indexOf(sorted, arr[index]);
            arr[index] = arr[i];
            arr[i] = temp;
            System.out.println(Arrays.toString(arr));
        }

        System.out.println("Swaps: " + swaps);
    }

    public static int indexOf(int arr[], int search) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == search)
                return i;
        }
        return -1;
    }
}
