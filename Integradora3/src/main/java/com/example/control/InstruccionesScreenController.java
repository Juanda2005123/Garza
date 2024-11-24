package com.example.control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class InstruccionesScreenController implements Initializable {
    @FXML
    private Button cerrarButton;

    /**
     * This function is an override of the initialize method from the Initializable interface in
     * JavaFX, and it is used to initialize the controller class.
     * 
     * @param url The URL of the FXML file that is being loaded.
     * @param resourceBundle The ResourceBundle parameter is used to access localized resources, such
     * as strings or images, that are specific to a particular locale or language. It allows you to
     * retrieve resources based on their keys.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * The function closes the current JavaFX stage.
     * 
     * @param event The event parameter is an instance of the ActionEvent class. It represents the
     * event that occurred, such as a button click or menu item selection. In this case, the method is
     * being called when the "cerrarButton" is clicked.
     */
    @FXML
    void cerrar(ActionEvent event) {
        Stage stage = (Stage) this.cerrarButton.getScene().getWindow();
        stage.close();
    }
}
