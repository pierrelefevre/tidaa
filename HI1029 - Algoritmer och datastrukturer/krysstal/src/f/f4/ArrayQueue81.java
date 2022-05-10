package f.f4;

import java.util.Arrays;

public class ArrayQueue81<E> {
    private int front, rear, size, maxSize;
    private E[] data;

    public ArrayQueue81(int initialSize) {
        size = 0;
        front = 0;
        maxSize = initialSize;
        rear = 0;
        data = (E[]) new Object[maxSize];
    }

    public boolean offer(E element) {
        if (size == maxSize)
            reallocate();
        data[rear] = element;
        rear = (rear + 1) % maxSize;         //lägger till parantes

        size++;
        return true;
    }

    public E peek() {
        if (size == 0) return null;
        return data[front];
    }

    public E poll() {
        if (size == 0) {
            return null;
        } else {
            size--;
            E element = data[front];
            data[front] = null; // för debug
            front = (front + 1) % maxSize; // front = front + 1 % maxSize;

            return element;
        }
    }

    /*
    private void reallocate() {
        maxSize *= 2;
        //HELT FEL! Läggs om fel vilket innebär att element som kan ha varit sist hamnar nu först
    data =Arrays.copyOf(data,maxSize);
    rear =size;
    }*/

    private void reallocate() {
        int newMaxSize = 2 * maxSize;
        E[] newData = (E[]) new Object[newMaxSize];
        int j = front;
        for (int i = 0; i < size; i++) {
            newData[i] = data[j];
            j = (j + 1) % maxSize;
        }
        front = 0;
        rear = size;
        maxSize = newMaxSize;
        data = newData;
    }

    @Override
    public String toString() {
        String raw = "[SIZE " + size + "]\n" + Arrays.toString(data) + "\n";
        String arr = "{";
        for (int i = front; i < rear; i++) {
            arr += data[i] + ", ";
        }
        arr += "}";
        return raw + arr;
    }
}