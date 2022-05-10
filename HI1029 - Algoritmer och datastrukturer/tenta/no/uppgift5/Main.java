package uppgift5;

public class Main {
    public static void main(String[] args) {
        System.out.println(maxValue(20, new int[]{13, 11, 10, 5, 4}, new int[]{9, 8, 7, 4, 3})); //28
        System.out.println(maxValue(117, new int[]{13, 11, 10, 5, 4}, new int[]{9, 8, 7, 4, 3})); //169
        System.out.println(maxValue(30, new int[]{10, 22}, new int[]{10, 21})); //30
    }

    static int maxValue(int sackSize, int[] values, int[] weights) {
        int[] sacks = new int[sackSize + 1];

        //For all sacks, check which ones will fit
        for (int sack = 0; sack <= sackSize; sack++)
            //Check for all boxes in the list if they fit in the current sack
            for (int box = 0; box < weights.length; box++)
                //If the box fits in the current sack
                if (weights[box] <= sack)
                    //Put the package if it increases the sack value
                    sacks[sack] = Math.max(sacks[sack],
                            sacks[sack - weights[box]] + values[box]);

        return sacks[sackSize];
    }
}