package lab.lab3;

import java.util.ArrayList;

public class PuzzleSolver {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";

    Type[][] board;
    int maxDepth;
    int solutions;

    public PuzzleSolver(int blockedRow, int blockedCol, int maxDepth) {
        board = new Type[5][5];
        solutions = 0;
        this.maxDepth = maxDepth;

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                board[row][col] = Type.Empty;
                if (row == blockedRow && col == blockedCol)
                    board[row][col] = Type.Blocked;
            }
        }
    }

    public void solve() {
        solve(0, 0, 0);
    }

    public void solve(int row, int col, int depth) {
        if (row >= board.length)
            return;

        for (int i = col; i < board.length; i++) {
            Position currentPos = new Position(row, i);
            ArrayList<Type> check = checkAvailable(currentPos);
            for (Type t : check) {
                placePiece(t, currentPos);
                depth++;

                if (depth == 8) {
                    printBoard();
                    solutions++;
                }

                solve(row, i + 1, depth);
                if (checkRow(row))
                    solve(row + 1, 0, depth);

                removePiece(t, currentPos);
                depth--;
            }
        }
    }

    private boolean checkRow(int row) {
        for (int i = 0; i < board.length; i++) {
            if (board[row][i] == Type.Empty)
                return false;
        }
        return true;
    }

    public int getSolutions() {
        return solutions;
    }

    private void removePiece(Type check, Position currentPos) {
        for (Position p : getPositions(check, currentPos)) {
            board[p.row][p.col] = Type.Empty;
        }
    }

    private void placePiece(Type check, Position currentPos) {
        for (Position p : getPositions(check, currentPos)) {
            board[p.row][p.col] = check;
        }
    }

    private ArrayList<Type> checkAvailable(Position currentPos) {
        var types = new ArrayList<Type>();
        Type correctType = Type.J;
        for (Position p : getPositions(Type.J, currentPos)) {
            if (!checkPosition(p))
                correctType = null;
        }
        if (correctType != null)
            types.add(correctType);

        correctType = Type.L;
        for (Position p : getPositions(Type.L, currentPos)) {
            if (!checkPosition(p))
                correctType = null;
        }
        if (correctType != null)
            types.add(correctType);

        correctType = Type.P;
        for (Position p : getPositions(Type.P, currentPos)) {
            if (!checkPosition(p))
                correctType = null;
        }
        if (correctType != null)
            types.add(correctType);

        correctType = Type.Q;
        for (Position p : getPositions(Type.Q, currentPos)) {
            if (!checkPosition(p))
                correctType = null;
        }
        if (correctType != null)
            types.add(correctType);

        return types;
    }

    private boolean checkPosition(Position s) {
        if (s.row < 0 || s.col < 0)
            return false;
        if (s.row >= board.length)
            return false;
        if (s.col >= board[0].length)
            return false;
        return board[s.row][s.col] == Type.Empty;
    }

    private Position[] getPositions(Type t, Position start) {
        Position p1 = new Position(start.row, start.col);
        Position p2 = new Position(start.row, start.col);

        switch (t) {
            case J:
                p1 = new Position(start.row + 1, start.col);
                p2 = new Position(start.row + 1, start.col - 1);
                break;

            case L:
                p1 = new Position(start.row + 1, start.col);
                p2 = new Position(start.row + 1, start.col + 1);
                break;

            case Q:
                p1 = new Position(start.row, start.col + 1);
                p2 = new Position(start.row + 1, start.col + 1);
                break;

            case P:
                p1 = new Position(start.row, start.col + 1);
                p2 = new Position(start.row + 1, start.col);
                break;
        }

        Position[] positions = new Position[]{new Position(start.row, start.col), p1, p2};
        return positions;
    }

    public void printBoard() {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                switch (board[row][col]) {
                    case J:
                        System.out.print(ANSI_BLUE + "██ " + ANSI_RESET);
                        break;

                    case L:
                        System.out.print(ANSI_GREEN + "██ " + ANSI_RESET);
                        break;

                    case Q:
                        System.out.print(ANSI_RED + "██ " + ANSI_RESET);
                        break;

                    case P:
                        System.out.print(ANSI_YELLOW + "██ " + ANSI_RESET);
                        break;

                    case Blocked:
                        System.out.print(ANSI_PURPLE + "██ " + ANSI_RESET);
                        break;

                    default:
                        System.out.print(ANSI_WHITE + "██ " + ANSI_RESET);
                        break;
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    private enum Type {J, L, Q, P, Blocked, Empty}

    private class Position {
        int col;
        int row;

        public Position(int row, int col) {
            this.col = col;
            this.row = row;
        }
    }
}
