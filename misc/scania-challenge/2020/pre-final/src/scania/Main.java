package scania;

import java.util.ArrayList;
import java.util.Comparator;

public class Main {

    public static void main(String[] args) {
        ArrayList<Cargo> boxes = new DataGetter().getData("C:\\Users\\pierr\\Desktop\\scania-challenge\\src\\cargo.txt");
        boxes.sort(Cargo::compareTo);

        ArrayList<Cargo> truck = new ArrayList<>();
        int spaceLeft = 10000;
        int value = 0;

        for (Cargo active : boxes) {
            if (spaceLeft - active.getWeight() < 0)
                continue;

            truck.add(active);
            spaceLeft -= active.getWeight();
            value += active.getValue();
        }
        System.out.println(truck);
        System.out.println("Truck loaded with " + truck.size() + "/" + boxes.size() + " boxes to " + (10000 - spaceLeft) + " kg with value " + value + "kr.");
    }
}
