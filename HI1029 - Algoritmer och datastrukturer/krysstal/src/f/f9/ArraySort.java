package f.f9;

import java.util.Arrays;

public class ArraySort {

    public static int[] selectionSort(int[] a) {

        for (int i = 0; i < a.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < a.length; j++) {
                if (a[j] < a[minIndex]) {
                    minIndex = j;
                }
            }

            int tmp = a[i];
            a[i] = a[minIndex];
            a[minIndex] = tmp;
        }
        return a;
    }

    public static int[] insertionSort(int[] a) {

        for (int i = 1; i <= a.length - 1; i++) {
            int data = a[i];
            int dataIndex = i;
            while (dataIndex > 0 && data < a[dataIndex - 1]) {
                a[dataIndex] = a[dataIndex - 1];
                dataIndex--;
                a[dataIndex] = data;
            }
        }

        return a;
    }

    public static int[] shellSort(int[] a) {
        int gap = a.length / 2;
        while (gap > 0) {
            for (int i = gap; i < a.length; i++) {
                int data = a[i];
                int dataIndex = i;
                while (dataIndex > gap - 1 && data < a[dataIndex - gap]) {
                    a[dataIndex] = a[dataIndex - gap];
                    dataIndex -= gap;
                }
                a[dataIndex] = data;
            }
            if (gap == 2)
                gap = 1;
            else
                gap = (int) ((double) gap / 2.2);
        }
        return a;
    }

    public static int[] mergeSort(int[] a) {
        if (a.length == 1)
            return a;

        int[] b = Arrays.copyOfRange(a, 0, a.length / 2);
        int[] c = Arrays.copyOfRange(a, a.length / 2, a.length);
        mergeSort(b);
        mergeSort(c);
        merge(b, c, a);
        return a;
    }

    private static int[] merge(int[] a, int[] b, int[] c) {
        int indexA = 0, indexB = 0, indexC = 0;

        while (indexA < a.length && indexB < b.length) {
            if (a[indexA] <= b[indexB]) {
                c[indexC++] = a[indexA];
                indexA++;
            } else {
                c[indexC++] = b[indexB];
                indexB++;
            }
        }
        while (indexA < a.length) {
            c[indexC++] = a[indexA];
            indexA++;
        }
        while (indexB < b.length) {
            c[indexC++] = b[indexB];
            indexB++;
        }

        return a;
    }

    public static int[] quickSort(int[] a) {
        return quickSort(a, 0, a.length - 1);
    }

    private static int[] quickSort(int[] a, int first, int last) {
        if (first < last) {
            int pivIndex = partition(a, first, last);
            //Sort left half
            quickSort(a, first, pivIndex - 1);
            //Sort right half
            quickSort(a, pivIndex + 1, last);
        }
        return a;
    }

    private static int partition(int[] a, int first, int last) {
        int pivot = a[first];

        int up = first;
        int down = last;

        do {
            while (a[up] <= pivot && up < last)
                up++;

            while (a[down] > pivot && down > first)
                down--;

            if (up < down) {
                int tmp = a[up];
                a[up] = a[down];
                a[down] = tmp;
            }

        } while (up < down);

        int tmp = a[first];
        a[first] = a[down];
        a[down] = tmp;

        return down;
    }
}
