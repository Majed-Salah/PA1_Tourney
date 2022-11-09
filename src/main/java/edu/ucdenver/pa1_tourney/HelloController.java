package edu.ucdenver.pa1_tourney;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public void addCourse(ActionEvent actionEvent) {
    }

    public void listStudentsAndCourses(Event event) {
    }
}