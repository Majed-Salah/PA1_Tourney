package edu.ucdenver.tournament;

/**
 * The Referee class has variables for a Referee's name and country of origin. As mentioned in the Country class description,
 * this country variable is used that we can ensure that a Referee isnt from the same country as a team in a match. And the
 * referee name variable is simply to keep a record of referees.
 * */
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
}