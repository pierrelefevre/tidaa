package f.f5;

public class BinarySearch {
    private static int binarySearch(Object[] items, Comparable target, int first, int last) {
        if (first > last) {
            return -1; // Base case for unsuccessful search.
        } else {
            int middle = (first + last) / 2; // Next probe index.
            int compResult = target.compareTo(items[middle]);
            if (compResult == 0) {
                return middle; // Base case for successful search.
            } else if (compResult < 0) {
                return binarySearch(items, target, first, middle - 1);
            } else {
                return binarySearch(items, target, middle + 1, last);
            }
        }
    }

    public static int binarySearch(Object[] items, Comparable target) {
        return binarySearch(items, target, 0, items.length - 1);
    }

    public static void main(String[] args) {
        String[] items = new String[5];
        items[0] = "a";
        items[1] = "b";
        items[2] = "c";
        items[3] = "d";
        items[4] = "e";

        System.out.println(binarySearch(items, "d"));
    }

}
