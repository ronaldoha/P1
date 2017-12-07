package code;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

class Parser {

    private ArrayList<Country> countryList = new ArrayList<>();
    private ArrayList<String> countryNameList = new ArrayList<>();
    private String path = "CountryList.txt";

    Parser() {
        File file = new File(path);
        try {
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split(";");
                Country country = new Country(values[0], Integer.parseInt(values[1]), Integer.parseInt(values[2]));
                countryList.add(country);
                countryNameList.add(country.getName());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    ArrayList getObjects() {
        return countryList;
    }

    ArrayList getNames() {
        return countryNameList;
    }

}
