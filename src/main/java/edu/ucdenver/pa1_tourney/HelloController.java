package edu.ucdenver.pa1_tourney;

import edu.ucdenver.tournament.Country;
import edu.ucdenver.tournament.Team;
import edu.ucdenver.tournament.Tournament;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import edu.ucdenver.client.Client;

/**
 * Explaing the controller sadsadasd
 */
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
    public DatePicker dateMatchDate;
    public TextField txtMatchTime;
    public TextField txtTeam1Match;
    public TextField txtTeam2Match;
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

    public String[] parseResponse(String msg){
        return msg.split("\\|");
    }

    public void addCountry(ActionEvent actionEvent) {

        try {

            String[] handledMessage = parseResponse(client.sendRequest("C|" + this.txtCountryField.getText()));
            if(handledMessage[0].equals("0")){
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
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Player Added Successfully");
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


}