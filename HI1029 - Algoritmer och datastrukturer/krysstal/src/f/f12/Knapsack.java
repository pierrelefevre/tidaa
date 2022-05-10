package f.f12;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class Knapsack {
    public static HashMap<Package, Integer> solve(ArrayList<Package> packages, float size) {
        var sack = new HashMap<Package, Integer>();
        packages.sort(new Package.PackageComparator());

        //Find smallest package in list
        float smallestSize = Float.POSITIVE_INFINITY;
        for (Package p : packages) {
            if (p.weight < smallestSize)
                smallestSize = p.weight;
        }
        double totalValue = 0;
        while (size > smallestSize) {

            //Find the best package to fit considering value; Sorted by value already so first is best
            for (Package p : packages) {
                if (p.weight < size) {
                    if (!sack.containsKey(p)) {
                        sack.put(p, 1);
                    } else {
                        sack.put(p, sack.get(p) + 1);
                    }
                    totalValue += p.getValue();
                    size -= p.getWeight();
                    break;
                }
            }
        }
        System.out.println("Total value: " + totalValue);
        System.out.println("Empty size left: " + size + " kg");
        return sack;
    }

    public static class Package {
        private final float weight;
        private final float value;

        public Package(float weight, float value) {
            this.weight = weight;
            this.value = value;
        }

        @Override
        public String toString() {
            return "[" + weight + "kg " + value + "kr " + getRatio() + "kr/kg] ";
        }

        public float getRatio() {
            return value / weight;
        }

        public float getWeight() {
            return weight;
        }

        public float getValue() {
            return value;
        }

        public static class PackageComparator implements Comparator<Package> {
            @Override
            public int compare(Package a, Package b) {
                return (int) (b.getRatio() * 100000 - a.getRatio() * 100000);
            }
        }
    }
}
