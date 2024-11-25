package com.example.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Obstacle {
    private final String PATH = "/com/example/img/obstacles";
    private Canvas canvas;
    private GraphicsContext graphicsContext;
    private Position position;
    private ToolType requiredTool; // Herramienta necesaria para eliminar
    private Image obstacleImage;
    private int healthPoints;
    public Obstacle(Canvas canvas, ToolType requiredTool, double x, double y) {
        this.canvas = canvas;
        this.graphicsContext = this.canvas.getGraphicsContext2D();
        this.position = new Position(x, y);
        this.requiredTool = requiredTool;
        this.healthPoints = 10;
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
    }
}
