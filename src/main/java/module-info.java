module SociologicalDB {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    requires com.opencsv;

    opens SQL;
}