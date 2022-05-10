package f.f4;

public class NB10 {
    public static void main(String[] args) {
        Deq deq = new Deq();

        System.out.println("NB10!");
        deq.offerFirst("Hej!1");
        deq.offerFirst("Hej!2");
        deq.offerFirst("Hej!3");
        deq.offerFirst("Hej!4");
        deq.offerFirst("Hej!5");

        System.out.println(deq);

        System.out.println(deq.pollFirst() + " SIZE " + Integer.toString(deq.size()));
        System.out.println(deq.pollFirst() + " SIZE " + Integer.toString(deq.size()));
        System.out.println(deq.pollFirst() + " SIZE " + Integer.toString(deq.size()));
        System.out.println(deq.pollFirst() + " SIZE " + Integer.toString(deq.size()));

        System.out.println("Removing last");
        System.out.println(deq.pollFirst() + " SIZE " + Integer.toString(deq.size()));


        System.out.println("Removing should be null");
        System.out.println(deq.pollFirst() + " SIZE " + Integer.toString(deq.size()));

        System.out.println("\nEmpty list:");
        System.out.println(deq);

        System.out.println("\nFilling from behind:");
        deq.offerLast("Hej!1");
        deq.offerLast("Hej!2");
        deq.offerLast("Hej!3");

        System.out.println(deq);

        System.out.println(deq.pollLast());
        System.out.println(deq.pollFirst());

        System.out.println("\nFinished list");
        System.out.println(deq);
    }
}
