package f.f12;

import java.util.ArrayList;
import java.util.Collections;

public class NumberLine {
    public static int steps(ArrayList<Double> numbers) {
        if (numbers.size() < 2)
            return 1;

        Collections.sort(numbers);
        System.out.println(numbers);

        int steps = 0;
        int totalSteps = 0;
        double limit = numbers.get(0);
        for (int i = 0; i < numbers.size(); i++) {
            if (numbers.get(i) <= limit)
                continue;
            while (numbers.get(i) - limit > steps * 2) {
                steps++;
                totalSteps++;
            }
            limit = numbers.get(i) + steps * 2;
            steps = 0;
        }
        return totalSteps;
    }
}
