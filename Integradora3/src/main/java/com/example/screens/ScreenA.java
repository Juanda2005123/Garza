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


    public Player getPlayer() {
        return player;
    }
    public ScreenA(Canvas canvas) {
        this.canvas = canvas;
        this.graphicsContext = this.canvas.getGraphicsContext2D();
        this.player = new Player(this.canvas);
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
    public void paint(){
        Image image = new Image(getClass().getResourceAsStream(PATH+"/Stage1.png"));
        graphicsContext.drawImage(image,0,0,1230,1002);

        player.paint();
        for (var animal:
             animals) {
            animal.paint();
            animal.onMove();
        }
        for(var tool: tools){
            tool.paint();
        }





    }
    /**
     * The function onKeyPressed calls the onKeyPressed method of the bomberMan object, passing in the
     * KeyEvent event as a parameter.
     * 
     * @param event The event parameter is of type KeyEvent, which represents a key press event.
     */
    public void onKeyPressed(KeyEvent event){
        this.player.onKeyPressed(event);
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