package f.f2;

public class IntList {

    private int[] arr;
    private int currentLength;

    public IntList(int initialCapacity) {
        arr = (int[]) new int[initialCapacity];
    }

    boolean add(int element) {
        if (currentLength == arr.length)
            reallocate();
        arr[currentLength++] = element;
        return true;
    }

    void add(int index, int element) {
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
        int[] newArr = new int[arr.length * 2];
        for(int i = 0; i < currentLength; i++){
            newArr[i] = arr[i];
        }
        arr = newArr;
    }

    int get(int index) {
        if (index < arr.length && index >= 0)
            return arr[index];
        return 0;
    }

    int indexOf(int searchFor) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (searchFor == arr[i])
                return i;
        }
        return -1;
    }

    int remove(int index) {
        if (index < 0 || index >= arr.length)
            return -1;

        int element = arr[index];
        for (int i = index; i < arr.length - 1; i++)
            arr[i] = arr[i + 1];
        currentLength--;

        return element;
    }

    int set(int index, int element) {
        if (index < 0 || index >= arr.length)
            return -1;

        int old = arr[index];
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

    public int size(){
        return currentLength;
    }
}
