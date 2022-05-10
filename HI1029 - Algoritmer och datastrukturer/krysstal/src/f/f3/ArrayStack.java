package f.f3;

import java.util.ArrayList;

public class ArrayStack<E> implements StackInt<E> {
    private ArrayList<E> data;

    public ArrayStack() {
        data = new ArrayList<>();
    }

    @Override
    public E push(E obj) {
        data.add(obj);
        return obj;
    }

    @Override
    public E peek() {
        return data.get(data.size() - 1);
    }

    @Override
    public E pop() {
        if (data.size() > 0)
            return data.remove(data.size() - 1);
        return null;
    }

    @Override
    public boolean empty() {
        return data.size() == 0;
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
