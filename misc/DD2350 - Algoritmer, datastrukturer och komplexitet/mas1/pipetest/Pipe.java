package pipetest;

public class Pipe {
    public static final int PIPESIZE = 5;

    char[] pipe;
    int pipeId;

    public Pipe() {
        pipe = new char[PIPESIZE];
    }

    public int topIndex() {
        for (int i = 0; i < PIPESIZE; i++) {
            if (pipe[i] == ' ')
                return i;
        }
        return PIPESIZE - 1;
    }

    public boolean isFull() {
        for (int i = 0; i < PIPESIZE; i++) {
            if (pipe[i] == ' ')
                return false;
        }
        return true;
    }
}
