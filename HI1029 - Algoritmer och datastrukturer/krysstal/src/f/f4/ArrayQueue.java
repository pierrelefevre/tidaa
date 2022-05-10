package f.f4;

public class ArrayQueue<E> {
    private int front, rear, size, maxSize;
    private E[] data;

    public ArrayQueue(int initialMaxSize) {
        size = 0;
        front = 0;
        maxSize = initialMaxSize;
        rear = maxSize - 1;
        data = (E[]) new Object[maxSize];
    }

    public int getSize() {
        return size;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public boolean offer(E element) throws Exception {
        if (size == maxSize) {
            reallocate(maxSize * 2);
        }
        rear = (rear + 1) % maxSize;
        data[rear] = element;
        size++;
        return true;
    }

    public E peek() {
        if (size == 0) return null;
        return data[front];
    }

    public E poll() throws Exception {
        if (size == 0) {
            return null;
        } else {
            size--;
            E element = data[front];
            front = (front + 1) % maxSize;
            if (size < maxSize / 4) {
                reallocate(maxSize / 2);
            }
            return element;
        }
    }

    private void reallocate(int newMaxSize) throws Exception {
        if (newMaxSize < size)
            throw new Exception("Cannot shrink beyond current size.");
        E[] newData = (E[]) new Object[newMaxSize];
        int j = front;
        for (int i = 0; i < size; i++) {
            newData[i] = data[j];
            j = (j + 1) % maxSize;
        }
        front = 0;
        rear = size - 1;
        maxSize = newMaxSize;
        data = newData;
    }
}