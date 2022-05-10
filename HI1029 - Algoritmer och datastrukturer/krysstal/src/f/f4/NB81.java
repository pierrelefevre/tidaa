package f.f4;

public class NB81 {
    public static void main(String[] args) {
        ArrayQueue81<String> nameQ = new ArrayQueue81<String>(10);
        System.out.println("Fresh array");
        System.out.println(nameQ.toString());
        try {
            System.out.println("\nAdd 8 elements");
            for (int i = 0; i < 8; i++)
                nameQ.offer("a" + (i + 1));
            System.out.println(nameQ.toString());

            System.out.println("\nRemove 2 elements");
            System.out.println(nameQ.poll());
            System.out.println(nameQ.poll());
            System.out.println(nameQ.toString());

            System.out.println("\nAdd 6 more");
            for (int i = 8; i < 14; i++) {
                nameQ.offer("b" + (i + 1));
                System.out.println(nameQ.toString());
            }

            System.out.println("\nRemove 4 of them");
            nameQ.poll();
            nameQ.poll();
            nameQ.poll();
            nameQ.poll();

            System.out.println(nameQ.toString());

            System.out.println("\nPrint all now");
            System.out.println(nameQ.toString());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}