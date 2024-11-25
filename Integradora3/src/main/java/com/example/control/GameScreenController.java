package com.example.control;

import com.example.model.Stage;
import com.example.model.ToolType;
import com.example.screens.ScreenA;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class GameScreenController implements Initializable {
    private final String PATH = "/com/example/img";
    @FXML
    private Label points;
    @FXML
    private VBox vBox;

    @FXML
    private Canvas canvas;

    @FXML
    private ImageView axe;

    @FXML
    private ImageView hammer;

    @FXML
    private ImageView sword;

    private GraphicsContext graphicsContext;
    private ScreenA screenA;
    //private ScreenB screenB;
    //private ScreenC screenC;
    private Controller controller;
    private Stage stage;


    /**
     * The initialize function sets up the game screen controller and initializes the graphics context
     * and listeners, as well as setting the images for the player's lives.
     * 
     * @param url The `url` parameter is the location of the FXML file that defines the layout of the
     * user interface. It is used to load the FXML file and initialize the UI components.
     * @param resourceBundle The ResourceBundle parameter is used to access localized resources, such
     * as strings or images, based on the current locale. It allows you to retrieve resources from
     * property files that are specific to different languages or regions. In this case, it is not
     * being used, so you can ignore it.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.graphicsContext = this.canvas.getGraphicsContext2D();

        this.controller = Controller.getInstance();
        controller.setGameScreenController(this);

        listeners();

        screenAStart();

        axe.setImage(new Image(getClass().getResourceAsStream(PATH+"/objects/tools/blackaxe.png")));
        hammer.setImage(new Image(getClass().getResourceAsStream(PATH+"/objects/tools/blackhammer.png")));
        sword.setImage(new Image(getClass().getResourceAsStream(PATH+"/objects/tools/blacksword.png")));
    }




    public void updateAxe() {
        axe.setImage(new Image(getClass().getResourceAsStream(PATH + "/objects/tools/axe.png")));
    }

    public void updateHammer() {
        hammer.setImage(new Image(getClass().getResourceAsStream(PATH + "/objects/tools/hammer.png")));
    }

    public void updateSword() {
        sword.setImage(new Image(getClass().getResourceAsStream(PATH + "/objects/tools/sword.png")));
    }


    public void updatePoints(int points) {
        this.points.setText(points+"");
    }



   /**
    * The function sets up key event listeners for different stages and calls the corresponding methods
    * on the active screen.
    */
    public void listeners(){
        this.canvas.setOnKeyPressed(keyEvent -> {
            switch (stage){
                case FIRSTSTAGE -> {
                    if(screenA!=null)
                        screenA.onKeyPressed(keyEvent);
                }
                /**case SECONDSTAGE -> {
                    if(screenB!=null)
                        screenB.onKeyPressed(keyEvent);
                }
                case THIRDSTAGE -> {
                    if(screenC!=null)
                        screenC.onKeyPressed(keyEvent);
                }
                 */
            }
        });

        this.canvas.setOnKeyReleased(keyEvent -> {
            switch (stage){
                case FIRSTSTAGE -> {
                    if(screenA!=null)
                        screenA.onKeyRelease(keyEvent);
                }
                /**
                case SECONDSTAGE -> {
                    if(screenB!=null)
                        screenB.onKeyRelease(keyEvent);
                }
                case THIRDSTAGE -> {
                    if(screenC!=null)
                        screenC.onKeyRelease(keyEvent);
                }
                 */
            }
        });

    }
    public void screenAStart(){
        screenA = new ScreenA(this.canvas);
        stage = Stage.FIRSTSTAGE;

        new Thread(() -> {
            while (stage == Stage.FIRSTSTAGE) {
                Platform.runLater(() -> {
                    if(screenA!=null)
                        screenA.paint();
                });

                try {
                    Thread.sleep(45);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public void screenBStart(){
        
    }

    public void highlightTool(ToolType toolType) {
        axe.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-border-width: 0;");
        hammer.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-border-width: 0;");
        sword.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-border-width: 0;");

        switch (toolType) {
            case AXE -> axe.setStyle("-fx-background-color: yellow; -fx-border-color: yellow; -fx-border-width: 3;");
            case HAMMER -> hammer.setStyle("-fx-background-color: yellow; -fx-border-color: yellow; -fx-border-width: 3;");
            case SWORD -> sword.setStyle("-fx-background-color: yellow; -fx-border-color: yellow; -fx-border-width: 3;");
        }
    }


    

}
