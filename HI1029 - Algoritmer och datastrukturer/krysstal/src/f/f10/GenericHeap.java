package f.f10;

import java.util.Arrays;

public class GenericHeap<E extends Comparable<E>> {
    E[] data;
    int nextPos;

    public GenericHeap(int size) {
        data = (E[]) new Comparable[size];
        nextPos = 0;
    }


    public void insert(E element) {
        data[nextPos] = element;

        int index = nextPos;
        while (index > 0 && data[index].compareTo(data[(index - 1) / 2]) < 0) {
            swap(index, (index - 1) / 2);
            index = (index - 1) / 2;
        }

        nextPos++;

        if (nextPos == data.length)
            data = Arrays.copyOf(data, data.length * 2);
    }

    // Heap in array
    // LEFT CHILD = 2p+1
    // RIGHT CHILD = 2p+2
    // PARENT = (p-1)/2

    private void swap(int a, int b) {
        E tmp = data[a];
        data[a] = data[b];
        data[b] = tmp;
    }

    private void reOrder(int index) {
        if (2 * index + 2 >= data.length)
            return;
        if (data[2 * index + 1] == null || data[2 * index + 2] == null)
            return;

        int lowestIndex = 2 * index + 1;
        if (data[lowestIndex].compareTo(data[2 * index + 2]) > 0)
            lowestIndex = 2 * index + 2;

        swap(index, lowestIndex);

        reOrder(lowestIndex);
    }

    public E extract() {
        E val = data[0];

        data[0] = data[--nextPos];
        data[nextPos] = null;

        reOrder(0);

        return val;
    }
}