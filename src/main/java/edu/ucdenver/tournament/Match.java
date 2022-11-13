package edu.ucdenver.tournament;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Match {
    private LocalDateTime dateTime;
    private int scoreTeamA;
    private int scoreTeamB;
    private ArrayList<Referee> matchReferees;
    private Team teamA;
    private Team teamB;
    private LineUp lineUpA = new LineUp(teamA);
    private LineUp lineUpB = new LineUp(teamB);

    /**
     * Match Class Constructor:
     */
    public Match(LocalDateTime dateTime, Team teamA, Team teamB){
        this.dateTime = dateTime;
        this.teamA = teamA;
        this.teamB = teamB;
    }

    /**
     * getTeamA Function:
     */
    public LineUp getTeamA(){
        return lineUpA;
    }

    /**
     * getTeamB Function:
     */
    public LineUp getTeamB(){
        return lineUpB;
    }

    /**
     * isUpcoming Function:
     */
    public boolean isUpcoming(){
        return LocalDateTime.now().isBefore(dateTime);
    }

    /**
     * Add Player Function:
     * Within the instance of the Match class, the addPlayer function can be passed
     * a player and team instance. The function will add a player to the specified teams LineUp.
     */
    public void addPlayer(Player player, Team team){
        if (team == this.teamA) {
            getTeamA().addPlayer(player);
        }
        else if (team == this.teamB) {
            getTeamB().addPlayer(player);
        }
    }

    /**
     * getReferees Function:
     */
    public List<Referee> getReferees(){
        return matchReferees;
    }

    /**
     * addReferee Function:
     */
    public void addReferee(Referee ref){
        // TODO Need to handle exception if already exists
        matchReferees.add(ref);
    }

    /**
     * setMatchScore Function:
     */
    public void setMatchScore(int scoreA, int scoreB){
        this.scoreTeamA = scoreA;
        this.scoreTeamB = scoreB;
    }

    /**
     * getDate Function:
     * Implemented so that the Tournament class's function setMatchScore, addPlayerToMatch, addRefereeToMatch
     * could identify the dateTime of a match.
     */
    public LocalDateTime getDate(){
        return dateTime;
    }

}