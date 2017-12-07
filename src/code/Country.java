package code;

class Country {
    private String name;
    private int x;
    private int y;

    Country(String name, int x, int y){
        this.name = name;
        this.x = x;
        this.y = y;
    }

    String getName(){
        return name;
    }

    double getY(){
        return y;
    }

    double getX(){
        return x;
    }
}
