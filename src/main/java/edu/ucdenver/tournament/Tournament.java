package edu.ucdenver.tournament;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;

/**
 * Tournament Class
 * The tournament class wraps all the different parts together with functions that leverage all the functions
 * in the remaining classes below. The main purpose of the functions in this class are to get and set tournament
 * variables by utilizing the functions within all the objects that are used in a tournament.
 */
public class Tournament implements Serializable {
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private ArrayList<Team> listTeams;
    private ArrayList<Country> participatingCountries;
    private ArrayList<Referee> listReferees;
    private ArrayList<Match> listMatches;
    public static final String filename = "./tournament.ser";


    public Tournament(String name, LocalDate startDate, LocalDate endDate){
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.listTeams = new ArrayList<>();
        this.participatingCountries = new ArrayList<>();
        this.listReferees = new ArrayList<>();
        this.listMatches = new ArrayList<>();
    }

    public void addTeam(String name, String country) throws IllegalArgumentException{
        Country teamCountry = null;
        for (Country c : participatingCountries) {
            if (c.getCountryName().equals(country)) {
                teamCountry = c;
            }
        }

        if(teamCountry == null){
            throw new IllegalArgumentException("Country not participating in tournament.");
        }

        for(Team team: listTeams){
            if(team.getTeamName().equals(name)){
                throw new IllegalArgumentException("Team name is already taken for tournament.");
            }
        }

        Team team = new Team(name, teamCountry);
        listTeams.add(team);

    }

    public void addCountry(String country) throws IllegalArgumentException{
        Country c = new Country(country);
        for(Country count: participatingCountries){
            if(count.getCountryName().equals(country)){
                throw new IllegalArgumentException("Country already in tournament");
            }
        }
        this.participatingCountries.add(c);
    }

    public void addReferee(String name, String country) throws IllegalArgumentException{
        Country c = null;
        for(Country count: participatingCountries){
            if(count.getCountryName().equals(country)){
                c = count;
            }
        }

        if(c == null){
            throw new IllegalArgumentException("Country not in tournament.");
        }

        for(Referee referee: listReferees){
            if(referee.getName().equals(name)){
                throw new IllegalArgumentException("Referee name already taken.");
            }
        }

        Referee ref = new Referee(name, c);
        listReferees.add(ref);

    }

    public void addPlayer(String teamName, String playerName, int age, double height, double weight){
        Team team = null;
        for(Team t: listTeams){
            if(t.getTeamName().equals(teamName)){
                team = t;
            }
        }
        team.addPlayer(playerName, age, height, weight);
    }

    public void addMatch(LocalDateTime dateTime, String teamAName, String teamBName) throws IllegalArgumentException{
        Team teamA = null;
        Team teamB = null;
        System.out.println(dateTime);

        for (Team t : listTeams) {
            if (t.getTeamName().equals(teamAName)) {
                teamA = t;
            }
            if (t.getTeamName().equals(teamBName)) {
                teamB = t;
            }
        }

        if(teamAName.equals(teamBName)){
            throw new IllegalArgumentException("Team can't play against themselves.");
        }

        if(teamA.getCountry() == teamB.getCountry()){
            throw new IllegalArgumentException("Teams are of the same country.");
        }

        Match match = new Match(dateTime, teamA, teamB);
        listMatches.add(match);
        for(Match m : listMatches){
            System.out.println(m);
        }
    }

    public void addRefereeToMatch(LocalDateTime dateTime, String refereeName){
        Match match = null;
        Referee ref = null;

        for(Match m: listMatches){
            if(m.getDate() == dateTime){
                match = m;
            }
        }

        Country t1 = match.getTeamATeam().getCountry();
        Country t2 = match.getTeamBTeam().getCountry();

        if(match == null){
            throw new IllegalArgumentException("Match at this time is not set.");
        }

        for(Referee r: match.getReferees()){
            if(r.getName().equals(refereeName)){
                ref = r;
            }
            if(ref.getCountry() == t1 || ref.getCountry() == t2){
                throw new IllegalArgumentException("Cannot assign referee representing same country as a team");
            }
        }

        match.addReferee(ref);

    }

