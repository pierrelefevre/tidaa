package quicksort;

public class Sort {
    public static void sort(int[] input) {
        sort(input, 0, input.length - 1);
    }

    private static void sort(int[] input, int start, int end) {
        if (start < end) {
            int p = partition(input, start, end);

            //Split around partition, exclude pivot element
            sort(input, start, p - 1);
            sort(input, p + 1, end);
        }
    }

    private static int partition(int[] input, int start, int end) {
        int pivot = input[end];
        int i = start;

        for (int j = start; j < end; j++)
            if (input[j] < pivot) {
                int tmp = input[i];
                input[i] = input[j];
                input[j] = tmp;
                i++;
            }

        int tmp = input[end];
        input[end] = input[i];
        input[i] = tmp;

        return i;
    }
}
