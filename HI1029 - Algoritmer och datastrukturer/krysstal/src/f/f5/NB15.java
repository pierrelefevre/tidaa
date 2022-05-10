package f.f5;

public class NB15 {
    public static void main(String[] args) {
        System.out.println(bToI("1000000001", 0));
        System.out.println(iToB(513));
    }

    public static int bToI(String bin, int index) {
        if (index >= bin.length())
            return 0;

        char c = bin.charAt(index);

        if (c == '1')
            return (int) Math.pow(2, bin.length() - index - 1) + bToI(bin, index + 1);
        return bToI(bin, index + 1);
    }

    public static String iToB(int dec) {
        int pow = 0;
        while (Math.pow(2, pow) < dec) {
            pow++;
        }
        return convert(dec, --pow);
    }

    public static String convert(int dec, int pow) {
        if (pow < 0)
            return "";

        int test = (int) Math.pow(2, pow);
        if (dec - test < 0) {
            return "0" + convert(dec, pow - 1);
        } else {
            return "1" + convert(dec - test, pow - 1);
        }
    }
}