package com.example.control;


import com.example.model.Stage;
import com.example.screens.Screen;
import com.example.screens.ScreenA;
import com.example.screens.ScreenB;
import javafx.application.Platform;

public class Controller {

    private static Controller controller;
    private int enemies;
    private int points;
    private int stopGame;

    private Screen currentScreen;
    private Stage stage; // Variable para almacenar el estado actual del escenario

    private GameScreenController gameScreenController;
    /**
     * The getInstance() function returns an instance of the Controller class, creating one if it
     * doesn't already exist.
     * 
     * @return The method is returning an instance of the Controller class.
     */
    public static Controller getInstance(){
        if(controller==null) {
            controller = new Controller();
        }
        return controller;
    }

    /**
     * The function increments the number of enemies and updates the enemy stat on the game screen.
     */
    public void setEnemies() {
        this.enemies++;
    }

    /**
     * The function returns the value of the stopGame variable.
     * 
     * @return The method is returning the value of the variable "stopGame".
     */
    public int getStopGame() {
        return stopGame;
    }

    /**
     * The function sets the value of the stopGame variable.
     * 
     * @param stopGame The parameter "stopGame" is an integer that represents whether the game should
     * be stopped or not.
     */
    public void setStopGame(int stopGame) {
        this.stopGame = stopGame;
    }

    // The `private Controller()` is a private constructor for the `Controller` class. It is called
    // when an instance of the `Controller` class is created.
    private Controller(){
        stopGame = 0;
        enemies = 0;
        points = 0;
    }
    /**
     * The function sets the game screen controller for the current object.
     * 
     * @param gameScreenController The gameScreenController parameter is an instance of the
     * GameScreenController class.
     */
    public void setGameScreenController(GameScreenController gameScreenController) {
        this.gameScreenController = gameScreenController;
    }

    public GameScreenController getGameScreenController() {
        return gameScreenController;
    }
    public void updatePoints(int points){
        this.points += points;
        this.gameScreenController.updatePoints(this.points);
    }

    public void updateAxe(){gameScreenController.updateAxe();
    }
    public void updateHammer(){
        gameScreenController.updateHammer();
    }
    public void updateSword(){
        gameScreenController.updateSword();
    }

    public void deleteAxe(){gameScreenController.deleteAxe();
    }
    public void deleteHammer(){
        gameScreenController.deleteHammer();
    }
    public void deleteSword(){
        gameScreenController.deleteSword();
    }
    /**
     * The function "handleScreenB" calls the "screenBStart" method in the "gameScreenController"
     * object.
     */
    public void handleScreenB(){
        //gameScreenController.screenBStart();
    }
    /**
     * The function handlesScreenC() calls the screenCStart() method in the gameScreenController
     * object.
     */
    public void handlesScreenC(){
        //gameScreenController.screenCStart();
    }

    public void switchScreen(Screen newScreen) {
        currentScreen = newScreen;

        // Inicia el ciclo de dibujo para la nueva pantalla
        new Thread(() -> {
            while (currentScreen != null) {
                Platform.runLater(() -> currentScreen.paint());
                try {
                    Thread.sleep(45);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void switchToScreenB() {
        Stage stage = Stage.SECONDSTAGE;
        gameScreenController.screenBStart();
    }

    public void switchToScreenA() {
        Stage stage = Stage.FIRSTSTAGE;
        gameScreenController.screenAStart();
    }

    public void switchToScreenC() {
        Stage stage = Stage.FIRSTSTAGE;
        gameScreenController.screenAStart();
    }

    public Stage getStage() {
        return this.stage;
    }

}