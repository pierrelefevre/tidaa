package pipetest;

import java.util.*;

public class WaterColor {

    public static void main(String[] args) {
        System.out.println("Start solver");

        Queue<PipeState> q = new LinkedList<>();

        PipeState state = initState();

        while (!solved(state)) {
            for (int i = 0; i < state.pipes.length; i++) {
                if (pour(i, state)) {
                    q.offer(state);
                }
            }
        }

        state = q.poll();

        for (var step : state.steps) {
            System.out.println("Häll rör " + step.from + " till rör " + step.to + ".");
        }
    }

    private static boolean solved(PipeState state) {
        for (var pipe : state.pipes) {
            var lastColor = pipe.pipe[0];
            for (int i = 1; i < Pipe.PIPESIZE; i++) {
                if (pipe.pipe[i] != lastColor) {
                    return false;
                }
            }
        }

        return true;
    }

    private static PipeState initState() {
        var ps = new PipeState();

        for (int i = 0; i < ps.pipes.length; i++) {
            for (int j = 0; j < Pipe.PIPESIZE; j++) {
                int value = 97 + (int) (Math.random() * 12);
                ps.pipes[i].pipe[j] = (char) value;
            }
        }

        return ps;
    }

    private static boolean pour(int currentPipeIndex, PipeState state) {
        for (int i = 0; i < state.pipes.length; i++) {
            Pipe currentPipe = state.pipes[currentPipeIndex];
            Pipe pipe = state.pipes[i];
            if (pipe.pipeId != currentPipe.pipeId && !pipe.isFull()) {

                int pipeIndex = pipe.topIndex();
                int currentIndex = currentPipe.topIndex();

                // Do the pour
                if (pipe.pipe[pipeIndex] == currentPipe.pipe[currentIndex]) {
                    char bucket = currentPipe.pipe[currentIndex];
                    currentPipe.pipe[currentIndex] = ' ';
                    pipe.pipe[pipeIndex + 1] = bucket;

                    state.steps.add(new Step(currentPipeIndex, i));
                    System.out.println("Pour " + currentPipeIndex + " to " + i);
                    return true;
                }
            }
        }
        return false;
    }

}