package f.f2;

public class NB2 {

    public static void main(String[] args) {
        System.out.println("NB0 - ArrayList2 Demo");

        IntList il = new IntList(16);
        System.out.println(il);

        il.add(1);
        il.add(2);
        System.out.println(il + " size " + il.size());

        il.remove(il.indexOf(1));
        System.out.println(il);

        il.set(2, 3);
        il.set(0, 3);
        System.out.println(il);
    }

}
