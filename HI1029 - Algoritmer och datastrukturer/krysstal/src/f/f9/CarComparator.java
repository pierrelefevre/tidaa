package f.f9;

import java.util.Comparator;

public class CarComparator implements Comparator<Car> {
    public int compare(Car a, Car b) {
        return a.getYear().compareTo(b.getYear());
    }
}