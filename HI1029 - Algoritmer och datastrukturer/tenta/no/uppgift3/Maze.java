package uppgift3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Maze {

    int rows, columns;
    int[][] mazeMatrix;
    Point goal;
    Point start;

    private class Point {
        int row;
        int col;

        public Point(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    public Maze() {
        try {
            BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\hello\\Desktop\\algodat\\tenta\\uppgift3\\Labyrint.txt"));
            rows = Integer.parseInt(in.readLine()) + 2;
            columns = Integer.parseInt(in.readLine()) + 2;
            mazeMatrix = new int[rows][columns];
            for (int j = 0; j < columns; j++) {
                mazeMatrix[0][j] = 0;
                mazeMatrix[rows - 1][j] = 0;
            }
            for (int i = 1; i < rows - 1; i++) {
                mazeMatrix[i][0] = 0;
                mazeMatrix[i][columns - 1] = 0;
            }
            for (int i = 1; i < rows - 1; i++) {
                String s = in.readLine();
                for (int j = 1; j < columns - 1; j++) {
                    mazeMatrix[i][j] = 1;
                    if (s.charAt(j - 1) == '*')
                        mazeMatrix[i][j] = 0;
                    else if (s.charAt(j - 1) == 'g') {
                        goal = new Point(i, j);
                    } else if (s.charAt(j - 1) == 's') {
                        start = new Point(i, j);
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
        return solve(start.row, start.col);
    }

    private boolean solve(int row, int col) {
        mazeMatrix[row][col] = 2;

        if (row == goal.row && col == goal.col) {
            return true;
        }

        if (check(row, col - 1)) {
            if (solve(row, col - 1))
                return true;
        }

        if (check(row, col + 1)) {
            if (solve(row, col + 1))
                return true;
        }

        if (check(row - 1, col)) {
            if (solve(row - 1, col))
                return true;
        }

        if (check(row + 1, col)) {
            if (solve(row + 1, col))
                return true;
        }

        mazeMatrix[row][col] = 1;
        return false;
    }

    private boolean check(int row, int col) {
        if (col >= columns || row >= rows)
            return false;
        if (row < 0 || col < 0)
            return false;
        if (mazeMatrix[row][col] != 1)
            return false;
        return true;
    }

    public void print() {
        for (int i = 1; i < rows - 1; i++) {
            for (int j = 1; j < columns - 1; j++)
                if (mazeMatrix[i][j] == 2)
                    System.out.print(" ");
                else
                    System.out.print(mazeMatrix[i][j]);
            System.out.println();
        }
    }

}
