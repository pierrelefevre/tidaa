package f.f14;

public class bottomUpFib {
    public static void main(String[] args) {
        int current = 1;
        int lastFib = 1;
        int lastLastFib = 1;

        for (int i = 2; i < 9; i++) {
            current = lastFib + lastLastFib;
            lastLastFib = lastFib;
            lastFib = current;
        }

        System.out.println(current);
    }
}
