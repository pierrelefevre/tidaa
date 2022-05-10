package round1;

import java.util.Arrays;

public class Challenge1 {

    public static void main(String[] args) {
        String s = "92467289257150275856161684991095581259413877076801199292088228715266891643229450818090528816028515270990954507362830972564807552571317981703601155328753371618349102594584916886265213472704637246340955";

        // int nums[] = new int[]{2, 7, 8, 2, 6, 1, 6, 3};

        int nums[] = new int[s.length()];

        for (int i = 0; i < s.length(); i++) {
            nums[i] = Integer.parseInt(String.valueOf(s.charAt(i)));
        }

        System.out.println(Arrays.toString(nums));

        int errors = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] % 2 != i % 2)
                errors++;
        }
        System.out.println("Errors: " + errors);

    }
}

// helo