package lab.lab1;

public class u2 {

//    public static void main(String[] args) {
//        System.out.println("Lab1.2 LinkedList Demo");
//
//        U2_SingleLinkedList<String> sll = new U2_SingleLinkedList<>();
//
//        System.out.println(sll);
//
//        sll.add("0");
//        System.out.println(sll);
//
//        sll.add("1");
//        sll.add("2");
//        sll.add("3");
//        System.out.println(sll);
//
//        sll.add(1, "0.5");
//        System.out.println(sll);
//
//        System.out.println("Get index 2: " + sll.get(2));
//        System.out.println(sll);
//
//        System.out.println("Remove index 2: " + sll.remove(2));
//        System.out.println(sll);
//
//        System.out.println("Remove index 3: " + sll.remove(3));
//        System.out.println(sll);
//
//        System.out.println("Remove index 0: " + sll.remove(0));
//        System.out.println(sll);
//
//        sll.add(0, "0.");
//        System.out.println(sll);
//
//        sll.remove(0);
//        System.out.println(sll);
//    }


    public static void main(String[] args) {

        U2_SingleLinkedList<String> list = new U2_SingleLinkedList<>();

        for (int i = 0; i < 4; i++) {
            list.add("e" + i);
        }
        list.remove(3);
        list.add(0, "först");
        list.add("sist");
        System.out.println(list);


        //Test a basic list and see if the tail is following
//        for (int i = 0; i <= 10; i++) {//O(n^2)
//            list.add("Sträng" + i);
//            for (String s : list) { //O(n)
//                System.out.println(s);
//            }
//        }
//
//        System.out.println(list.get(2));
//
//
//        list.remove(10);
//        list.printDataHeadTailSize("Testing to remove last item");
//
//        list.remove(3);
//        list.printDataHeadTailSize("Testing to remove in the middle");
//
//        list.add(5, "Mellan 5 och 6");
//        list.printDataHeadTailSize("Testing to add in the middle");
//
//        list.remove(0);
//        list.printDataHeadTailSize("Testing to remove the head");
//
//        list.add(0, "First");
//        list.printDataHeadTailSize("Testing to add a new item to the head");
//
//        U2_SingleLinkedList<String> smallL = new U2_SingleLinkedList<>();
//
//        smallL.add("One item");
//        smallL.printDataHeadTailSize("Check head and tail");
//
//        smallL.add(0, "Zero item");
//        smallL.printDataHeadTailSize("Try to add to head");
//
//        smallL.remove(0);
//        smallL.printDataHeadTailSize("Remove head");
//
//        smallL.remove(0);
//        smallL.printDataHeadTailSize("Test to clear list");
//
//        smallL.add("New Item");
//        smallL.add("Second item");
//        smallL.printDataHeadTailSize("Test to add item again");
    }
}