    public void addPlayerToMatch(LocalDateTime dateTime, String teamName, String playerName) throws IllegalArgumentException{
        Match match = null;
        Team team;
        Player player = null;

        for(Match m: listMatches){
            if(m.getDate().compareTo(dateTime) == 0){
                match = m;
            }
        }

        if(match == null){
            throw new IllegalArgumentException("No match at particular time.");
        }

        if(teamName.equals(match.getTeamATeam().getTeamName())){
            System.out.println(match.getTeamATeam().getTeamName());
            team = match.getTeamATeam();
        }
        else if(teamName.equals(match.getTeamBTeam().getTeamName())){
            team = match.getTeamBTeam();
        }
        else{
            throw new IllegalArgumentException("Team not in match.");
        }

        for(Player p: team.getSquad()){
            if(p.getName().equals(playerName)){
                player = p;
            }
        }

        if(player == null){
            throw new IllegalArgumentException("Player not in team.");
        }

        match.addPlayer(player, team);

    }

    public void setMatchScore(LocalDateTime matchDate, int team1Score, int team2Score) throws IllegalArgumentException{
        Match m = null;
        for(Match match: listMatches){
            // check for dates and see if the same
            if(match.getDate() == matchDate){
                m = match;
            }
        }
        if(m == null){
            throw new IllegalArgumentException("No match at this date/time");
        }
        m.setMatchScore(team1Score, team2Score);
    }

    public ArrayList<Match> getUpcomingMatches(){
        ArrayList<Match> upcomingMatches = new ArrayList<>();
        for(Match match: listMatches){
            if(match.isUpcoming()){
                upcomingMatches.add(match);
            }
        }

        return upcomingMatches;

    }

    public ArrayList<Match> getMatchesOn(LocalDate matchDate){
        ArrayList<Match> dateMatches = new ArrayList<>();

        for(Match match: listMatches){
            System.out.println(match);
            if(match.getDate().toLocalDate().equals(matchDate)){
                System.out.println(match.getDate().toLocalDate());
                dateMatches.add(match);
            }
        }

        return dateMatches;

    }

    public ArrayList<Match> getMatchesFor(String teamName){
        ArrayList<Match> teamMatches = new ArrayList<>();
        for(Match match: listMatches){
            // go through names and see what is different
            if(match.getTeamATeam().getTeamName().equals(teamName) || match.getTeamBTeam().getTeamName().equals(teamName)){
                teamMatches.add(match);
            }
        }

        System.out.println(teamMatches);
        return teamMatches;

    }

    public ArrayList<LineUp> getMatchLineUps(LocalDateTime matchDate){
        ArrayList<LineUp> matchLineUp = new ArrayList<>();
        for(Match match: listMatches){
            if(match.getDate() == matchDate){
                matchLineUp.add(match.getTeamA());
                matchLineUp.add(match.getTeamB());
            }
        }

        return matchLineUp;

    }

    public ArrayList<Country> getListCountries(){
        return participatingCountries;
    }


    public ArrayList<Team> getTeams() {
        return listTeams;
    }

    public void saveToFile(){

        ObjectOutputStream oos = null;

        try{
            oos = new ObjectOutputStream(new FileOutputStream(filename));
            oos.writeObject(this);
        }
        catch (IOException ioe){
            ioe.printStackTrace();

        }
        finally{
            if(oos != null){
                try{
                    oos.close();
                }
                catch (IOException ioe){
                    ioe.printStackTrace();
                }
            }
        }
    }

    public static Tournament loadFromFile() throws FileNotFoundException{
        ObjectInputStream ois = null;
        Tournament tournament = null;

        try{
            ois = new ObjectInputStream(new FileInputStream(Tournament.filename));
            tournament = (Tournament) ois.readObject();
        }
        catch (Exception e){
            e.printStackTrace();
            throw new FileNotFoundException("Missing file to load");
        }
        finally{
            if (ois != null){
                try{
                    ois.close();
                }
                catch (IOException ioe){
                    ioe.printStackTrace();
                }
            }
        }

        return tournament;
    }

    public void checkRefereeForMatch(LocalDateTime dateTime, String refereeName){
        Match match = null;
        for(Match m: listMatches){
            if(m.getDate() == dateTime){
                match = m;
            }
        }

        Country t1 = match.getTeamATeam().getCountry();
        Country t2 = match.getTeamBTeam().getCountry();

        if(match == null){
            throw new IllegalArgumentException("Match at this time is not set.");
        }

        // check if referees are the same
        for(Referee ref: match.getReferees()){
            if(ref.getCountry() == t1 || ref.getCountry() == t2){
                throw new IllegalArgumentException("Cannot assign referee representing same country as a team");
            }
        }
    }

    public ArrayList<Match> getListMatches(){
        return this.listMatches;
    }

}