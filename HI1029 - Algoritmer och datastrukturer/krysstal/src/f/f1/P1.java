package f.f1;

import java.util.ArrayList;

public class P1 {

    public static void main(String[] args) {
        System.out.println("P1 - Replace demo");
        ArrayList<String> data = new ArrayList<>();
        data.add("Hej");
        data.add("Hejsan");
        data.add("Hejdå");
        data.add("Hej");
        data.add("Hejsan");
        data.add("Hejdå");
        data.add("Hej");

        System.out.println(data);

        replace(data, "Hej", "Hej!!");

        System.out.println(data);

    }

    public static void replace(ArrayList<String> alist, String oldItem, String newItem){
        for(int i = 0; i < alist.size(); i++){
            if(alist.get(i).equals(oldItem))
                alist.set(i, newItem);
        }
    }

}
