module com.example.integradora3 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    exports com.example.ui;
    opens com.example.ui to javafx.fxml;
    exports com.example.control;
    opens com.example.control to javafx.fxml;
}