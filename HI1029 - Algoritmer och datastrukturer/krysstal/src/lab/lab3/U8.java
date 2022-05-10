package lab.lab3;

public class U8 {
//    public static void main(String[] args) {
//        System.out.println("Lab uppgift 8 - Next larger");
//        F7BST<Integer> bst = new F7BST<>();
//        bst.add(5);
//        bst.add(1);
//        bst.add(12);
//        bst.add(8);
//        bst.add(2);
//
//        bst.printTree();
//        System.out.println();
//
//        System.out.println("\nShould be 2");
//        System.out.println("Search for 1: " + bst.getNextLarger(1));
//
//        System.out.println("\nShould be 5");
//        System.out.println("Search for 1: " + bst.getNextLarger(1));
//        System.out.println("Search for 2: " + bst.getNextLarger(2));
//        System.out.println("Search for 2: " + bst.getNextLarger(2));
//        System.out.println("Search for 3: " + bst.getNextLarger(3));
//        System.out.println("Search for 4: " + bst.getNextLarger(4));
//
//        System.out.println("\nShoule be 8");
//        System.out.println("Search for 5: " + bst.getNextLarger(5));
//        System.out.println("Search for 6: " + bst.getNextLarger(6));
//        System.out.println("Search for 7: " + bst.getNextLarger(7));
//
//        System.out.println("\nShoule be null");
//        System.out.println("Search for 12 " + bst.getNextLarger(12));
//        System.out.println("Search for 13 " + bst.getNextLarger(13));
//        System.out.println("Search for null " + bst.getNextLarger(null));

    public static void buildTree(F7BST<Integer> b, int[] v) {
        for (int data : v)
            b.add(data);
    }

    public static void main(String[] args) {

        F7BST<Integer> bst = new F7BST<>();
        buildTree(bst, new int[]{30, 20, 40, 15, 25, 35, 45, 10, 17, 22, 27, 32, 37, 42, 47, 16, 23, 28, 39, 49, 29, 51});
        System.out.println(bst);
        for (int i = 0; i < 52; i++)
            System.out.print("" + i + ":" + bst.getNextLarger(i) + "#");

    }
}
//    }
