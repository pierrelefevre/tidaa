package f.f6;

public class NB19 {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static void main(String[] args) {
        System.out.println("NB19");
        BrickGame b = new BrickGame();
        System.out.println(b.solve(b.bricks, 0));
    }

    private static class BrickGame {
        int[] bricks = new int[7];
        private int[] correct = new int[7];

        public BrickGame() {
            bricks[0] = 1;
            bricks[1] = 1;
            bricks[2] = 1;
            bricks[3] = 0;
            bricks[4] = 2;
            bricks[5] = 2;
            bricks[6] = 2;

            correct[0] = 2;
            correct[1] = 2;
            correct[2] = 2;
            correct[3] = 0;
            correct[4] = 1;
            correct[5] = 1;
            correct[6] = 1;
        }

        private int findEmpty(int[] test) {
            for (int i = 0; i < test.length; i++) {
                if (test[i] == 0)
                    return i;
            }
            return -1;
        }

        private int[] move(int[] test, int index1, int index2) {
            int[] attempt = new int[test.length];
            for (int i = 0; i < test.length; i++) {
                attempt[i] = test[i];
            }
            int tmp = attempt[index1];
            attempt[index1] = attempt[index2];
            attempt[index2] = tmp;

            return attempt;
        }

        public boolean solve(int[] test, int moves) {

            if (moves > 15) {
                return false;
            }

            if (isSolved(test)) {
                System.out.println(toString(test));
                return true;
            }


            int empty = findEmpty(test);

            if (empty < test.length - 1) {
                //Test right 1
                if (solve(move(test, empty, empty + 1), moves + 1)) {
                    System.out.println(toString(test));
                    return true;
                }
            }

            if (empty > 0) {
                //Test left 1
                if (solve(move(test, empty, empty - 1), moves + 1)) {
                    System.out.println(toString(test));
                    return true;
                }
            }

            if (empty < test.length - 2) {
                //Test right 2
                if (solve(move(test, empty, empty + 2), moves + 1)) {
                    System.out.println(toString(test));
                    return true;
                }
            }

            if (empty > 1) {
                //Test left 2
                if (solve(move(test, empty, empty - 2), moves + 1)) {
                    System.out.println(toString(test));
                    return true;
                }
            }

            return false;
        }

        private boolean isSolved(int[] test) {
            for (int i = 0; i < test.length; i++) {
                if (test[i] != correct[i])
                    return false;
            }
            return true;
        }

        public String toString(int[] test) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < test.length; i++) {
                switch (test[i]) {
                    case 1:
                        sb.append(ANSI_BLACK + "⚫" + ANSI_RESET);
                        break;

                    case 2:
                        sb.append(ANSI_WHITE + "⚫" + ANSI_RESET);
                        break;

                    default:
                        sb.append(" ");
                }
            }
            return sb.toString();
        }
    }
}