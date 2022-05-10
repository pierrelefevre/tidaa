package f.f4;

public class NB9 {


    public static void main(String[] args) {
        LinkedQ<String> lq = new LinkedQ<>();
        System.out.println("NB9!");
        lq.offer("Hej!1");
        lq.offer("Hej!2");
        lq.offer("Hej!3");
        lq.offer("Hej!4");
        lq.offer("Hej!5");

        System.out.println(lq);

        System.out.println(lq.poll());
        System.out.println(lq.poll());
        System.out.println(lq.poll());
        System.out.println(lq.poll());
        System.out.println(lq.poll());
        System.out.println(lq.poll());

        System.out.println(lq);

        lq.offer("Hej!1");
        lq.offer("Hej!2");
        lq.offer("Hej!3");

        System.out.println(lq.poll());
        System.out.println(lq.poll());

        System.out.println("Finished list");
        System.out.println(lq);
    }
}


