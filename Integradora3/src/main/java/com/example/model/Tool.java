package com.example.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Tool implements Collidable{
    private final String PATH = "/com/example/img/objects/tools";
    private Canvas canvas;
    private GraphicsContext graphicsContext;
    private ToolType toolType;
    private Image hammer;
    private Image axe;
    private Image sword;
    private int fullDamage;
    private int minimunDamage;
    private Position position;
    private int durability;
    private Alive alive;

    public Tool(Canvas canvas, ToolType toolType, int x, int y) {
        this.canvas = canvas;
        this.graphicsContext = this.canvas.getGraphicsContext2D();
        this.toolType = toolType;
        this.position = new Position(x,y);
        this.minimunDamage = 1;
        this.fullDamage = 4;
        durability = 10;
        this.alive = Alive.ALIVE;
        initImages();
    }

    public Alive getAlive() {
        return alive;
    }

    public void setAlive(Alive alive) {
        this.alive = alive;
    }

    public int getDurability() {
        return durability;
    }
    public Alive reduceDurability() {
        if ((durability - 1) > 0) {
            this.durability--;
        } else {
            durability = 0;
            this.setAlive(Alive.DEAD);
        }
        return this.getAlive();
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    public Tool() {
        super();
    }

    private void initImages(){
        hammer = new Image(getClass().getResourceAsStream(PATH+"/hammer.png"));
        axe = new Image(getClass().getResourceAsStream(PATH+"/axe.png"));
        sword = new Image(getClass().getResourceAsStream(PATH+"/sword.png"));

    }
    /**
     * The function sets the position of an object to the specified x and y coordinates.
     *
     * @param x The x-coordinate of the position.
     * @param y The y parameter represents the y-coordinate of the position.
     */
    public void setPosition(double x, double y){
        if(position==null)
            position = new Position(x,y);
        else {
            position.setX(x);
            position.setY(y);
        }
    }



    /**
     * The paint() function draws an image based on the value of the wildCardType variable.
     */

    public void paint() {
        switch (toolType) {

            case AXE -> graphicsContext.drawImage(axe, position.getX(), position.getY(), 50, 50);
            case HAMMER -> graphicsContext.drawImage(hammer, position.getX(), position.getY(), 50, 50);
            case SWORD -> graphicsContext.drawImage(sword, position.getX(), position.getY(), 50,50);
        }

    }

    public ToolType getToolType() {
        return toolType;
    }

    public void setToolType(ToolType toolType) {
        this.toolType = toolType;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
    public int getFullDamage(){return this.fullDamage;}
    public void setFullDamage(int fullDamage){this.fullDamage = fullDamage;}
    public int getMinimunDamage(){return this.minimunDamage;}
    public void setMinimunDamage(int minimunDamage){this.minimunDamage = minimunDamage;}

    @Override
    public Rectangle getHitBox() {
        return null;
    }

    @Override
    public boolean isCollidable() {
        return false;
    }
}
