package f.f13;

import java.util.Arrays;

public class NB43 {
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String BLACK_BOLD_BRIGHT = "\033[1;90m"; // BLACK
    public static final String RED_BOLD_BRIGHT = "\033[1;91m";   // RED
    public static final String GREEN_BOLD_BRIGHT = "\033[1;92m"; // GREEN
    public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";// YELLOW
    public static final String BLUE_BOLD_BRIGHT = "\033[1;94m";  // BLUE
    public static final String PURPLE_BOLD_BRIGHT = "\033[1;95m";// PURPLE
    public static final String CYAN_BOLD_BRIGHT = "\033[1;96m";  // CYAN
    public static final String WHITE_BOLD_BRIGHT = "\033[1;97m"; // WHITE

    public static void main(String[] args) {
        System.out.println("NB43 - Change calculator");
        int[] values = new int[]{500, 200, 100, 50, 20, 10, 5, 2, 1};
        System.out.println("Values:\n" + Arrays.toString(values));
        int amount = 346;
        System.out.println("Changing " + amount + " to:");
        int[] result = Changer.change(values, amount);
        for (int i = 0; i < values.length; i++) {
            if (result[i] == 0)
                continue;
            if (values[i] > 9)
                System.out.print(ANSI_GREEN_BACKGROUND + WHITE_BOLD_BRIGHT + "  " + values[i] + "  " + ANSI_RESET);
            else
                System.out.print(ANSI_YELLOW_BACKGROUND + BLACK_BOLD_BRIGHT + " " + values[i] + " " + ANSI_RESET);
            System.out.println(" * " + result[i] + " = " + values[i] * result[i]);
        }
    }
}
