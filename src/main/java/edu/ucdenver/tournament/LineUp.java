package edu.ucdenver.tournament;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * LineUp Class
 * The LineUp class, which is utilized by the Match Class above, is one of the simplest classes. Its entire functionality
 * is storing a list of players, a single team, and some functions to get the team and players or add a player. LineUp's
 * are matched up against each other within a Match so that two teams of 11 can play against each other.
* */
public class LineUp implements Serializable {
    List<Player> listOfPlayers = new ArrayList<>();
    Team team; // not sure about this

    public LineUp(Team team){
        this.team = team;
    }

    public Team getTeam(){
        return team;
    }

    // TODO when is the lineup getting created
    public void addPlayer(Player player){
        try {
            if (this.team.getSquad().contains(player)) {
                listOfPlayers.add(player);
            }
        }
        catch (Exception e){
            System.err.println(e);
        }

    }

    public List<Player> getPlayers(){
        return listOfPlayers;
    }

    public String toString(){
        String s = "";
        for(Player p : listOfPlayers){
            s += p.getName() + ";";
        }
        return s;
    }
}