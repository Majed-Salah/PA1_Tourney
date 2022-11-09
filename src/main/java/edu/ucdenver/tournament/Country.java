package edu.ucdenver.tournament;

public class Country {
    private String countryName;

    public Country(String name){
        this.countryName = name;
    }

    public String getCountryName() {
        return countryName;
    }

    @Override
    public String toString(){
        return this.countryName;
    }

}