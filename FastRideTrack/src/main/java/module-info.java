module org.ispw.fastridetrack {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.web;


    opens org.ispw.fastridetrack to javafx.fxml;
    opens org.ispw.fastridetrack.Controller.GUIController to javafx.fxml;

    exports org.ispw.fastridetrack;
    exports org.ispw.fastridetrack.Controller.GUIController;
}

