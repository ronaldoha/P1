package code;

public class Country {
    private String name;
    private double x;
    private double y;

    Country(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public double getY(){
        return y;
    }

    public double getX(){
        return x;
    }
}
