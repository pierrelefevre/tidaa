package f.f12;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static f.f12.Knapsack.solve;

public class NB35 {
    public static void main(String[] args) {
        ArrayList<Knapsack.Package> packages = new ArrayList<>();
        System.out.println("NB35 - Sack");
        int size = 100;
        Random rng = new Random();
        for (int i = 0; i < 10; i++) {
            packages.add(new Knapsack.Package(30 - rng.nextFloat() * (30 - 1), 30 - rng.nextFloat() * (30 - 1)));
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean running = true;
        int input = -1;

        while (running) {
            System.out.println();
            System.out.println("Current packages available");
            System.out.println(packages + " ");
            System.out.println("SIZE " + size);
            System.out.println("1 Add packages; 2 Set sack size; 3 Sort the sack; 0: Quit");

            try {
                input = Integer.parseInt(br.readLine());
                switch (input) {
                    case 0:
                        running = false;
                        break;
                    case 1:
                        System.out.println("Enter weight");
                        float weight = Float.parseFloat(br.readLine());
                        System.out.println("Enter value");
                        float value = Float.parseFloat(br.readLine());
                        Knapsack.Package pkg = new Knapsack.Package(weight, value);
                        packages.add(pkg);
                        System.out.println("New pkg: " + pkg);
                        break;
                    case 2:
                        System.out.println("Enter size");
                        size = Integer.parseInt(br.readLine());
                        System.out.println("New size: " + size);
                        break;
                    case 3:
                        System.out.println("Packed following boxes: ");
                        printPackages(solve(packages, size));

                }
            } catch (Exception e) {
                System.out.println("Try again");
            }
        }
    }

    private static void printPackages(HashMap<Knapsack.Package, Integer> sack) {
        for (var p : sack.entrySet()) {
            System.out.println(p.getKey() + " " + p.getValue() + "x");
        }
    }
}
