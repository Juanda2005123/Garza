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


    public Player getPlayer() {
        return player;
    }
    public ScreenA(Canvas canvas) {
        this.canvas = canvas;
        this.graphicsContext = this.canvas.getGraphicsContext2D();
        this.player = new Player(this.canvas);
        this.controller = Controller.getInstance();  // Obtener la instancia del controlador
        animals = new ArrayList<>();
        tools = new ArrayList<>();
        player.setPosition(167,210);
        initEnemies();
        initTools();

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
        Image image = new Image(getClass().getResourceAsStream(PATH + "/Stage1.png"));
        graphicsContext.drawImage(image, 0, 0, 1230, 1002);

        player.paint();

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
    }

    /**
     * The function onKeyPressed calls the onKeyPressed method of the bomberMan object, passing in the
     * KeyEvent event as a parameter.
     * 
     * @param event The event parameter is of type KeyEvent, which represents a key press event.
     */
    public void onKeyPressed(KeyEvent event) {
        player.onKeyPressed(event);

        if (event.getCode().toString().equals("G")) { // Si se presiona G
            for (int i = 0; i < tools.size(); i++) {
                Tool tool = tools.get(i);
                if (player.checkCollision(tool.getPosition(), 50, 50)) {
                    System.out.println("Recogiendo herramienta: " + tool.getToolType());
                    tools.remove(i);  // Eliminar herramienta del mapa
                    updateInterfaceWithTool(tool.getToolType());  // Actualizar interfaz

                    controller.updatePoints(10);  // Incrementar puntos
                    System.out.println("Puntos obtenidos: 10");
                    break;  // Salir del bucle para evitar problemas al modificar la lista
                }
            }
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