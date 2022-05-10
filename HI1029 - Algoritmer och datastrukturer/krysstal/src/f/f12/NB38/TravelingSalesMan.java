/*
    Rutten ges genom att route fylls med index för städerna i den ordning de besöks
    Det förutsätts att rutten börjar i staden med index 0 och route[0] ska sättas till 0.
    Sedan ska route[1] sättas till index för den stad som ligger närmast stad 0 osv.
    route har samma dimension som w och sista platsen i route: route[route.length-1] ska sättas
    till sista staden som besöks innan resan tillbaka till stad med index 0.
    minDistanceGreedy ska returnera längden på hela resan inklusive tillbaka till startstaden.
 */

package f.f12.NB38;


import java.util.BitSet;

public class TravelingSalesMan {


    public static int minDistanceGreedy(int[][] w, int[] route) {
        //använd helst et BitSet för att markera vilka du besökt
        //Välj en startstad
        //Medans det finns obesökta städer
        //  Res till den stad som ligger närmast den du befinner dig i
        //Res till startstaden

        int totalDistance = 0;
        var visited = new BitSet();

        //Choose a start position
        int currentCity = 0;
        visited.set(currentCity);

        //While there are unvisited cities
        int lastCity = -1;
        for (int n = 1; n < w.length; n++) {
            double shortest = Double.POSITIVE_INFINITY;

            //Find the closest one
            int closestCity = -1;

            for (int i = 0; i < w.length; i++) {
                if (!visited.get(i)) {
                    if (w[i][currentCity] < shortest) {
                        shortest = w[i][currentCity];
                        closestCity = i;
                    }
                }
            }

            //Apply the new city
            visited.set(closestCity);
            currentCity = closestCity;
            route[n] = currentCity;
            totalDistance += shortest;
            lastCity = currentCity;
        }

        //Add the last distance for going back home
        totalDistance += w[lastCity][0];

        return totalDistance;
    }


}
