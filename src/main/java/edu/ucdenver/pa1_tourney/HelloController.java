package edu.ucdenver.pa1_tourney;

import edu.ucdenver.tournament.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class HelloController {

    private Tournament tournament;

    public Tab tabAddMatch;

    public DatePicker datePickerMatch;

    public ListView <Match> listviewMatchesString;

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
    public ChoiceBox <Team> choiceboxSquadTeam;

    private ArrayList<String> countryNames = new ArrayList<>();

    public HelloController(){
        this.tournament = new Tournament("Soccer Tournament", LocalDateTime.now(), LocalDateTime.now());
        for(Country country: tournament.getListCountries()){
            countryNames.add(country.getCountryName());
        }
        if(tournament.getListCountries().size() > 0){
            this.choiceCountry.setItems(FXCollections.observableArrayList(tournament.getListCountries()));
        }
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
            System.out.println("m.getDate() = " + m.getDate() + ", localDateTime = " + localDateTime);
            if (m.getDate().equals(localDateTime)) { // found match with date
                System.out.println("Found match with dateTime (" + localDateTime + "), adding referee to match");
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

    public void getTeamsOfMatch(ContextMenuEvent contextMenuEvent) {
        ObservableList<Team> teams = FXCollections.observableArrayList(choiceboxSquadMatch.getValue().getTeamA().getTeam(), choiceboxSquadMatch.getValue().getTeamB().getTeam());
        choiceboxSquadTeam.setItems(teams); // set items to the teams in the slected match
    }
}