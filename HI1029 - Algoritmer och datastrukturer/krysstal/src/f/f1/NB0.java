package f.f1;

public class NB0 {

    public static void main(String[] args) {
        System.out.println("NB0 - ArrayList2 Demo");

        ArrayList2<String> al2 = new ArrayList2<>();
        System.out.println(al2);

        al2.add("Hej!");
        al2.add("Hejsan!");
        System.out.println(al2);

        al2.remove(al2.indexOf("Hej!"));
        System.out.println(al2);

        al2.set(2, "hejdå");
        al2.set(0, "hejdå");
        System.out.println(al2);
    }

}
