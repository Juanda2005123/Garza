package com.example.control;

import com.example.model.Player;
import com.example.model.Stage;
import com.example.model.ToolType;
import com.example.screens.ScreenA;
import com.example.screens.ScreenB;
import com.example.screens.ScreenC;
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
    private ScreenB screenB;
    private ScreenC screenC;
    private Controller controller;
    private Stage stage;
    private Player player;
    @FXML
    private Label toolMessage;


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
        this.player = new Player(this.canvas); // Crear el jugador una sola vez

        listeners();

        screenAStart();

        if (toolMessage == null) {
            System.out.println("toolMessage no está inicializado correctamente.");
        } else {
            System.out.println("toolMessage está listo.");
        }

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

    public void deleteAxe() {
        axe.setImage(new Image(getClass().getResourceAsStream(PATH+"/objects/tools/blackaxe.png")));
    }

    public void deleteHammer() {
        hammer.setImage(new Image(getClass().getResourceAsStream(PATH+"/objects/tools/blackhammer.png")));
    }

    public void deleteSword() {
        sword.setImage(new Image(getClass().getResourceAsStream(PATH+"/objects/tools/blacksword.png")));
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
                case SECONDSTAGE -> {
                    if(screenB!=null)
                        screenB.onKeyPressed(keyEvent);
                }
                /**
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

                case SECONDSTAGE -> {
                    if(screenB!=null)
                        screenB.onKeyRelease(keyEvent);
                }
                /**
                case THIRDSTAGE -> {
                    if(screenC!=null)
                        screenC.onKeyRelease(keyEvent);
                }
                 */
            }
        });

    }
    public void screenAStart() {
        screenA = new ScreenA(this.canvas, player); // Crear la pantalla A
        stage = Stage.FIRSTSTAGE; // Establecer el escenario como FIRSTSTAGE

        // Ciclo para pintar ScreenA mientras el stage sea FIRSTSTAGE
        new Thread(() -> {
            while (stage == Stage.FIRSTSTAGE) {
                Platform.runLater(() -> {
                    if (screenA != null) {
                        screenA.paint();

                        // Verificar si el jugador está en el borde para cambiar de pantalla
                        if (screenA.getPlayer().getPosition().getX() > canvas.getWidth()) {
                            switchToScreenB(); // Cambiar a ScreenB si el jugador llega al borde derecho
                        }
                    }
                });

                try {
                    Thread.sleep(45);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    // Método para cambiar a ScreenB
    private void switchToScreenB() {
        stage = Stage.SECONDSTAGE; // Cambiar el estado
        screenBStart(); // Iniciar la siguiente pantalla
    }

    public void screenBStart() {
        screenB = new ScreenB(this.canvas, player); // Crear la pantalla B
        stage = Stage.SECONDSTAGE; // Establecer el escenario como SECONDSTAGE

        // Ciclo para pintar ScreenB mientras el stage sea SECONDSTAGE
        new Thread(() -> {
            while (stage == Stage.SECONDSTAGE) {
                Platform.runLater(() -> {
                    if (screenB != null) {
                        screenB.paint();

                        // Verificar si el jugador está en el borde para regresar a ScreenA
                        if (screenB.getPlayer().getPosition().getX() < 0) {
                            switchToScreenA(); // Cambiar a ScreenA si el jugador regresa al borde izquierdo
                        }
                    }
                });

                try {
                    Thread.sleep(45);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    // Método para cambiar de vuelta a ScreenA
    private void switchToScreenA() {
        stage = Stage.FIRSTSTAGE; // Cambiar el estado
        screenAStart(); // Reiniciar la pantalla A
    }

    public void switchToScreenC() {
        stage = Stage.THIRDSTAGE; // Actualiza el escenario
        screenCStart(); // Inicia la pantalla C
    }

    private void screenCStart() {
        screenC = new ScreenC(this.canvas); // Crea la nueva pantalla
        new Thread(() -> {
            while (stage == Stage.THIRDSTAGE) {
                Platform.runLater(() -> {
                    if (screenC != null)
                        screenC.paint();
                });

                try {
                    Thread.sleep(45);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }



    public void highlightTool(ToolType toolType, boolean[] toolsCollected) {
        // Reinicia los estilos de todas las herramientas
        axe.setStyle("-fx-border-color: transparent; -fx-border-width: 0;");
        hammer.setStyle("-fx-border-color: transparent; -fx-border-width: 0;");
        sword.setStyle("-fx-border-color: transparent; -fx-border-width: 0;");

        // Aplica el estilo solo si el arma ha sido recogida
        switch (toolType) {
            case AXE -> {
                if (toolsCollected[0]) {
                    axe.setStyle("-fx-border-color: yellow; -fx-border-width: 80;");
                }
            }
            case HAMMER -> {
                if (toolsCollected[1]) {
                    hammer.setStyle("-fx-border-color: yellow; -fx-border-width: 80;");
                }
            }
            case SWORD -> {
                if (toolsCollected[2]) {
                    sword.setStyle("-fx-border-color: yellow; -fx-border-width: 80;");
                }
            }
            default -> System.out.println("No se puede resaltar un arma no válida.");
        }
    }

    public void showToolMessage(String message) {
        if (toolMessage != null) {
            toolMessage.setText(message);
            toolMessage.setVisible(true);
            System.out.println("Mostrando mensaje: " + message);

            // Oculta el mensaje después de 3 segundos
            new Thread(() -> {
                try {
                    Thread.sleep(3000); // Esperar 3 segundos
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(() -> {
                    toolMessage.setVisible(false);
                    System.out.println("Mensaje oculto.");
                });
            }).start();
        } else {
            System.out.println("toolMessage es null.");
        }
    }



    

}
