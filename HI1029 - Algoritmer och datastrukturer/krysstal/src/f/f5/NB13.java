package f.f5;

public class NB13 {
    public static void main(String[] args) {
        System.out.println("sqrt 16 = " + sqrt(16, 1, .0001));
        System.out.println("sqrt 17 = " + sqrt(17, 1, .0001));
        System.out.println("sqrt 250 = " + sqrt(250, 1, .0001));
        //32768
    }

    public static double sqrt(double n, double a, double e) {
        if (Math.abs(Math.pow(a, 2) - n) < e)
            return a;
        return sqrt(n, ((Math.pow(a, 2) + n) / (2 * a)), e);
    }

}
