package edu.ucdenver.tournament;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

public class Tournament {
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private ArrayList<Team> listTeams;
    private ArrayList<Country> participatingCountries;
    private ArrayList<Referee> listReferees;
    private ArrayList<Match> listMatches;

    public Tournament(String name, LocalDate startDate, LocalDate endDate){
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
        for(Country count: participatingCountries){
            if(count.getCountryName().equals(country)){
                throw new IllegalArgumentException();
            }
        }
        this.participatingCountries.add(c);
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

    public void addMatch(LocalDate dateTime, String teamAName, String teamBName){
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

    public void addRefereeToMatch(LocalDate dateTime, String refereeName){
        Match match = null;

    }

    public void addPlayerToMatch(LocalDate dateTime, String teamName, String playerName){
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

    public void setMatchScore(LocalDate matchDate, int team1Score, int team2Score){
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

    public ArrayList<LineUp> getMatchLineUps(LocalDate matchDate){
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