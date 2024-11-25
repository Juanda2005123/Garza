package com.example.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.*;
public class Obstacle implements Collidable{
    private final String PATH = "/com/example/img/obstacles";
    private Canvas canvas;
    private GraphicsContext graphicsContext;
    private Position position;
    private ToolType requiredTool; // Herramienta necesaria para eliminar
    private Image obstacleImage;
    private Alive state;
    private int healthPoints;
    private Rectangle hitBox;
    public Obstacle(Canvas canvas, ToolType requiredTool, double x, double y) {
        this.canvas = canvas;
        this.graphicsContext = this.canvas.getGraphicsContext2D();
        this.position = new Position(x, y);
        this.requiredTool = requiredTool;
        this.healthPoints = 10;
    }
    public void updateHitBox() {
        hitBox.setX(position.getX());
        hitBox.setY(position.getY());
    }

    public void drawHitBox(GraphicsContext gc) {
        gc.setStroke(javafx.scene.paint.Color.RED); // Color rojo para la hitbox
        gc.strokeRect(hitBox.getX(), hitBox.getY(), hitBox.getWidth(), hitBox.getHeight());
    }
    @Override
    public Rectangle getHitBox() {
        return hitBox;
    }
    @Override
    public boolean isCollidable() {
        return true; // Siempre es colisionable
    }
    public Position getPosition() {
        return position;
    }

    public ToolType getRequiredTool() {
        return requiredTool;
    }
    public int getHP(){return this.healthPoints;}
    public void setHP(int healthPoints){this.healthPoints = healthPoints;}
    public void paint() {

        graphicsContext.drawImage(obstacleImage, position.getX(), position.getY(), 50, 50);
        // Actualiza la hitbox después de dibujar el sprite
        updateHitBox();
        // Opcional: Dibuja la hitbox para depuración. Comentar antes de jugar.
        drawHitBox(graphicsContext);
    }
    public void damage(int damage){
        this.healthPoints = healthPoints-damage;
        if(this.healthPoints <= 0){
            this.setState(Alive.DEAD);
        }

    }
    public void setState(Alive state){this.state = state;}
    public Alive getState(){return this.state;}
}
