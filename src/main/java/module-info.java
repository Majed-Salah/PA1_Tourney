module edu.ucdenver.pa1_tourney {
    requires javafx.controls;
    requires javafx.fxml;

    opens edu.ucdenver.tourney_app to javafx.fxml;
    exports edu.ucdenver.tourney_app;
    exports edu.ucdenver.client;
    exports edu.ucdenver.server;
    exports edu.ucdenver.tournament;

}