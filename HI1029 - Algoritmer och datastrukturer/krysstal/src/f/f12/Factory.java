package f.f12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;

public class Factory {
    public static int scheduleWorkers(ArrayList<Task> tasks) {
        System.out.println();
        if (tasks.size() <= 1)
            return tasks.size();

        tasks.sort(new Task.TaskComparator());

        int maxWorkers = 1;
        int currentTask = 0;
        int currentWorkers = 1;

        for (int i = 1; i < tasks.size(); i++) {
            while (tasks.get(currentTask).end <= tasks.get(i).start) {
                System.out.println("Should remove " + (currentWorkers - 1));
                currentWorkers--;
                currentTask++;
            }

            currentWorkers++;
            if (currentWorkers > maxWorkers)
                maxWorkers = currentWorkers;
            System.out.println("Task " + i + " " + tasks.get(i) + ", Current " + currentWorkers + ", Max " + maxWorkers);
        }

        return maxWorkers;
    }

    public static ArrayList<Task> readTasks() {
        try {
            String filepath = "C:\\Users\\hello\\Desktop\\algodat\\krysstal\\src\\f.f12\\tasks.txt";
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            String buffer = "";
            ArrayList<Task> tasks = new ArrayList<>();
            boolean firstRow = true;
            while ((buffer = br.readLine()) != null) {
                String[] strings = buffer.split(" ");
                tasks.add(new Task(Integer.parseInt(strings[0]), Integer.parseInt(strings[1])));
            }

            return tasks;

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static class Task {
        int start;
        int end;

        public Task(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public String toString() {
            return "[" + start + "-" + end + "]";
        }

        public static class TaskComparator implements Comparator<Task> {

            @Override
            public int compare(Task t1, Task t2) {
                return t1.start - t2.start;
            }
        }
    }
}
