package edu.ucdenver.tournament;

import java.io.Serializable;

/**
 * The country class only has one variable, country name. It only has one method, other than the overridden toString, and
 * that method is gets the country name. This is used by the Match Class to ensure that the country of a referee is not
 * the same as the country of one of the teams in a match to ensure fairness. It is also of course used to keep a record of
 * the matches as well.
 * */
public class Country implements Serializable {
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