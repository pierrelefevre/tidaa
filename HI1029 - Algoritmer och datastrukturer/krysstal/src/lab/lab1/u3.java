package lab.lab1;

import java.util.Iterator;

public class u3 {

    public static void main(String[] args) {
        U3_SingleLinkedList<String> list = new U3_SingleLinkedList<String>();

        for (int i = 0; i < 10; i++) {
            list.add("e" + i);
        }

        Iterator<String> iter = list.iterator();
        ;
        iter.remove();
        System.out.println(list);

//
//        System.out.println("Lab1.3 LinkedList Demo");
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
//        System.out.println("Remove 0.5!");
//        Iterator<String> iter = sll.iterator();
//        while (iter.hasNext()) {
//            if (iter.next() == "0.5")
//                iter.remove();
//        }
//
//        sll.add(0, "0.");
//        System.out.println(sll);
//
//        System.out.println("Remove it all!");
//        System.out.println(sll);
//
//        Iterator<String> iter2 = sll.iterator();
//
////        while (iter2.hasNext()) {
////            iter2.next();
////            iter2.remove();
////        }
////
////
//        iter2.next();
//        iter2.next();
//        iter2.remove();
//        iter2.remove();
//
//        System.out.println(sll);
    }
}
