package lab.lab2;

import java.util.Arrays;

public class RobotSorter {

    char[] goal = new char[]{'a', 'b', 'c', 'd', 'e'};

    public RobotSorter() {
    }

    public char[] getGoal() {
        return goal;
    }

    private char[] swapLeft(char[] test) {
        char[] a = Arrays.copyOf(test, test.length);
        char tmp = a[0];
        a[0] = a[1];
        a[1] = tmp;
        return a;
    }

    private char[] swapEdges(char[] test) {
        char right = test[test.length - 1];

        char[] a = Arrays.copyOf(test, test.length);
        for (int i = test.length - 1; i > 0; i--) {
            a[i] = a[i - 1];
        }

        a[0] = right;
        return a;
    }

    private boolean isEqual(char[] a, char[] goal) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] != goal[i])
                return false;
        }
        return true;
    }

    private String solve(char[] a, int depth) {
        if (depth > 15) {
            return "";
        }

        if (isEqual(a, goal)) {
            return "";
        }

        String edges = solve(swapEdges(a), depth + 1);
        String left = solve(swapLeft(a), depth + 1);

        if (edges.length() > left.length()) {
            return "b" + left;
        } else {
            return "s" + edges;
        }
    }

    public String solve(char[] a) {
        return solve(a, 0);
    }
}
