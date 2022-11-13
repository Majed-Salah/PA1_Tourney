package edu.ucdenver.pa1_tourney;

import edu.ucdenver.tournament.Country;
import edu.ucdenver.tournament.Team;
import edu.ucdenver.tournament.Tournament;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import edu.ucdenver.client.Client;

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
    public ComboBox<Integer> choiceNumbers;
    public TextField txtTeamNamePlayer;
    public TextField txtCountryReferee;
    public TextField txtCountryChoice;
    private Tournament tournament;
    private ArrayList<String> countryNames = new ArrayList<>();
    private ArrayList<Integer> nums = new ArrayList<>(Arrays.asList(1, 3));
    private Client client;

    public HelloController(){

        client = new Client();
        client.connect();

        this.tournament = new Tournament("Soccer Tournament", LocalDate.now(), LocalDate.now());

        for(Country country: tournament.getListCountries()){
            countryNames.add(country.getCountryName());
        }

        if(tournament.getListCountries().size() > 0){
            this.choiceCountry.setItems(FXCollections.observableArrayList(tournament.getListCountries()));
        }

    }

    public void addCountry(ActionEvent actionEvent) {

        try {
/*            this.tournament.addCountry(this.txtCountryField.getText());
            countryNames.add(this.txtCountryField.getText());
            this.choiceCountry.setItems(FXCollections.observableArrayList(tournament.getListCountries()));
            this.choiceCountryForRef.setItems(FXCollections.observableArrayList(tournament.getListCountries()));*/

            try {
                client.sendRequest("C|" + this.txtCountryField.getText());
            }
            catch (IOException ioe){
                System.err.println(ioe);
            }

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
/*            tournament.addTeam(txtTeamNameField.getText(), choiceCountry.getValue().getCountryName());
            txtTeamNameField.setText("");*/

            try{
                client.sendRequest("D|" + txtTeamNameField.getText() + "|" + txtCountryChoice.getText());
            }
            catch(IOException ioe){
                System.err.println(ioe);
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Team Added Successfully");
            alert.show();
        }
        catch(IllegalArgumentException iae){
            Alert alert = new Alert(Alert.AlertType.ERROR, iae.getMessage());
            alert.show();
        }
    }

    public void addReferee(ActionEvent actionEvent) {
        try {
/*            tournament.addReferee(txtRefereeName.getText(), choiceCountryForRef.getValue().getCountryName());
            txtRefereeName.setText("");*/

            try{
                client.sendRequest("R|" + txtRefereeName.getText() + "|" + txtCountryReferee.getText());
            }
            catch(IOException ioe){
                System.err.println(ioe);
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Referee Added Successfully");
            alert.show();
        }
        catch(IllegalArgumentException iae){
            Alert alert = new Alert(Alert.AlertType.ERROR, iae.getMessage());
            alert.show();
        }
    }

    private void clearPlayerBoxes(){
        txtTeamNamePlayer.setText("");
        txtPlayerAge.setText("");
        txtPlayerHeight.setText("");
        txtPlayerName.setText("");
        txtPlayerWidth.setText("");
    }


    public void addPlayer(ActionEvent actionEvent) {
        try{
/*            Team team = comboTeam.getValue();
            String playerName = txtPlayerName.getText();
            int playerAge = Integer.parseInt(txtPlayerAge.getText());
            double playerWeight = Double.parseDouble(txtPlayerWidth.getText());
            double playerHeight = Double.parseDouble(txtPlayerHeight.getText());
            tournament.addPlayer(team.getTeamName(), playerName, playerAge, playerHeight, playerWeight);*/

            try{
                String teamName = txtTeamNamePlayer.getText();
                String playerName = txtPlayerName.getText();
                String playerAge = txtPlayerAge.getText();
                String playerWeight = txtPlayerWidth.getText();
                String playerHeight = txtPlayerHeight.getText();
                client.sendRequest("P|" + teamName + "|" + playerName + "|" + playerAge + "|" + playerHeight + "|" + playerWeight);
            }
            catch(IOException ioe){
                System.err.println(ioe);
            }

            clearPlayerBoxes();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Player Added Successfully");
            alert.show();

        }
        catch (IllegalArgumentException iae){
            System.err.println(iae);
        }
    }
}