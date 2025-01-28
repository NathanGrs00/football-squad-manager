module com.nathan.footballsquadmanagerbp2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens com.nathan.footballsquadmanagerbp2 to javafx.fxml;
    exports com.nathan.footballsquadmanagerbp2;
}