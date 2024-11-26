package com.example.screens;

import com.example.control.Controller;
import com.example.model.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.Random;

public class ScreenA {
    private final String PATH = "/com/example/img/stage";

    private Canvas canvas;
    private GraphicsContext graphicsContext;
    private Player player;
    private ArrayList<Animal> animals;
    private ArrayList<Tool> tools;
    private Controller controller;  // Instancia del controlador central

    private ArrayList<Obstacle> obstacles; // Lista de obstáculos

    private ArrayList<Tree> trees; // Lista de árboles


    public Player getPlayer() {
        return player;
    }
    public ScreenA(Canvas canvas) {
        this.canvas = canvas;
        this.graphicsContext = this.canvas.getGraphicsContext2D();
        this.player = new Player(this.canvas);
        this.controller = Controller.getInstance();  // Obtener la instancia del controlador
        this.trees = new ArrayList<>();
        this.obstacles = new ArrayList<Obstacle>();
        animals = new ArrayList<>();
        tools = new ArrayList<>();
        player.setPosition(167,210);
        initEnemies();
        initTools();

        initTrees(); // Inicializar árboles
        initObstacles(); // Inicializar obstáculos
        initCrops(); // Inicializar cultivos
    }


    private void initTrees() {
        trees.add(new Tree(canvas,300, 400, 50, 70));
        trees.add(new Tree(canvas,500, 300, 50, 70));
        trees.add(new Tree(canvas,700, 200, 50, 70));
        obstacles.addAll(trees);
    }
    private void initCrops() {
        Crop crop1 = new Crop(canvas, 200, 300);
        Crop crop2 = new Crop(canvas, 400, 500);
        obstacles.add(crop1); //
        obstacles.add(crop2);
    }

    /**
     * The function initializes enemy objects with specific positions and adds them to a list of
     * enemies.
     */
    private void initEnemies(){
        Sheep animal = new Sheep(canvas,550,510);
        animals.add(animal);

        Cow animal2 = new Cow(canvas, 500,210);
        animals.add(animal2);

        Goat animal3 = new Goat(canvas, 120, 300);
        animals.add(animal3);
    }
    private void initObstacles() {

        //obstacles.add(new Obstacle(canvas, ToolType.AXE, 400, 400));
        //obstacles.add(new Obstacle(canvas, ToolType.HAMMER, 600, 300));
        //obstacles.add(new Obstacle(canvas, ToolType.SWORD, 200, 500));


    }
    private void updateInterfaceWithTool(ToolType toolType) {
        switch (toolType) {
            case AXE -> {
                controller.updateAxe();  // Actualizar solo el espacio del hacha
                System.out.println("Hacha seleccionada");
            }
            case HAMMER -> {
                controller.updateHammer();  // Actualizar solo el espacio del martillo
                System.out.println("Martillo seleccionado");
            }
            case SWORD -> {
                controller.updateSword();  // Actualizar solo el espacio de la espada
                System.out.println("Espada seleccionada");
            }
        }
    }

    private void initTools(){
        Tool sword = new Tool(canvas, ToolType.SWORD, 100, 100);
        Tool hammer = new Tool(canvas, ToolType.HAMMER, 300, 100);
        Tool axe = new Tool(canvas, ToolType.AXE, 700, 100);
        tools.add(sword);
        tools.add(hammer);
        tools.add(axe);
    }

    /**
     * The paint() function is responsible for drawing the game elements on the screen, handling
     * collisions between the player and enemies, and checking for level completion.
     */
    public void paint() {
        Image image = new Image(getClass().getResourceAsStream(PATH + "/MountainSprite1.png"));
        graphicsContext.drawImage(image, 0, 0, 1230, 1002);

        player.paint(obstacles, animals);



        // Pintar herramientas restantes y mostrar "G" si el jugador está cerca
        for (Tool tool : tools) {
            tool.paint();

            // Mostrar la letra "G" si el jugador está cerca
            if (player.checkCollision(tool.getPosition(), 50, 50)) {
                graphicsContext.fillText("G", tool.getPosition().getX() + 20, tool.getPosition().getY() - 10);
            }
        }

        // Pintar animales y manejar su movimiento
        for (Animal animal : animals) {
            animal.paint();
            animal.onMove();
        }
        // Pintar obstacles
        for (Obstacle obstacle : obstacles) {
            if (obstacle instanceof Tree){
                ((Tree)obstacle).paint(this.canvas.getGraphicsContext2D());
            }else if(obstacle instanceof Crop){
                ((Crop)obstacle).paint(this.canvas.getGraphicsContext2D());
            }


        }
    }

    /**
     * The function onKeyPressed calls the onKeyPressed method of the bomberMan object, passing in the
     * KeyEvent event as a parameter.
     * 
     * @param(event) The event parameter is of type KeyEvent, which represents a key press event.
     */

