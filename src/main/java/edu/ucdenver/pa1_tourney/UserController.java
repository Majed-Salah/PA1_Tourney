package edu.ucdenver.pa1_tourney;

import edu.ucdenver.client.Client;
import edu.ucdenver.tournament.Country;
import edu.ucdenver.tournament.Match;
import edu.ucdenver.tournament.Tournament;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
/**
 * The hello controllers responsibility is to take in the user's input from java jx and sends that data to the serve
 * in line with the protocol that we have defined.
 * */
public class UserController {
    public ListView<String> listRequestedMatches;
    public DatePicker dateMatchSelect;
    public ComboBox<String> comboTeamSelect;
    public ListView listviewTeamBLineUp;
    public ListView listviewTeamALineUp;
    public ComboBox<String> choiceMatchDate;
    private Client client;


    public UserController(){

        client = new Client();
        client.connect();

    }

    public String[] parseResponse(String msg){
        return msg.split("\\|");
    }

    public void getFilteredMatches(ActionEvent actionEvent) {

        // array lists for tracking changes
        ArrayList<String> unionList = new ArrayList<>();
        ArrayList<String> filterMatchByDate = new ArrayList<>();
        ArrayList<String> filterMatchByTeam = new ArrayList<>();
        ArrayList<String> listOfTeams = new ArrayList<>();

        try {
            String[] returnedTeams = parseResponse(client.sendRequest("W|"));
            String[] dateMatches = null;
            String[] teamMatches = null;

            // start after 2nd pipe
            for(int i = 2; i < returnedTeams.length; ++i){
                listOfTeams.add(returnedTeams[i]);
            }

            comboTeamSelect.setItems(FXCollections.observableArrayList(listOfTeams));

// dont parse if there is nothing to read
            if(dateMatchSelect.getValue() != null) {
                dateMatches = parseResponse(client.sendRequest("G|" + dateMatchSelect.getValue()));
                if (dateMatches[0].equals("0") && dateMatches.length > 2) {
                    for (int i = 2; i < dateMatches.length; ++i) {
                        filterMatchByDate.add(dateMatches[i]);
                    }
                }
            }

            if(comboTeamSelect.getValue() != null) {
                String val = comboTeamSelect.getValue();
                teamMatches = parseResponse(client.sendRequest("F|" + val));
                if (teamMatches[0].equals("0") && teamMatches.length > 2) {
                    for (int i = 2; i < teamMatches.length; ++i) {
                        filterMatchByTeam.add(teamMatches[i]);
                    }
                }
            }

            if(filterMatchByDate.size() == 0){
                unionList = filterMatchByTeam;
            }
            else if(filterMatchByTeam.size() == 0){
                unionList = filterMatchByDate;
            }
            else{
                // union of two lists, check what is present
                for(String match : filterMatchByDate){
                    unionList.add(match);
                }
                for(String match : filterMatchByTeam){
                    if(!unionList.contains(match)){
                        unionList.add(match);
                    }
                }
            }

            listRequestedMatches.setItems(FXCollections.observableArrayList(unionList));

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Country Added Successfully.");
            alert.show();
        }
        catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Not able to show relevant matches.");
            alert.show();
        }

    }

    public void getLineUps(ActionEvent actionEvent) {

        ArrayList<String> listMatches = new ArrayList<>();
        ArrayList<String> lineupA = new ArrayList<>();
        ArrayList<String> lineupB = new ArrayList<>();

        try {
            String[] matches = parseResponse(client.sendRequest("H|"));

            for(int i = 2; i < matches.length; ++i){
                listMatches.add(matches[i]);
            }

            choiceMatchDate.setItems(FXCollections.observableArrayList(listMatches));

            String[] results = parseResponse(client.sendRequest("U|" + choiceMatchDate.getValue()));
            if(results[0].equals("0")) {
                // split by semicolon in toString method
                String[] teamA = results[2].split(";");
                String[] teamB = results[3].split(";");


                for (String x : teamA){
                    lineupA.add(x);
                }

                for(String x: teamB){
                    lineupB.add(x);
                }

                listviewTeamALineUp.setItems(FXCollections.observableArrayList(lineupA));
                listviewTeamBLineUp.setItems(FXCollections.observableArrayList(lineupB));
            }
        }
        catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Not able to show relevant matches.");
            alert.show();
        }

    }
}
