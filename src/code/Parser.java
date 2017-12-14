package code;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

class Parser {

    void parseCountries() {
        File file = new File("CountryList.txt");
        ArrayList<String> nameList = new ArrayList<>();
        ArrayList<Country> objectList = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split(";");
                Country country = new Country(values[0], Integer.parseInt(values[1]), Integer.parseInt(values[2]));
                objectList.add(country);
                nameList.add(country.getName());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        App.countryNameList = nameList;
        App.countryList = objectList;
    }

    void parseUsers() {
        File file = new File("UserList.txt");
        ArrayList<String> nameList = new ArrayList<>();
        ArrayList<User> objectList = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split(";");
                User user = new User(values[0], values[1]);
                objectList.add(user);
                nameList.add(user.getUsername());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        App.userNameList = nameList;
        App.userList = objectList;
    }


}
