package lab.lab2;

import java.util.Arrays;
import java.util.LinkedList;

public class BreadthRobotSorter {
    static char[] goal = new char[]{'a', 'b', 'c', 'd', 'e'};

    public BreadthRobotSorter() {
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

    public String solve(char[] a) {
        var q = new LinkedList<State>();
        var sb = new StringBuilder();
        var state = new State(a, 0, "");
        while (!isEqual(state.a, goal)) {
            q.offer(new State(swapEdges(state.a), state.depth + 1, state.swaps + "s"));
            q.offer(new State(swapLeft(state.a), state.depth + 1, state.swaps + "b"));

            state = q.poll();
        }

        return state.swaps;
    }

    private class State {
        int depth;
        char[] a;
        String swaps;

        public State(char[] a, int depth, String swaps) {
            this.depth = depth;
            this.a = a;
            this.swaps = swaps;
        }
    }
}
