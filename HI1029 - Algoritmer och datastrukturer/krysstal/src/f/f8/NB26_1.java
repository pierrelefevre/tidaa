package f.f8;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class NB26_1 {
    public static void main(String[] args) {
        String[] strings = new String[100];
        Random rng = new Random();

        for (int i = 0; i < 100; i++)
            strings[i] = Integer.toString(rng.nextInt(10));
        HashMap<String, Integer> map = new HashMap<>();

        for (var string : strings) {
            if (map.containsKey(string)) {
                map.put(string, map.get(string) + 1);
            } else {
                map.put(string, 1);
            }
        }


        System.out.println(map);
        System.out.println("Most occurences: " + map.entrySet().stream().max(Map.Entry.comparingByValue()).get().getValue());

    }
}