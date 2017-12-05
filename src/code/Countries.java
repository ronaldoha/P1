package code;

import java.io.*;
import java.util.ArrayList;

class Country implements Serializable
{
    String name;

    public Country(String name)
    {
        this.name = name;
    }

    @Override
    public String toString() { return "Country: " + name; }

}
public class Countries
{

    public static void main(String[] args)
    {
        ArrayList<Countries> countries;
        countries = new ArrayList<Countries>();
        Country Italy = new Country("Italy");
        Country Greece = new Country("Greece");
        Country Spain = new Country("Spain");
        Country Lithuania = new Country("Lithuania");

        try {
            writeToFile(Italy, countries);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        try {
            writeToFile(Lithuania, countries);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        try {
            writeToFile(Spain, countries);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        try {
            writeToFile(Greece, countries);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        try {
            addAll(countries);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            readFile(countries);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void writeToFile(Country c, ArrayList countries) throws IOException { countries.add(c); }

    public static void addAll(ArrayList countries) throws IOException {
        FileOutputStream out = new FileOutputStream("CountryList.bin");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
        objectOutputStream.writeObject(countries);

    }

    public static void readFile(ArrayList countries) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("CountryList.bin"));
        countries = (ArrayList<Countries>)objectInputStream.readObject();
        System.out.println(countries);
    }

}
