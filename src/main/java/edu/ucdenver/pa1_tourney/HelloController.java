package edu.ucdenver.pa1_tourney;

import edu.ucdenver.tournament.Country;
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
    public ChoiceBox<Country> choiceCountryForRef;
    public TextField txtRefereeName;
    public TextField txtPlayerHeight;
    public TextField txtPlayerWidth;
    public TextField txtPlayerAge;
    public TextField txtPlayerName;
    public ComboBox<Team> comboTeam;
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

    public void addCourse(ActionEvent actionEvent) {
    }

    public void listStudentsAndCourses(Event event) {
    }

    public void addCountry(ActionEvent actionEvent) {
        try {
            this.tournament.addCountry(this.txtCountryField.getText());
            countryNames.add(this.txtCountryField.getText());
            this.choiceCountry.setItems(FXCollections.observableArrayList(tournament.getListCountries()));
            this.choiceCountryForRef.setItems(FXCollections.observableArrayList(tournament.getListCountries()));
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
            txtTeamNameField.setText("");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Team Added Successfully");
            comboTeam.setItems(FXCollections.observableArrayList(tournament.getTeams()));
            alert.show();
        }
        catch(IllegalArgumentException iae){
            Alert alert = new Alert(Alert.AlertType.ERROR, iae.getMessage());
            alert.show();
        }
    }

    public void addReferee(ActionEvent actionEvent) {
        try {
            tournament.addReferee(txtRefereeName.getText(), choiceCountryForRef.getValue().getCountryName());
            txtRefereeName.setText("");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Referee Added Successfully");
            alert.show();
        }
        catch(IllegalArgumentException iae){
            Alert alert = new Alert(Alert.AlertType.ERROR, iae.getMessage());
            alert.show();
        }
    }

    private void clearPlayerBoxes(){
        txtPlayerAge.setText("");
        txtPlayerHeight.setText("");
        txtPlayerName.setText("");
        txtPlayerWidth.setText("");
    }


    public void addPlayerToTeam(ActionEvent actionEvent) {
        try{
            Team team = comboTeam.getValue();
            String playerName = txtPlayerName.getText();
            int playerAge = Integer.parseInt(txtPlayerAge.getText());
            double playerWeight = Double.parseDouble(txtPlayerWidth.getText());
            double playerHeight = Double.parseDouble(txtPlayerHeight.getText());
            tournament.addPlayer(team.getTeamName(), playerName, playerAge, playerHeight, playerWeight);
            clearPlayerBoxes();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Player Added Successfully");
            alert.show();

        }
        catch (IllegalArgumentException iae){
            System.err.println(iae);
        }
    }
}