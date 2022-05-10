/*
    Rutten ges genom att route fylls med index för städerna i den ordning de besöks
    Det förutsätts att rutten börjar i staden med index 0 och route[0] ska sättas till 0.
    Sedan ska route[1] sättas till index för den stad som ligger närmast stad 0 osv.
    route har samma dimension som w och sista platsen i route: route[route.length-1] ska sättas
    till sista staden som besöks innan resan tillbaka till stad med index 0.
    minDistanceGreedy ska returnera längden på hela resan inklusive tillbaka till startstaden.
 */
package f.f13.NB44;

import java.util.ArrayList;
import java.util.List;

public class Skyline {
    public static ArrayList<Building> generateBuildings() {
        var testBuildings = new ArrayList<Building>();

        testBuildings.add(new Building(1, 5, 11));
        testBuildings.add(new Building(2, 7, 6));
        testBuildings.add(new Building(3, 9, 13));
        testBuildings.add(new Building(12, 16, 7));
        testBuildings.add(new Building(14, 25, 3));
        testBuildings.add(new Building(19, 22, 18));
        testBuildings.add(new Building(23, 29, 13));
        testBuildings.add(new Building(24, 28, 4));

        return testBuildings;
    }

    public static ArrayList<Point> skyline(List<Building> buildings) {
        ArrayList<Point> output = new ArrayList<>();
        int n = buildings.size();

        if (n == 0) return output;
        if (n == 1) {
            int xStart = buildings.get(0).x1;
            int xEnd = buildings.get(0).x2;
            int y = buildings.get(0).y;

            output.add(new Point(xStart, y));
            output.add(new Point(xEnd, 0));

            return output;
        }

        ArrayList<Point> leftSkyline, rightSkyline;
        leftSkyline = skyline(buildings.subList(0, n / 2));
        rightSkyline = skyline(buildings.subList(n / 2, n));


        var out = merge(leftSkyline, rightSkyline);

        return out;
    }

    public static ArrayList<Point> merge(List<Point> left, List<Point> right) {
        int nL = left.size(), nR = right.size();
        int pL = 0, pR = 0;
        int currY = 0, leftY = 0, rightY = 0;
        int x, maxY;
        ArrayList<Point> output = new ArrayList<>();

        //While >1 skylines are present
        while ((pL < nL) && (pR < nR)) {
            Point pointL = left.get(pL);
            Point pointR = right.get(pR);

            //Pick the smallest X
            if (pointL.x < pointR.x) {
                x = pointL.x;
                leftY = pointL.y;
                pL++;
            } else {
                x = pointR.x;
                rightY = pointR.y;
                pR++;
            }

            //Pick the largest Y
            maxY = Math.max(leftY, rightY);

            //If there was a change, update the output list
            if (currY != maxY) {
                update(output, x, maxY);
                currY = maxY;
            }
        }

        //Append left
        append(output, left, pL, nL, currY);

        //Append right
        append(output, right, pR, nR, currY);

        return output;
    }

    public static void update(List<Point> output, int x, int y) {
        output.add(new Point(x, y));
//
//        if (output.isEmpty() || output.get(output.size() - 1).x != x)
//        else {
////            Point p =
//            output.get(output.size() - 1).y = y;
////            p.y = y;
////            output.set(output.size() - 1, p);
//        }
    }

    public static void append(List<Point> output, List<Point> skyline, int p, int n, int currY) {
        while (p < n) {
            Point point = skyline.get(p);

            if (currY != point.y) {
                update(output, point.x, point.y);
                currY = point.y;
            }

            p++;
        }
    }

    public static class Building {
        int x1;
        int x2;
        int y;

        public Building(int x1, int x2, int y) {
            this.x1 = x1;
            this.x2 = x2;
            this.y = y;
        }

        @Override
        public String toString() {
            return "[x1: " + x1 +
                    ", x2: " + x2 +
                    ", y: " + y +
                    ']';
        }
    }


}





























