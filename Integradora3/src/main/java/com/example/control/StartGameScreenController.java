package com.example.control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StartGameScreenController implements Initializable {

    @FXML
    private ImageView image;
    @FXML
    private Button buttonInstrucciones;

    @FXML
    private Button buttonJugar;
    @FXML
    private VBox vbox;

    /**
     * The initialize function is an overridden method that is called when the controller is
     * initialized.
     * 
     * @param url The URL of the FXML file that is being loaded. This can be used to access any
     * additional resources or data associated with the FXML file.
     * @param resourceBundle The ResourceBundle parameter is used to access localized resources, such
     * as strings or images, that are specific to a particular locale or language. It allows you to
     * retrieve resources based on their keys.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
    /**
     * The function `initAttributes` sets the image and dimensions of an image view based on whether
     * the player wins or loses.
     * 
     * @param win The "win" parameter is a boolean value that indicates whether the game was won or
     * not.
     */
/**
 * The "jugar" function loads a new FXML file and sets it as the scene for the current stage.
 * 
 * @param event The event parameter is an ActionEvent object that represents the event that triggered
 * the method. In this case, it is the event of clicking a button that is associated with the jugar()
 * method.
 */

    @FXML
    void jugar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/vista/game-screen.fxml"));

            Parent root = loader.load();

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene mainScene = new Scene(root);

            currentStage.setScene(mainScene);
        } catch (IOException e) {
            e.printStackTrace(); // Imprime el stack trace completo
            throw new RuntimeException(e.getMessage());
        }

    }


    /**
     * The function opens a new window displaying instructions by loading an FXML file.
     * 
     * @param event The event parameter is an ActionEvent object that represents the event that
     * triggered the method. It is typically used to handle user interactions, such as button clicks.
     */
    @FXML
    void abrirInstrucciones(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/vista/instrucciones-screen.fxml"));

            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();

        } catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }
    }


}
