package f.f10;

public class Heap {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    //Heap in array
    // LEFT CHILD = 2p+1
    // RIGHT CHILD = 2p+2
    // PARENT = (p-1)/2


    private static void swap(int[] data, int a, int b) {
        int tmp = data[a];
        data[a] = data[b];
        data[b] = tmp;
    }

    private static void reOrder(int[] data, int index, int size) {
        if (2 * index + 1 > size || 2 * index + 2 > size)
            return;

        int highestIndex = 2 * index + 1;
        if (data[highestIndex] < (data[2 * index + 2]))
            highestIndex = 2 * index + 2;

        if (data[highestIndex] <= data[index])
            return;

        swap(data, index, highestIndex);

        reOrder(data, highestIndex, size);
    }

    public static int poll(int[] data, int size) {
        int val = data[0];

        data[0] = data[size];

        reOrder(data, 0, size - 1);

        return val;
    }

    public static void offer(int[] data, int index) {
        while (index > 0 && data[index] > data[(index - 1) / 2]) {
            swap(data, index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    public static int[] sort(int[] data) {
        int size = 1;
        while (size < data.length) {
            offer(data, size++);
            printList(data, size);
        }
        while (size > 1) {
            data[--size] = poll(data, size);
            printList(data, size);
        }

        return data;
    }

    private static void printList(int[] a, int size) {
        System.out.print("[" + ANSI_GREEN);
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i]);
            if (i == size - 1)
                System.out.print(ANSI_YELLOW + " | " + ANSI_RED);
            else if (i == a.length - 1)
                System.out.print(ANSI_RESET + "]");
            else
                System.out.print(", ");
        }
        System.out.println(ANSI_RESET);
    }
}
