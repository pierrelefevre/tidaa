package f.f8;

public class NB24 {
    public static void main(String[] args) {
        var table = new HashTableBucket<Integer, String>();

        System.out.println("HashTableBucket: \n" + table);

        for (int i = 0; i < 10; i++) {
            table.put(i * 2, "V" + i * 5);
        }

        System.out.println();

        System.out.println("After 10 elements: ");
        System.out.println(table);
        System.out.println();

        //Test get
        System.out.println("Test get");
        for (int i = 0; i < 5; i++) {
            var key = i * 2;
            System.out.println("Key " + key + "\t Value: " + table.get(key));
        }

        System.out.println();

        System.out.println("Remove 4 and 6: ");
        table.remove(4);
        table.remove(6);
        System.out.println(table);

        System.out.println();

        System.out.println("Overwrite key 2: ");
        table.put(2, "NEWVAL");
        System.out.println(table);
        System.out.println();

        System.out.println("Add new values: ");
        for (int i = 5; i < 50; i++) {
            table.put(i * 2, "R2 V" + i * 5);
        }
        System.out.println();

        //Collision and remove check
        System.out.println(table);
        table.remove(18);
//        System.out.println(myHashMap);
        table.remove(90);
        System.out.println(table);
    }
}
