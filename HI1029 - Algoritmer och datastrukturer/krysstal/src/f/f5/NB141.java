package f.f5;

public class NB141 {
    public static void main(String[] args) {
        System.out.println(Integer.toString(swap(5, 1, 3, 0)));
        System.out.println(Integer.toString(swap(2, 1, 3, 0)));
        System.out.println(Integer.toString(swap(22, 22, 13, 0)));
    }

    private static int swap(int b, int w, int r, int swaps) {
        if (swaps > 15)
            return swaps;

        if (b == w && w == r)
            return 0;

        int checkB = swap(b - 1, w + 3, r + 1, swaps + 1);
        int checkW = swap(b + 3, w - 1, r + 4, swaps + 1);
        int checkR = swap(b + 2, w + 1, r - 1, swaps + 1);

        return Math.min(checkB, Math.min(checkW, checkR)) + 1;
    }

}
