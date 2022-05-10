package lab.lab2;

import java.util.*;

public class U4 {
    public static void main(String[] args) {
        System.out.println("Small airport");
        Random rnd = new Random();

        var landingQ = new LinkedList<Integer>();
        int landingTot = 0;
        int landingCount = 0;
        int landingMax = 0;

        var departingQ = new LinkedList<Integer>();
        int departingTot = 0;
        int departingCount = 0;
        int departingMax = 0;

        int timeLeft = 0;

        for (int time = 0; time < 5259487; time += 5) {
            int rng = rnd.nextInt(100);
            if (rng < 5) {
                landingQ.offer(time);
            }
            rng = rnd.nextInt(100);
            if (rng < 5) {
                departingQ.offer(time);
            }
//
//            if (time == 0)
//                landingQ.offer(time);
//            if (time == 5)
//                departingQ.offer(time);

            if (timeLeft > 0)
                timeLeft -= 5;

            if (timeLeft > 0)
                continue;

            if (!landingQ.isEmpty()) {
                int waitingTime = time - landingQ.poll();
                landingTot += waitingTime;
                landingCount++;
                if (landingMax < waitingTime)
                    landingMax = waitingTime;
                timeLeft = 20;
            } else if (!departingQ.isEmpty()) {
                int waitingTime = time - departingQ.poll();
                departingTot += waitingTime;
                departingCount++;
                if (departingMax < waitingTime)
                    departingMax = waitingTime;
                timeLeft = 20;
            }
        }

        double departingAvg = (double) departingTot / (double) departingCount;
        double landingAvg = (double) landingTot / (double) landingCount;

        System.out.println("Landing avg:\t" + landingAvg + "\t Landing max:\t" + landingMax + " minutes");
        System.out.println("Departing avg:\t" + departingAvg + "\t Departing max:\t" + departingMax + " minutes");
    }
}
