package scania;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class DataGetter {
    private ArrayList<Cargo> cargo;
    public DataGetter() {
        cargo = new ArrayList<>();
    }

    public ArrayList<Cargo> getData(String fileName){
        try {
            File myObj = new File(fileName);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] dataBits = data.split(" ");
                Cargo tempCargo = new Cargo(Integer.parseInt(dataBits[0]), Integer.parseInt(dataBits[1]));
                cargo.add(tempCargo);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File error: " + e.getMessage());
        }

        return cargo;
    }
}
