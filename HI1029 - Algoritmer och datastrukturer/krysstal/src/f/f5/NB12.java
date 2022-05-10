package f.f5;

public class NB12 {
    public static void main(String[] args) {
        System.out.println("2 ^ 15 = " + powRec(2, 15));
        System.out.println("2 ^ 15 = " + powIter(2, 15));
        //32768
    }

    public static int powRec(int x, int n) {
        if (n == 1)
            return x;
        return x * powRec(x, n - 1);
    }

    public static int powIter(int x, int n) {
        int a = x;
        for (int i = 1; i < n; i++) {
            a *= x;
        }
        return a;
    }
}