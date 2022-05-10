package f.f9;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Car implements Comparable<Car> {
    private String make;
    private String year;
    private String miles;

    public Car(String make, String year, String miles) {
        this.make = make;
        this.year = year;
        this.miles = miles;
    }

    public static void generateTestCars() {
        try {
            var fileWriter = new FileWriter("C:\\Users\\hello\\Desktop\\algodat\\krysstal\\src\\f.f9\\cars.txt");
            var makes = new String[]{"BMW", "Volvo", "Audi", "Mercedes-Benz", "Renault", "Alpine", "Volkswagen", "Seat", "Jeep", "Fiat", "Dodge", "Honda", "Subaru", "Kia", "Toyota", "Porsche", "Mini", "Opel", "Jaguar", "Lexus", "Aston-Martin", "McLaren"};
            var rng = new Random();
            for (int i = 0; i < makes.length * 50; i++) {
                fileWriter.write(makes[rng.nextInt(makes.length)] + " " + (1999 + rng.nextInt(23)) + " " + rng.nextInt(100000) + "\n");
            }

            fileWriter.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void writeCars(ArrayList<Car> cars, String path) throws IOException {
        FileWriter fileWriter = new FileWriter("C:\\Users\\hello\\Desktop\\algodat\\krysstal\\src\\f.f9\\result" + path + ".txt");

        for (Car c : cars) {
            fileWriter.write(c.make + " " + c.year + " " + c.miles + "\n");
        }
        fileWriter.close();
    }

    public static ArrayList<Car> readCars() throws IOException {
        var f = new File("C:\\Users\\hello\\Desktop\\algodat\\krysstal\\src\\f.f9\\cars.txt");
        var myReader = new Scanner(f);
        var cars = new ArrayList<Car>();
        while (myReader.hasNextLine()) {
            var data = myReader.nextLine();
            var chunks = data.split(" ");
            cars.add(new Car(chunks[0], chunks[1], chunks[2]));
        }

        myReader.close();
        return cars;
    }

    @Override
    public String toString() {
        return make + " " + year + " " + miles;
    }

    public String getMake() {
        return make;
    }

    public String getYear() {
        return year;
    }

    public String getMiles() {
        return miles;
    }

    public int compareTo(Car c) {
        return make.compareTo(c.getMake());
    }
}
