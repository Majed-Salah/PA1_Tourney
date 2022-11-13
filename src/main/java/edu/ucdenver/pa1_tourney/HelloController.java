package edu.ucdenver.pa1_tourney;

import edu.ucdenver.tournament.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class HelloController {
    public TextField textFieldListOfPlayersToAdd;
    public TextField textFieldPlayerName;
    public TextField textFieldPlayerAge;
    public TextField textFieldPlayerHeight;
    public TextField textFieldPlayerWeight;
    public TextField textfieldTeamBMatchScore;
    public TextField textfieldTeamAMatchScore;
    private Tournament tournament;

    public Tab tabAddMatch;

    public DatePicker datePickerMatch;

    public ListView <Match> listviewMatchesString;
    public ListView <Player> listviewFullSquad;
    public ListView <Player> listviewFullLineUp;


    public TextField textFieldAddMatchTime;
    public TextField txtTeamNameField;
    public TextField txtCountryField;
    public TextField textfieldRefereeName;
    public TextField textFieldRefereeCountry;

    public ComboBox <String> choiceMatchTeamA;
    public ComboBox <String> choiceMatchTeamB;
    public ComboBox <Referee> comboboxAssignRef1;
    public ComboBox <Referee> comboboxAssignRef3;
    public ComboBox <Referee> comboboxAssignRef2;
    public ComboBox <Referee> comboboxAssignRef4;
    public ComboBox <Match> comboboxAssignReftoWhichMatch;

    public ChoiceBox<Country> choiceCountry;
    public ChoiceBox <Match> choiceboxSquadMatch;
    public ChoiceBox <String> choiceboxSquadTeam;
    public ChoiceBox <Match> comboboxSelectMatchToSetScore;


    private ArrayList<String> countryNames = new ArrayList<>();
    private int count = 0;

    public HelloController(){
        this.tournament = new Tournament("Soccer Tournament", LocalDateTime.now(), LocalDateTime.now());
        for(Country country: tournament.getListCountries()){
            countryNames.add(country.getCountryName());
        }
        if(tournament.getListCountries().size() > 0){
            this.choiceCountry.setItems(FXCollections.observableArrayList(tournament.getListCountries()));
        }

        /*if (count == 0 ){
            LocalDateTime stubTime = LocalDateTime.of(2022,11,12,22,22,22);
            this.tournament.addCountry("USA");
            this.tournament.addCountry("UK");
            this.tournament.addTeam("Chicago Fire", "USA");
            this.tournament.addTeam("Real Madrid", "UK");
            this.tournament.addMatch(stubTime, "Chicago Fire", "Real Madrid");
            count+= 1;
        }*/

    }
    /**
     * addMatch Function:
     * TODO Figure out what is going on with the time part of the date: use LocalDateTime? A new var for time?
     */
    public void addMatch(ActionEvent actionEvent) throws InterruptedException {
        /* 1. Get the date and time from date picker and text field, format results for LocalDateTime */
        LocalDate localDate = this.datePickerMatch.getValue();
        String[] arrOfStr = textFieldAddMatchTime.getText().split("-", 3);
        int hour = Integer.parseInt(arrOfStr[0]);
        int min = Integer.parseInt(arrOfStr[1]);
        int sec = Integer.parseInt(arrOfStr[2]);
        LocalDateTime localDateTime = localDate.atTime(hour, min, sec);

        /* 2. DateTime and teams have been Selected, add matc to tournament with those values */
        tournament.addMatch(localDateTime, this.choiceMatchTeamA.getValue(), this.choiceMatchTeamB.getValue());
        System.out.println("Index1: " + tournament.getAllMatches().get(0));

        /* Displaying all matches in right pane */
        ObservableList<Match> matches = FXCollections.observableArrayList(tournament.getAllMatches());
        listviewMatchesString.setItems(matches);
        listviewMatchesString.getItems();

        /* Fill ChoiceBox's "Assign to Match" in Add Match View, and "Match" in Choose LineUp with the existing matches (same as right pane) */
        comboboxAssignReftoWhichMatch.setItems(matches);
        choiceboxSquadMatch.setItems(matches);
        comboboxSelectMatchToSetScore.setItems(matches);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Match Added Successfully");
        alert.show();
    }

    public void addCountry(ActionEvent actionEvent) {
        try {
            this.tournament.addCountry(this.txtCountryField.getText());
            countryNames.add(this.txtCountryField.getText());
            this.choiceCountry.setItems(FXCollections.observableArrayList(tournament.getListCountries()));
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Country Added Successfully");
            alert.show();
        }
        catch(IllegalArgumentException iae){
            Alert alert = new Alert(Alert.AlertType.ERROR, iae.getMessage());
            alert.show();
        }
    }

    public void addTeam(ActionEvent actionEvent) {
        try{
            tournament.addTeam(txtTeamNameField.getText(), choiceCountry.getValue().getCountryName());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Team Added Successfully");
            alert.show();
        }
        catch(IllegalArgumentException iae){
            Alert alert = new Alert(Alert.AlertType.ERROR, iae.getMessage());
            alert.show();
        }
    }


    public void addReferee(ActionEvent actionEvent) {
        tournament.addReferee(textfieldRefereeName.getText(), textFieldRefereeCountry.getText());
        this.comboboxAssignRef1.setItems(FXCollections.observableArrayList(tournament.getAllReferees()));
        this.comboboxAssignRef2.setItems(FXCollections.observableArrayList(tournament.getAllReferees()));
        this.comboboxAssignRef3.setItems(FXCollections.observableArrayList(tournament.getAllReferees()));
        this.comboboxAssignRef4.setItems(FXCollections.observableArrayList(tournament.getAllReferees()));
    }


    public void loadMatchFields(Event event) {
        ObservableList<String> teams = FXCollections.observableArrayList(tournament.getAllTeamsToString());
        System.out.println("TEAMS: " + teams);
        this.choiceMatchTeamA.setItems(teams); // all the team
        this.choiceMatchTeamB.setItems(teams);
    }

    public void assignRefereetoMatch(ActionEvent actionEvent) {
        System.out.println("assigning ref: upcoming matches = " + tournament.getUpcomingMatches());
        Match matchChosen = comboboxAssignReftoWhichMatch.getValue();
        LocalDateTime localDateTime = matchChosen.getDate();

        for (Match m : tournament.getUpcomingMatches()) {
            System.out.println("Upcoming Matches: " + tournament.getUpcomingMatches());
            System.out.println("m.getDate() = " + m.getDate() + ", localDateTime = " + localDateTime);
            if (m.getDate().equals(localDateTime)) { // found match with date
                System.out.println("Found match with dateTime (" + localDateTime + "), adding referees to match");
                // get the value that was in combo box when they clicked add referree
                if (comboboxAssignRef1.getValue() != null){
                    tournament.addRefereeToMatch(localDateTime, comboboxAssignRef1.getValue().getName()); // the ref they chose
                }
                if (comboboxAssignRef2.getValue() != null){
                    tournament.addRefereeToMatch(localDateTime, comboboxAssignRef2.getValue().getName()); // the ref they chose
                }
                if (comboboxAssignRef3.getValue() != null){
                    tournament.addRefereeToMatch(localDateTime, comboboxAssignRef3.getValue().getName()); // the ref they chose
                }
                if (comboboxAssignRef4.getValue() != null){
                    tournament.addRefereeToMatch(localDateTime, comboboxAssignRef4.getValue().getName()); // the ref they chose
                }
                System.out.println("Match " + m + " now has referees: " + m.getReferees());
            }
        }

        System.out.println();
    }

    public void getTeamsOfMatch(MouseEvent mouse) {
        ObservableList<String> teams = FXCollections.observableArrayList(choiceboxSquadMatch.getValue().getTeamA().getTeam().getTeamName(), choiceboxSquadMatch.getValue().getTeamB().getTeam().getTeamName());
        choiceboxSquadTeam.setItems(teams); // set items to the teams in the slected match
    }

    public void fillSquadList(Event event) {
        // Function needs to add player to squad and fill Squad list with current players
        Team teamChosen = choiceboxSquadMatch.getValue().getTeamA().getTeam();
        if (teamChosen.getSquad() == null){
            teamChosen.addPlayer(textFieldPlayerName.getText(), Integer.parseInt(textFieldPlayerAge.getText()), Double.parseDouble(textFieldPlayerHeight.getText()), Double.parseDouble(textFieldPlayerWeight.getText()));
        }
        else{
            teamChosen.addPlayer(textFieldPlayerName.getText(), Integer.parseInt(textFieldPlayerAge.getText()), Double.parseDouble(textFieldPlayerHeight.getText()), Double.parseDouble(textFieldPlayerWeight.getText()));
        }
        ObservableList<Player> squadList = FXCollections.observableArrayList(teamChosen.getSquad());
        System.out.println(squadList);
        listviewFullSquad.setItems(squadList);
    }


    public void addSelectedPlayertoLineUp() {
        LineUp lineUpChosen = choiceboxSquadMatch.getValue().getTeamA();
        ObservableList<Player> lineUpPlayers = listviewFullSquad.getSelectionModel().getSelectedItems();
        ArrayList<Player> tempPlayers = new ArrayList<>();
        tempPlayers.addAll(lineUpPlayers);
        lineUpChosen.addPlayer(tempPlayers.get(0));

        System.out.println("Temp Players" + tempPlayers);
        System.out.println("Lineup chose get player: " + lineUpChosen.getPlayers());
        finalizeLineup(lineUpChosen);
    }


    public void finalizeLineup(LineUp lineUpChosen) {
        lineUpChosen = choiceboxSquadMatch.getValue().getTeamA();
        ObservableList<Player> updateLineUpView =  FXCollections.observableArrayList(lineUpChosen.getPlayers());
        listviewFullLineUp.setItems(updateLineUpView);
    }

    public void updateMatchScore(ActionEvent actionEvent) {
        if (comboboxSelectMatchToSetScore.getValue().getDate().isAfter(LocalDateTime.now())){ // in the past
            comboboxSelectMatchToSetScore.getValue().setMatchScore(Integer.parseInt(textfieldTeamAMatchScore.getText()), Integer.parseInt(textfieldTeamBMatchScore.getText()));
            System.out.println("Match Score was TeamA(" + textfieldTeamAMatchScore.getText() + ") - TeamB(" + textfieldTeamBMatchScore.getText() + ")" );
        }
        else {
            System.out.println("[ERROR] Match has not occurred yet");
        }
    }
}