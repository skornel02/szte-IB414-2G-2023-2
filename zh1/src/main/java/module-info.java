module hu.skornel02 {
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;

    opens hu.skornel02 to javafx.fxml;
    exports hu.skornel02;
    exports hu.skornel02.controllers;
    exports hu.skornel02.entities;
    opens hu.skornel02.controllers to javafx.fxml;
}
