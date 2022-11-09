module edu.ucdenver.pa1_tourney {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens edu.ucdenver.pa1_tourney to javafx.fxml;
    exports edu.ucdenver.pa1_tourney;
}