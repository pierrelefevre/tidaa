package lab.lab2;

import java.util.Arrays;

public class U5_6 {
    public static void main(String[] args) {
        //BEACD
        //BAECD
        //EADBC

        char[] a = new char[]{'b', 'e', 'a', 'c', 'd'};
        char[] b = new char[]{'b', 'a', 'e', 'c', 'd'};
        char[] c = new char[]{'e', 'a', 'd', 'b', 'c'};

        System.out.println("Depth first Robot");
        testDepth(a);
        testDepth(b);
        testDepth(c);

        System.out.println("\nBreadth first Robot");
        testBreadth(a);
        testBreadth(b);
        testBreadth(c);

    }

    private static void testDepth(char[] a) {
        RobotSorter s = new RobotSorter();
        System.out.println("Goal:\t" + Arrays.toString(s.getGoal()));
        System.out.println("Input:\t" + Arrays.toString(a));
        String solution = s.solve(a);
        System.out.println("Moves:\t" + solution + " (" + solution.length() + ")");
    }

    private static void testBreadth(char[] a) {
        BreadthRobotSorter s = new BreadthRobotSorter();
        System.out.println("Goal:\t" + Arrays.toString(s.getGoal()));
        System.out.println("Input:\t" + Arrays.toString(a));
        String solution = s.solve(a);
        System.out.println("Moves:\t" + solution + " (" + solution.length() + ")");
    }
}
