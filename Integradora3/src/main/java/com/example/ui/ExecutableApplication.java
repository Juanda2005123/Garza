package com.example.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ExecutableApplication extends Application {
    @Override
    // The `start` method is the entry point for a JavaFX application. It is called when the
    // application is launched.
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ExecutableApplication.class.getResource("/com/example/vista/start-game-screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 300, 300); //90% Full HD
        stage.setTitle("Stardew Valleyk");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main function launches the Java program.
     */
    public static void main(String[] args) {
        launch();
    }
}