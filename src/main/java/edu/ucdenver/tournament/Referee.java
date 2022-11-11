package edu.ucdenver.tournament;

public class Referee {
    private String name;
    private Country country;

    public Referee(String name, Country country){
        this.name = name;
        this.country = country;
    }

    public Country getCountry(){
        return country;
    }

    public String getName(){
        return name;
    }

    @Override
    public String toString(){
        return name + " (" + country +")";
    }
}