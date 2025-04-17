module org.ispw.fastridetrack {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.ispw.fastridetrack to javafx.fxml;
    exports org.ispw.fastridetrack;
}