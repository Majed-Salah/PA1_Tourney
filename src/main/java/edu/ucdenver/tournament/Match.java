package edu.ucdenver.tournament;


import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Match Class:
 * A Match consists of a Date, Time, and Pair of Teams. Matches are correlated with a LineUp, which contains all the
 * players that are participating in the Match (11/35 in squad to be specific). We also have functions to add players to
 * a team, add Referee's to a match, update the match score, and a few other basic getter and setter functions.
 */
public class Match implements Serializable {
    private LocalDateTime dateTime;
    private int scoreTeamA;
    private int scoreTeamB;
    private ArrayList<Referee> matchReferees = new ArrayList<>();
    private Team teamA;
    private Team teamB;
    private LineUp lineUpA ;
    private LineUp lineUpB ;


    public Match(LocalDateTime dateTime, Team teamA, Team teamB){
        this.dateTime = dateTime;
        this.teamA = teamA;
        this.teamB = teamB;
        this.lineUpA = new LineUp(teamA);
        this.lineUpB = new LineUp(teamB);
    }

    /**
     * getTeamA Function:
     */
    public LineUp getTeamA(){
        return this.lineUpA;
    }

    /**
     * getTeamB Function:
     */
    public LineUp getTeamB(){
        return this.lineUpB;
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
        if (matchReferees != null){ // if there are existing match referees
            if(matchReferees.contains(ref)){ // check that this ref isnt in matches referee list
                throw new IllegalArgumentException("Referee already in match."); // if it is, throw an error
            }
        }
        // if we made it past above check, add the referee
        this.matchReferees.add(ref);
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

    public Team getTeamATeam(){ return teamA; }
    public Team getTeamBTeam(){ return teamB; }

    public String getMatchScore(){
        return " (" + this.scoreTeamA + " - " + this.scoreTeamB + ")";
    }

    @Override
    public String toString(){
        return this.dateTime + " -- " + teamA.getTeamName() + " AGAINST " +  teamB.getTeamName();
    }

}