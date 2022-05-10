package f.f1;

import java.util.Arrays;

public class ArrayList2<E> {

    private E[] arr;
    private int currentLength;

    public ArrayList2() {
        arr = (E[]) new Object[16];
    }

    boolean add(E element) {
        if (currentLength == arr.length)
            reallocate();
        arr[currentLength++] = element;
        return true;
    }

    void add(int index, E element) {
        if (index < 0 || index >= arr.length)
            return;
        if (currentLength == arr.length)
            reallocate();
        for (int i = index; i < arr.length; i++)
            arr[i + 1] = arr[i];
        currentLength++;
        arr[index] = element;
    }

    private void reallocate() {
        arr = Arrays.copyOf(arr, arr.length * 2);
    }

    E get(int index) {
        if (index < arr.length && index >= 0)
            return arr[index];
        return null;
    }

    int indexOf(Object o) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (o.equals(arr[i]))
                return i;
        }
        return -1;
    }

    E remove(int index) {
        if (index < 0 || index >= arr.length)
            return null;

        E element = arr[index];
        for (int i = index; i < arr.length - 1; i++)
            arr[i] = arr[i + 1];
        currentLength--;

        return element;
    }

    E set(int index, E element) {
        if (index < 0 || index >= arr.length)
            return null;

        E old = arr[index];
        arr[index] = element;

        return old;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("ArrayList2 {");

        for (int i = 0; i < currentLength; i++)
            if (i == currentLength - 1) {
                str.append(arr[i]);
            } else {
                str.append(arr[i] + ", ");
            }

        str.append("}");
        return str.toString();
    }
}
