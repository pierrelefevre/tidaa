package f.f9;

import java.util.ArrayList;
import java.util.Collections;

public class NB27 {
    public static void main(String[] args) {

//        Car.generateTestCars();

        try {
            ArrayList<Car> cars = Car.readCars();
            Collections.sort(cars);
            for (Car c : cars) {
                Car.writeCars(cars, "1");
            }

            System.out.println("");
            System.out.println("");
            System.out.println("");
            System.out.println("");

            ArrayList<Car> cars2 = Car.readCars();
            Collections.sort(cars2, new CarComparator());
            Car.writeCars(cars2, "2");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
