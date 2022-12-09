package edu.ucdenver.tournament;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The Team class is what is referenced for a LineUp to be made. A Team instance has a team name, country, and a list of players
 * but this time it is a list of 35 since it is the teams entire squad. A Squad is every player, including subs and say 1st
 * and 2nd string, but when a match is planned there are only 11 selected out of this group to play. In this class you get
 * Add a player, which must be done before trying to assign player in a LineUp, and get all the previous attributes I have
 * mentioned (country, team name, squad).
*/
public class Team implements Serializable {
    private String name;
    private Country country;
    private ArrayList<Player> squad;

    public Team(String name, Country country){
        this.name = name;
        this.country = country;
        this.squad = new ArrayList<>();
    }

    public Country getCountry(){
        return country;
    }

    public void addPlayer(String name, int age, double height, double weight){
        Player player = new Player(name, age, height, weight);
        this.squad.add(player);
    }

    public String getTeamName() {
        return name;
    }

    public ArrayList<Player> getSquad(){
        return this.squad;
    }
}