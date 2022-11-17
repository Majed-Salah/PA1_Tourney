package edu.ucdenver.pa1_tourney;

import edu.ucdenver.tournament.Country;
import edu.ucdenver.tournament.Referee;
import edu.ucdenver.tournament.Team;
import edu.ucdenver.tournament.Tournament;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import edu.ucdenver.client.Client;

public class HelloController {

    public TextField txtCountryField;
    public ChoiceBox<Country> choiceCountry;
    public TextField txtTeamNameField;
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
    public DatePicker dateMatchDate;
    public TextField txtMatchTime;
    public TextField txtTeam1Match;
    public TextField txtTeam2Match;
    public DatePicker dateRefMatch;
    public TextField txtMatchTimeRef;
    public TextField txtReferee4;
    public TextField txtReferee3;
    public TextField txtReferee2;
    public TextField txtReferee1;
    public TextField txtLineUpTeam;
    public TextField txtPlayerLineUp;
    public DatePicker datePlayerLineUp;
    public TextField txtMatchTimeLineUp;
    public TextField txtTeam1MatchScore;
    public TextField txtTeam2MatchScore;
    public DatePicker dateRecordMatchAtDate;
    public TextField txtRecordMatchAtTime;
    private Tournament tournament;
    private ArrayList<String> countryNames = new ArrayList<>();
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

    public String[] parseResponse(String msg){
        return msg.split("\\|");
    }

    public void addCountry(ActionEvent actionEvent) {

        try {

            String[] handledMessage = parseResponse(client.sendRequest("C|" + this.txtCountryField.getText()));
            if(handledMessage[0].equals("0")){
                txtCountryField.setText("");
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Country Added Successfully");
                alert.show();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR, handledMessage[2]);
                alert.show();
            }

        }
        catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.show();
        }

    }

    public void addTeam(ActionEvent actionEvent) {
        try{

            String[] handledMessage = parseResponse(client.sendRequest("D|" + txtTeamNameField.getText() + "|" + txtCountryChoice.getText()));
            if(handledMessage[0].equals("0")){
                txtTeamNameField.setText("");
                txtCountryChoice.setText("");
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Team Added Successfully");
                alert.show();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR, handledMessage[2]);
                alert.show();
            }

        }
        catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.show();
        }
    }

    public void addReferee(ActionEvent actionEvent) {
        try {

            String[] handledResponse = parseResponse(client.sendRequest("R|" + txtRefereeName.getText() + "|" + txtCountryReferee.getText()));
            if(handledResponse[0].equals("0")){
                txtRefereeName.setText("");
                txtCountryReferee.setText("");
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Referee Added Successfully");
                alert.show();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR, handledResponse[2]);
                alert.show();
            }

        }
        catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
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

            String teamName = txtTeamNamePlayer.getText();
            String playerName = txtPlayerName.getText();
            String playerAge = txtPlayerAge.getText();
            String playerWeight = txtPlayerWidth.getText();
            String playerHeight = txtPlayerHeight.getText();

            String[] handledMessage = parseResponse(client.sendRequest("P|" + teamName + "|" + playerName + "|" + playerAge + "|" + playerHeight + "|" + playerWeight));

            if(handledMessage[0].equals("0")){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Player Added Successfully");
                alert.show();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR, handledMessage[2]);
                alert.show();
            }

            clearPlayerBoxes();


        }
        catch (Exception e){
            System.err.println(e);
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.show();
        }
    }

    public void addMatch(ActionEvent actionEvent) {
        try{

            LocalDate matchDate = dateMatchDate.getValue();
            LocalTime matchTime = LocalTime.parse(txtMatchTime.getText() + ":00");
            LocalDateTime dt = LocalDateTime.of(matchDate, matchTime);

            String[] handledMessage = parseResponse(client.sendRequest("M|" + dt + "|" + txtTeam1Match.getText() + "|" + txtTeam2Match.getText()));

            if(handledMessage[0].equals("0")){
                dateMatchDate.setValue(null);
                txtMatchTime.setText("");
                txtTeam1Match.setText("");
                txtTeam2Match.setText("");

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Match Added Successfully");
                alert.show();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR, handledMessage[2]);
                alert.show();
            }

        }
        catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.show();
        }
    }


    public void addRefToMatch(ActionEvent actionEvent) {
        LocalDate dt = dateRefMatch.getValue();
        LocalTime time = LocalTime.parse(txtMatchTimeRef.getText() + ":00");

        try {

            ArrayList<String> refs = new ArrayList<>();
            HashMap<String, Integer> referee = new HashMap<>();

            refs.add(txtReferee1.getText());
            refs.add(txtReferee2.getText());
            refs.add(txtReferee3.getText());
            refs.add(txtReferee4.getText());

            for(String ref : refs){
                if(ref.equals(null)){
                    throw new IllegalArgumentException("Missing referee.");
                }
            }

            for(String ref: refs){
                if(referee.containsKey(ref)){
                    throw new IllegalArgumentException("Adding multiple of the same referee.");
                }
                else{
                    referee.put(ref, 1);
                }
            }

            client.sendRequest("Z|" + LocalDateTime.of(dt, time) + "|" + txtReferee1.getText());
            client.sendRequest("Z|" + LocalDateTime.of(dt, time) + "|" + txtReferee2.getText());
            client.sendRequest("Z|" + LocalDateTime.of(dt, time) + "|" + txtReferee3.getText());
            client.sendRequest("Z|" + LocalDateTime.of(dt, time) + "|" + txtReferee4.getText());

            client.sendRequest("A|" + LocalDateTime.of(dt, time) + "|" + txtReferee1.getText());
            client.sendRequest("A|" + LocalDateTime.of(dt, time) + "|" + txtReferee2.getText());
            client.sendRequest("A|" + LocalDateTime.of(dt, time) + "|" + txtReferee3.getText());
            client.sendRequest("A|" + LocalDateTime.of(dt, time) + "|" + txtReferee4.getText());

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Referee Added Successfully");
            alert.show();

        }
        catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.show();
        }

    }

    public void addPlayerToLineUp(ActionEvent actionEvent) {
        try{

            LocalDate matchDate = datePlayerLineUp.getValue();
            LocalTime matchTime = LocalTime.parse(txtMatchTimeLineUp.getText() + ":00");
            LocalDateTime dt = LocalDateTime.of(matchDate, matchTime);

            String[] serverResponse = parseResponse(client.sendRequest("Y|" + dt + "|" + txtLineUpTeam.getText() + "|" + txtPlayerLineUp.getText()));
            if(serverResponse[0].equals("0")){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, serverResponse[2]);
                alert.show();
            }
            else{
                throw new IllegalArgumentException(serverResponse[2]);
            }

        }
        catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.show();
        }
    }

    public void recordMatchScore(ActionEvent actionEvent) {
        try{

            LocalDate date = dateRecordMatchAtDate.getValue();
            LocalTime time = LocalTime.parse(txtRecordMatchAtTime.getText() + ":00");
            String[] serverResponse = parseResponse("S|" + client.sendRequest(LocalDateTime.of(date, time) + "|" + txtTeam1MatchScore.getText() + "|" + txtTeam2MatchScore.getText()));
            if(serverResponse[0].equals("0")){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, serverResponse[2]);
                alert.show();
            }
            else{
                throw new IllegalArgumentException("Was unable to set score for match.");
            }

        }
        catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.show();
        }
    }
}