    public void useTool(){
        for (int i = 0; i < obstacles.size(); i++) {
            Obstacle obstacle = obstacles.get(i);
            if (player.checkCollision(obstacle.getPosition(), 50, 50)) {
                if (player.getCurrentTool() == obstacle.getRequiredTool()) { // Verificar herramienta
                    //Tool[] inventory = player.g
                    //for(int i = 0; i < player.get)
                    System.out.println("Obstáculo eliminado con: " + obstacle.getRequiredTool());
                    obstacles.remove(i); // Eliminar obstáculo del mapa
                    controller.updatePoints(20); // Incrementar puntos
                    break;
                } else {
                    System.out.println("Herramienta incorrecta para este obstáculo.");
                }
            }
        }
    }
    public void onKeyPressed(KeyEvent event) {
        player.onKeyPressed(event);

        switch (event.getCode().toString()) {
            case "G" -> { // Interacción con herramientas
                for (int i = 0; i < tools.size(); i++) {
                    Tool tool = tools.get(i);
                    if (player.checkCollision(tool.getPosition(), 50, 50)) {
                        tools.remove(i); // Eliminar herramienta del mapa
                        updateInterfaceWithTool(tool.getToolType()); // Actualizar interfaz
                        controller.updatePoints(10); // Incrementar puntos
                        // Marcar herramienta como recogida
                        switch (tool.getToolType()) {
                            case AXE -> player.toolsCollected[0] = true;
                            case HAMMER -> player.toolsCollected[1] = true;
                            case SWORD -> player.toolsCollected[2] = true;
                        }
                        break;
                    }
                }
            }
            case "H" -> { // Interacción con árboles
                System.out.println("Intentando talar un árbol..."); // Debugging
                for (int i = 0; i < trees.size(); i++) {
                    Tree tree = trees.get(i);

                    // Verificar si el jugador está cerca del árbol
                    if (tree.getHitBox().intersects(player.getPosition().getX(), player.getPosition().getY(), 51, 90)) {
                        System.out.println("Jugador está cerca de un árbol."); // Debugging

                        // Verificar si tiene el hacha equipada
                        if (player.getCurrentTool() == ToolType.AXE) {
                            System.out.println("Árbol talado con el hacha."); // Debugging

                            // Eliminar el árbol del mapa
                            trees.remove(i);

                            // Sumar puntos
                            controller.updatePoints(10);

                            // Mostrar mensaje
                            controller.getGameScreenController().showToolMessage("¡Árbol talado! +10 puntos");
                            break;
                        } else {
                            System.out.println("Necesitas un hacha para talar este árbol."); // Debugging
                            controller.getGameScreenController().showToolMessage("Necesitas un hacha para talar este árbol");
                        }
                    }
                }
                // Interacción con cultivos
                for (Obstacle obstacle : obstacles) {
                    if (obstacle instanceof Crop crop) {
                        if (player.getInteractionArea().intersects(
                                crop.getHitBox().getX(), crop.getHitBox().getY(),
                                crop.getHitBox().getWidth(), crop.getHitBox().getHeight())) {
                            if (crop.getCropState() == CropState.EMPTY) {
                                crop.plant(); // Planta algo en el cultivo
                                controller.updatePoints(5);
                            } else if (crop.getCropState() == CropState.GROWN) {
                                crop.harvest(); // Cosecha
                                controller.updatePoints(10);
                            } else {
                                System.out.println("Este cultivo ya está ocupado.");
                            }
                            break;
                        }
                    }
                }
            }
            case "DIGIT1" -> { // Seleccionar hacha
                player.equipTool(0);
                controller.getGameScreenController().highlightTool(ToolType.AXE, player.getToolsCollected());
                controller.getGameScreenController().showToolMessage("Hacha seleccionada");
            }
            case "DIGIT2" -> { // Seleccionar martillo
                player.equipTool(1);
                controller.getGameScreenController().highlightTool(ToolType.HAMMER, player.getToolsCollected());
                controller.getGameScreenController().showToolMessage("Martillo seleccionado");
            }
            case "DIGIT3" -> { // Seleccionar espada
                player.equipTool(2);
                controller.getGameScreenController().highlightTool(ToolType.SWORD, player.getToolsCollected());
                controller.getGameScreenController().showToolMessage("Espada seleccionada");
            }
            default -> System.out.println("Tecla no asignada: " + event.getCode());
        }
    }


    /**
     * The function calls the onKeyRelease method of the bomberMan object, passing in the KeyEvent
     * event as a parameter.
     * 
     * @param event The event parameter is an object of type KeyEvent, which represents a key release
     * event.
     */


    public void onKeyRelease(KeyEvent event){
        this.player.onKeyRelease(event);
    }
}