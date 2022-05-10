package f.f6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Maze {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    int rows, columns;
    Cell[][] mazeMatrix;
    private Position currentP, goal;

    public Maze() {
        try {
            BufferedReader in = new BufferedReader(new FileReader("src/f.f6/Labyrint.txt"));
            rows = Integer.parseInt(in.readLine()) + 2;
            columns = Integer.parseInt(in.readLine()) + 2;
            mazeMatrix = new Cell[rows][columns];
            for (int j = 0; j < columns; j++) {
                mazeMatrix[0][j] = Cell.WALL;
                mazeMatrix[rows - 1][j] = Cell.WALL;
            }
            for (int i = 1; i < rows - 1; i++) {
                mazeMatrix[i][0] = Cell.WALL;
                mazeMatrix[i][columns - 1] = Cell.WALL;
            }
            for (int i = 1; i < rows - 1; i++) {
                String s = in.readLine();
                for (int j = 1; j < columns - 1; j++) {
                    mazeMatrix[i][j] = Cell.OPEN;
                    if (s.charAt(j - 1) == '*')
                        mazeMatrix[i][j] = Cell.WALL;
                    else if (s.charAt(j - 1) == 'g') {
                        goal = new Position(i, j);
                    } else if (s.charAt(j - 1) == 's') {
                        currentP = new Position(i, j);
                    }
                }
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace(System.err);
            System.exit(1);
        }
    }

    public boolean solve() {
        return solve(currentP);
    }

    private boolean test(int row, int col) {
        return (mazeMatrix[row][col] == Cell.OPEN);
    }

    private boolean solve(Position p) {
        mazeMatrix[p.row][p.column] = Cell.CORRECT;

        if (p.equals(goal)) {
            return true;
        }

        //Test right
        if (test(p.row, p.column + 1)) {
            if (solve(new Position(p.row, p.column + 1)))
                return true;
        }

        //Test left
        if (test(p.row, p.column - 1)) {
            if (solve(new Position(p.row, p.column - 1)))
                return true;
        }

        //Test up
        if (test(p.row - 1, p.column)) {
            if (solve(new Position(p.row - 1, p.column)))
                return true;
        }

        //Test down
        if (test(p.row + 1, p.column)) {
            if (solve(new Position(p.row + 1, p.column)))
                return true;
        }

        mazeMatrix[p.row][p.column] = Cell.VISITED;

        return false;
    }

    public void print() {
        for (int i = 1; i < rows - 1; i++) {
            for (int j = 1; j < columns - 1; j++)
                switch (mazeMatrix[i][j]) {
                    case WALL:
                        System.out.print(ANSI_RED + "▓▓" + ANSI_RESET);
                        break;
                    case OPEN:
                        System.out.print("  ");
                        break;
                    case CORRECT:


                        if (i == goal.row && j == goal.column) {
                            System.out.print(ANSI_CYAN + "X " + ANSI_RESET);
                            break;
                        }
                        if (i == currentP.row && j == currentP.column) {
                            System.out.print(ANSI_CYAN + "O " + ANSI_RESET);
                            break;
                        }

                        if (mazeMatrix[i - 1][j] == Cell.CORRECT && mazeMatrix[i][j + 1] == Cell.CORRECT) {
                            System.out.print(ANSI_GREEN + "└─" + ANSI_RESET);
                            break;
                        }
                        if (mazeMatrix[i - 1][j] == Cell.CORRECT && mazeMatrix[i][j - 1] == Cell.CORRECT) {
                            System.out.print(ANSI_GREEN + "┘ " + ANSI_RESET);
                            break;
                        }
                        if (mazeMatrix[i + 1][j] == Cell.CORRECT && mazeMatrix[i][j + 1] == Cell.CORRECT) {
                            System.out.print(ANSI_GREEN + "┌─" + ANSI_RESET);
                            break;
                        }
                        if (mazeMatrix[i + 1][j] == Cell.CORRECT && mazeMatrix[i][j - 1] == Cell.CORRECT) {
                            System.out.print(ANSI_GREEN + "┐ " + ANSI_RESET);
                            break;
                        }

                        if (mazeMatrix[i - 1][j] == Cell.CORRECT || mazeMatrix[i + 1][j] == Cell.CORRECT) {
                            System.out.print(ANSI_GREEN + "│ " + ANSI_RESET);
                            break;
                        }
                        if (mazeMatrix[i][j - 1] == Cell.CORRECT && mazeMatrix[i][j + 1] == Cell.CORRECT) {
                            System.out.print(ANSI_GREEN + "──" + ANSI_RESET);
                            break;
                        }

                        System.out.print(" ");
                        break;
                    case VISITED:
                        System.out.print(ANSI_YELLOW + ". " + ANSI_RESET);
                        break;
                    default:
                        System.out.print("??");
                }
            System.out.println();
        }
    }

    public enum Cell {WALL, OPEN, CORRECT, VISITED}

    private class Position {
        int row, column;

        public Position(int r, int c) {
            row = r;
            column = c;
        }

        @Override
        public String toString() {
            return "[X: " + row + ", Y: " + column + "]";
        }

        public boolean equals(Position p) {
            return (row == p.row && column == p.column);
        }
    }

}
