package uppgift1;

public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) {
        SingleLinkedList<String> list = new SingleLinkedList<>();

        for (int i = 0; i < 10; i++)
            list.add(4, "Strang " + i);

        for (int i = 0; i < 10; i++)
            System.out.println(list.get(i));

        System.out.println("Storlek rekursivt: " + list.size());
    }

}