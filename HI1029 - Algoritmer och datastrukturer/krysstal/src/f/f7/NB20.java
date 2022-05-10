package f.f7;

public class NB20 {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    private static int timesPrinted;

    public static void main(String[] args) {
        int size = 8;
        int[][] board = new int[size][size];

        addQueen(board, size, 0);
    }

    private static void addQueen(int[][] board, int size, int row) {
        for (int col = 0; col < size; col++) {
            if (!checkCol(board, col, size) && !checkRow(board, row, size) && !checkNW(board, col, row, size) && !checkNE(board, col, row, size)) {
                board[row][col] = 1;
                if (row == size - 1) {
                    printBoard(board, size);
                } else {
                    addQueen(board, size, row + 1);
                }
                board[row][col] = 0;
            }
        }
    }

    private static void printBoard(int[][] board, int n) {
        timesPrinted++;
        System.out.print("   ");
        for (int col = 0; col < n; col++) {
            System.out.print(col);
            for (int i = 0; i < (3 - Integer.toString(col).length()); i++) {
                System.out.print(" ");
            }
        }
        System.out.println("");
        for (int row = 0; row < n; row++) {
            System.out.print(row + " ");
            for (int i = 0; i < (2 - Integer.toString(row).length()); i++) {
                System.out.print(" ");
            }

            for (int col = 0; col < n; col++) {
                if (board[row][col] == 1)
                    System.out.print(ANSI_RED);

                int mod = 0;
                if (row % 2 != 0)
                    mod = 1;
                if (col % 2 == mod) {
                    System.out.print("██ ");
                } else {
                    System.out.print("▒▒ ");
                }

                System.out.print(ANSI_RESET);
            }
            System.out.println("");
        }
        System.out.println("There are " + timesPrinted + " solutions with size " + n + "*" + n + ".");
    }

    private static boolean checkNW(int[][] board, int col, int row, int size) {
        for (int i = 0; i < size; i++) {
            if (row - i < 0 || col + i >= size)
                break;
            if (board[row - i][col + i] == 1)
                return true;
        }
        return false;
    }

    private static boolean checkNE(int[][] board, int col, int row, int size) {
        for (int i = 0; i < size; i++) {
            if (col - i < 0 || row - i < 0)
                break;
            if (board[row - i][col - i] == 1)
                return true;
        }
        return false;
    }

    private static boolean checkCol(int[][] board, int col, int size) {
        for (int i = 0; i < size; i++) {
            if (board[i][col] == 1)
                return true;
        }
        return false;

    }

    private static boolean checkRow(int[][] board, int row, int size) {
        for (int i = 0; i < size; i++) {
            if (board[row][i] == 1)
                return true;
        }
        return false;
    }

    private static class Position {
        int row;
        int col;

        public Position(int col, int row) {
            this.row = row;
            this.col = col;
        }
    }
}
