package edu.ucdenver.pa1_tourney;

import edu.ucdenver.tournament.Country;
import edu.ucdenver.tournament.Match;
import edu.ucdenver.tournament.Team;
import edu.ucdenver.tournament.Tournament;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class HelloController {

    public TextField txtCountryField;
    public ChoiceBox<Country> choiceCountry;
    public TextField txtTeamNameField;
    public DatePicker datePickerMatch;
    public ComboBox <Team> choiceMatchTeamA;
    public ComboBox <Team> choiceMatchTeamB;
    private Tournament tournament;
    private ArrayList<String> countryNames = new ArrayList<>();

    public HelloController(){
        this.tournament = new Tournament("Soccer Tournament", LocalDate.now(), LocalDate.now());
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
    public void addMatch(ActionEvent actionEvent){
        tournament.addMatch(this.datePickerMatch.getValue(), this.choiceMatchTeamA.getValue().getTeamName(), this.choiceMatchTeamB.getValue().getTeamName());
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Match Added Successfully");
        alert.show();
    }

    public void addCourse(ActionEvent actionEvent) {
    }

    public void listStudentsAndCourses(Event event) {
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

    public void addTeamA(ActionEvent actionEvent) {
    }
}