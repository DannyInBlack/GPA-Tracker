module com.example.project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens Root to javafx.fxml;
    exports Root;
}