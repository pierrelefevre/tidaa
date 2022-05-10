package f.f14;

import java.util.Arrays;

public class topDownFib {
    static long[] storage;

    public static void main(String[] args) {
        int n = 69;
        storage = new long[n + 1];
        Arrays.fill(storage, -1);
        System.out.println(fib(n));
    }

    private static long fib(int n) {
        //check storage
        if (storage[n] != -1)
            return storage[n];

        //If not already stored, calculate it
        long val;
        if (n == 0) {
            val = 0;
        } else if (n == 1) {
            val = 1;
        } else {
            val = fib(n - 1) + fib(n - 2);
        }

        //then save it
        storage[n] = val;
        return val;
    }
}
