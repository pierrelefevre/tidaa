package f.f7;

import java.util.Random;

public class F7 {
    public static void main(String[] args) {
        var bst = new F7BST<Integer>();
        Random rng = new Random();
        for (int i = 0; i < 50; i++) {
            bst.add(rng.nextInt(50));
        }
        bst.printTree();

        System.out.println("Tree has " + bst.numberOfLeaves() + " leaves, " + bst.numberOfNodes() + " nodes, " + bst.height() + " size");
        int findMe = rng.nextInt(15);
        System.out.println("Searching for: " + findMe + " Found: " + bst.find(findMe));
        System.out.println("Max: " + bst.maxRec() + " Iter: " + bst.maxIt());
    }
}
