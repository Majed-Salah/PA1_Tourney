package edu.ucdenver.tournament;

import java.util.ArrayList;

public class Team {
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
        squad.add(player);
    }

    public String getTeamName() {
        return name;
    }

    public ArrayList<Player> getSquad(){
        return squad;
    }

    @Override
    public String toString(){
        return name;
    }
}