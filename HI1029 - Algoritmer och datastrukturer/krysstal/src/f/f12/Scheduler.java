package f.f12;

import java.util.ArrayList;
import java.util.Comparator;

public class Scheduler {
    public static ArrayList<Activity> solve(ArrayList<Activity> activities) {
        activities.sort(new Activity.ActivityComparator());
        ArrayList<Activity> schedule = new ArrayList<>();

        while (!activities.isEmpty()) {
            Activity current = activities.remove(0);
            schedule.add(current);

            int endTime = current.getEndTime();
            activities.removeIf(check -> check.startTime < endTime);
        }

        return schedule;
    }

    public static class Activity {
        private final int startTime;
        private final int endTime;
        private final String name;

        public Activity(int startTime, int endTime, String name) {
            this.startTime = startTime;
            this.endTime = endTime;
            this.name = name;
        }

        public int getStartTime() {
            return startTime;
        }

        public int getEndTime() {
            return endTime;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name + " " + startTime + ":00-" + endTime + ":00";
        }

        public static class ActivityComparator implements Comparator<Activity> {
            @Override
            public int compare(Activity t1, Activity t2) {
                return t1.endTime - t2.endTime;
            }
        }
    }
}
