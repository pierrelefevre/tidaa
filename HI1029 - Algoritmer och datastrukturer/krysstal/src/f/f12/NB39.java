package f.f12;

import java.util.Random;

public class NB39 {
    static Random rng;

    public static void main(String[] args) {
        System.out.println("NB39 - Factory");
        // Random generation
        //        var tasks = new ArrayList<Factory.Task>();
        //        rng = new Random();
        //
        //        for (int i = 0; i < 15; i++) {
        //            int startTime = startTime();
        //            tasks.add(new Factory.Task(startTime, endTime(startTime)));
        //        }

        //File reading
        var tasks = Factory.readTasks();

        System.out.println("\nTasks");
        System.out.println(tasks);

        System.out.println("\nWorkers needed: " + Factory.scheduleWorkers(tasks));
    }

    private static int startTime() {
        return 6 + rng.nextInt(11);
    }

    private static int endTime(int startTime) {
        int endTime = 0;
        while (endTime <= startTime) {
            endTime = 7 + rng.nextInt(11);
        }
        return endTime;
    }
}
