package f.f10;

public class NB30 {
    public static void main(String[] args) {
        GenericHeap<String> h = new GenericHeap<>(2);
        h.insert("a");
        h.insert("b");
        h.insert("c");
        h.insert("z");
        h.insert("x");
        h.insert("a");
        h.insert("ab");

        System.out.println(h.extract());
        System.out.println(h.extract());
        System.out.println(h.extract());
        System.out.println(h.extract());
        System.out.println(h.extract());
        System.out.println(h.extract());
        System.out.println(h.extract());
    }
}
