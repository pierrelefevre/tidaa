package f.f6;

import java.util.LinkedList;
import java.util.Random;

public class NB16 {
    public static void main(String[] args) {
        System.out.println("NB16");

        Random r = new Random();
        System.out.println(Integer.toString(swap(5, 1, 3)));
        System.out.println(Integer.toString(swap(2, 1, 3)));
        System.out.println(Integer.toString(swap(22, 22, 13)));
    }

    private static int swap(int b, int w, int r) {
        var q = new LinkedList<BallState>();
        var state = new BallState(b, w, r);

        while (!(state.w == state.b && state.b == state.r) && state.hops < 15) {
            q.offer(new BallState(state.b - 1, state.w + 3, state.r + 1, state.hops + 1));
            q.offer(new BallState(state.b + 3, state.w - 1, state.r + 4, state.hops + 1));
            q.offer(new BallState(state.b + 2, state.w + 1, state.r - 1, state.hops + 1));

            state = q.poll();
        }

        return state.hops;
    }

    private static class BallState {
        int b;
        int w;
        int r;
        int hops;

        public BallState(int b, int w, int r, int hops) {
            this.b = b;
            this.w = w;
            this.r = r;
            this.hops = hops;
        }

        public BallState(int b, int w, int r) {
            this.b = b;
            this.w = w;
            this.r = r;
            this.hops = 0;
        }

        @Override
        public String toString() {
            return "BallState{" +
                    "b=" + b +
                    ", w=" + w +
                    ", r=" + r +
                    ", hops=" + hops +
                    '}';
        }
    }
}
