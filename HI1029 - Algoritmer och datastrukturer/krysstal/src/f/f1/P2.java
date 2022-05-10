package f.f1;

import java.util.ArrayList;

public class P2 {

    public static void main(String[] args) {
        System.out.println("P2 - ArrayList2 Demo");
        ArrayList<String> data = new ArrayList<>();
        data.add("Hej");
        data.add("Hejsan");
        data.add("Hejdå");
        data.add("Hej");
        data.add("Hejsan");
        data.add("Hejdå");
        data.add("Hej");

        System.out.println(data);

        delete(data, "Hej");

        System.out.println(data);

    }

    public static void delete(ArrayList<String> alist, String target) {
        for (int i = 0; i < alist.size(); i++) {
            if (alist.get(i).equals(target)) {
                alist.remove(i);
                return;
            }
        }
    }

}
