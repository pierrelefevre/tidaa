/*
    Rutten ges genom att route fylls med index för städerna i den ordning de besöks
    Det förutsätts att rutten börjar i staden med index 0 och route[0] ska sättas till 0.
    Sedan ska route[1] sättas till index för den stad som ligger närmast stad 0 osv.
    route har samma dimension som w och sista platsen i route: route[route.length-1] ska sättas
    till sista staden som besöks innan resan tillbaka till stad med index 0.
    minDistanceGreedy ska returnera längden på hela resan inklusive tillbaka till startstaden.
 */
package tenta.skyline;

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

    public static List<Point> skyline(List<Building> buildings) {
        if (buildings.size() == 0)
            return new ArrayList<Point>();

        if (buildings.size() == 1) {
            ArrayList<Point> points = new ArrayList<>();
            Building b = buildings.get(0);

            points.add(new Point(b.x1, b.y));
            points.add(new Point(b.x2, 0));

            return points;
        }

        var left = skyline(buildings.subList(0, buildings.size() / 2));
        var right = skyline(buildings.subList(buildings.size() / 2, buildings.size()));

        return merge(left, right);
    }

    public static List<Point> merge(List<Point> left, List<Point> right) {
        int pL = 0, pR = 0, currY = 0, leftY = 0, rightY = 0, x, maxY = 0;
        var merged = new ArrayList<Point>();

        while (pL < left.size() && pR < right.size()) {
            Point pointL = left.get(pL);
            Point pointR = right.get(pR);

            if (pointL.x < pointR.x) {
                x = pointL.x;
                leftY = pointL.y;
                pL++;
            } else {
                x = pointR.x;
                rightY = pointR.y;
                pR++;
            }

            maxY = Math.max(leftY, rightY);

            if (currY != maxY) {
                merged.add(new Point(x, maxY));
                currY = maxY;
            }
        }

        int tempY = currY;
        for (int i = pL; i < left.size(); i++) {
            Point p = left.get(i);
            if (p.y != tempY) {
                merged.add(p);
                tempY = p.y;
            }
        }

        tempY = currY;
        for (int i = pR; i < right.size(); i++) {
            Point p = right.get(i);
            if (p.y != tempY) {
                merged.add(p);
                tempY = p.y;
            }
        }

        return merged;
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





























