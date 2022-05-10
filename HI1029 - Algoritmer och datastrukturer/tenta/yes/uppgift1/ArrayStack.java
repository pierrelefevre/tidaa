package uppgift1;

import java.util.Arrays;
import java.util.EmptyStackException;

public class ArrayStack<E> implements Stack<E> {

    E[] data;
    int size;

    public ArrayStack() {
        this.data = (E[]) new Object[2];
    }

    @Override
    public E push(E obj) {
        if (size == data.length)
            reallocate();

        data[size++] = obj;

        return obj;
    }

    private void reallocate() {
        data = Arrays.copyOf(data, data.length * 2);
    }

    @Override
    public E peek() {
        if (empty()) throw new EmptyStackException();
        return data[size - 1];
    }

    @Override
    public E pop() {
        if (empty()) throw new EmptyStackException();
        return data[--size];
    }

    @Override
    public boolean empty() {
        return size == 0;
    }
}
