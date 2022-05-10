package f.f11;

public class NB32 {
    public static void main(String[] args) {
        System.out.println("NB32: Graph in Java");
        GraphSolver a = new GraphSolver("C:\\Users\\hello\\Desktop\\algodat\\krysstal\\src\\f.f11\\europe.txt", true);
        System.out.println(a.findPath("Stockholm", "Madrid"));
        System.out.println(a.findPath("Rome", "Stockholm"));
        System.out.println(a.findPath("London", "Vienna"));

        GraphSolver b = new GraphSolver("C:\\Users\\hello\\Desktop\\algodat\\krysstal\\src\\f.f11\\nicklas.txt", true);
        System.out.println(b.findPath("b", "a"));
        System.out.println(b.findPath("c", "a"));
        System.out.println(b.findPath("d", "a"));
        System.out.println(b.findPath("e", "a"));
        System.out.println(b.findPath("f", "a"));
        System.out.println(b.findPath("g", "a"));
        System.out.println(b.findPath("h", "a"));
    }
}
