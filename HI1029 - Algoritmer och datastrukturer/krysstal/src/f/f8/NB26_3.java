package f.f8;

import java.util.Random;

public class NB26_3 {
    public static void main(String[] args) {
        HashTable<Integer, String> tbl = new HashTable<>();

        Random rng = new Random();
        for (int i = 0; i < 10; i++)
            if (i != 7)
                tbl.put(i, "R" + rng.nextInt(100));

        tbl.put(7, "!!!!!");
        tbl.put(7, "?????");
        tbl.put(7, "XXXXX");

        System.out.println(tbl.get(7));
        System.out.println(tbl);
        System.out.println(tbl.remove(7));
        System.out.println(tbl.remove(3));

        for (int i = 10; i < 25; i++) {
            tbl.put(i, "R" + rng.nextInt(100));
        }

        System.out.println(tbl);

        tbl.put(7, "XXXXX");
        System.out.println(tbl);

        tbl.put(7, "?????");
        System.out.println(tbl);
    }
}
