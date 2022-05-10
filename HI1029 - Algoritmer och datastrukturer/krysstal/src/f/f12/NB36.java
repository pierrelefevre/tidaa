package f.f12;

import java.util.ArrayList;
import java.util.Random;

public class NB36 {
    static Random rng;

    public static void main(String[] args) {
        System.out.println("NB36 - Scheduler");
        var activities = new ArrayList<Scheduler.Activity>();
        rng = new Random();

        for (int i = 0; i < 15; i++) {
            int startTime = startTime();
            activities.add(new Scheduler.Activity(startTime, endTime(startTime), name()));
        }
        System.out.println("\nPossible activities");
        System.out.println(activities);

        var schedule = Scheduler.solve(activities);
        System.out.println("\nFinished schedule");
        System.out.println(schedule);
    }

    private static String name() {
        int name = rng.nextInt(5);
        switch (name) {
            case 0:
                return "Math";
            case 1:
                return "English";
            case 2:
                return "Physics";
            case 3:
                return "Programming";
            case 4:
                return "Biology";
        }
        return "Swedish";
    }

    private static int startTime() {
        return 8 + rng.nextInt(7);
    }

    private static int endTime(int startTime) {
        int endTime = 0;
        while (endTime <= startTime) {
            endTime = 8 + rng.nextInt(8);
        }
        return endTime;
    }
}
