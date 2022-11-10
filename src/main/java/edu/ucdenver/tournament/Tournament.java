package edu.ucdenver.tournament;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;

public class Tournament {
    private String name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private ArrayList<Team> listTeams;
    private ArrayList<Country> participatingCountries;
    private ArrayList<Referee> listReferees;
    private ArrayList<Match> listMatches;

    public Tournament(String name, LocalDateTime startDate, LocalDateTime endDate){
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.listTeams = new ArrayList<>();
        this.participatingCountries = new ArrayList<>();
        this.listReferees = new ArrayList<>();
        this.listMatches = new ArrayList<>();
    }

    public void addTeam(String name, String country){
        Country teamCountry = null;
        try {
            for (Country c : participatingCountries) {
                if (c.getCountryName().equals(country)) {
                    teamCountry = c;
                }
            }
            Team team = new Team(name, teamCountry);
            listTeams.add(team);
        }
        catch(Exception e){
            System.err.println("Country not participating: " + e);
        }
    }

    public void addCountry(String country) throws IllegalArgumentException{
        Country c = new Country(country);
        if(this.participatingCountries.contains(c)){
            throw new IllegalArgumentException();
        }
        else{
            this.participatingCountries.add(c);
        }
    }

    public void addReferee(String name, String country) throws IllegalArgumentException{
        Country c = null;
        for(Country count: participatingCountries){
            if(count.getCountryName().equals(name)){
                c = count;
            }
        }
        for(Referee referee: listReferees){
            if(referee.getName().equals(name)){
                throw new IllegalArgumentException();
            }
            else{
                Referee ref = new Referee(name, c);
                listReferees.add(ref);
            }
        }
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

    public void addMatch(LocalDateTime dateTime, String teamAName, String teamBName){
        Team teamA = null;
        Team teamB = null;

        for(Team t: listTeams){
            if(t.getTeamName().equals(teamAName)){
                teamA = t;
            }
            if(t.getTeamName().equals(teamBName)){
                teamB = t;
            }
        }

        Match match = new Match(dateTime, teamA, teamB);
        listMatches.add(match);
    }

    /* Four referees are required for a match to take place.
        • The nationality of the referees must be validated. A referee’s nationality cannot be any of the matches' countries.
        • An exception is expected when these rules are violated, or either math or referee cannot be found in the system.*/

    public void addRefereeToMatch(LocalDateTime dateTime, String refereeName){
        String refsCountry = "";

        for (Referee ref : listReferees){                       // parse through referee list
            if (ref.getName().equals(refereeName)){             // if ref name in list -> ref found
                refsCountry = ref.getCountry().getCountryName();// save the name of the refs country

                for (Match m : listMatches){                    // now that we have a ref, parse thorough matches
                    if (m.getReferees().size() < 4){            // only do something if there is less than 4 refs
                        if (m.getDate() == dateTime) {          // since a ref can be added, lets find the match with date given

                            // Make sure the ref is not from either of their countries
                            if (!refsCountry.equals(m.getTeamA().getTeam().getCountry().getCountryName()) ||
                                    !refsCountry.equals(m.getTeamB().getTeam().getCountry().getCountryName())){
                                m.addReferee(ref);              // add the referee to the match
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    public void addPlayerToMatch(LocalDateTime dateTime, String teamName, String playerName){
        Team team = null;
        Player player = null;

        for(Team t: listTeams){
            if(t.getTeamName().equals(teamName)){
                team = t;
            }
        }
        for(Player p: team.getSquad()){
            if(p.getName().equals(playerName)){
                player = p;
            }
        }

        for(Match match: listMatches){
            if(match.getDate() == dateTime){
                match.addPlayer(player, team);
            }
        }

    }

    public void setMatchScore(LocalDateTime matchDate, int team1Score, int team2Score){
        Match m = null;
        for(Match match: listMatches){
            if(match.getDate() == matchDate){
                m = match;
            }
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

        int year = matchDate.getYear();
        Month month = matchDate.getMonth();
        int day = matchDate.getDayOfMonth();

        for(Match match: listMatches){
            if(match.getDate().getYear() == year && match.getDate().getMonth() == month && match.getDate().getDayOfMonth() == day){
                dateMatches.add(match);
            }
        }

        return dateMatches;

    }

    public ArrayList<Match> getMatchesFor(String teamName){
        ArrayList<Match> teamMatches = new ArrayList<>();
        for(Match match: listMatches){
            if(match.getTeamA().getTeam().getTeamName().equals(teamName) || match.getTeamB().getTeam().getTeamName().equals(teamName)){
                teamMatches.add(match);
            }
        }

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


}