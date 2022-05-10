package f.f4;

public class NB8 {
    public static void main(String[] args) {
        ArrayQueue<String> nameQ = new ArrayQueue<String>(10);

        System.out.println("Size 0 " + nameQ.getSize());
        System.out.println("Max 0  " + nameQ.getMaxSize());
        try {

            for (int i = 0; i < 8; i++)
                nameQ.offer("a" + (i + 1));
            nameQ.poll();
            nameQ.poll();
            for (int i = 8; i < 14; i++)
                nameQ.offer("b" + (i + 1));
            System.out.println("Size 1 " + nameQ.getSize());
            System.out.println("Max 1  " + nameQ.getMaxSize());
            nameQ.poll();
            nameQ.poll();
            nameQ.poll();
            nameQ.poll();
            System.out.println("Size 2 " + nameQ.getSize());
            System.out.println("Max 2  " + nameQ.getMaxSize());
            System.out.println("");

            while (nameQ.peek() != null)
                System.out.println(nameQ.poll() + " size: " + +nameQ.getSize());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}