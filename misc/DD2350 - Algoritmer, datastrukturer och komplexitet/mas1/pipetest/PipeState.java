package pipetest;

import java.util.*;

public class PipeState {
    Pipe pipes[];
    List<Step> steps;

    public PipeState() {
        pipes = new Pipe[14];
        for (int i = 0; i < 14; i++)
            pipes[i] = new Pipe();
        steps = new ArrayList<>();
    }
}
