package com.example.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public class Stone extends Obstacle implements Collidable {
    private final String PATH = "/com/example/img/stone";
    private Image stoneImage; // Imagen del árbol

    public Stone(Canvas canvas, double x, double y, double width, double height) {
        super(canvas, x, y);
        this.stoneImage = new Image(getClass().getResourceAsStream(PATH + "/stone.png"));
        this.setRequiredTool(ToolType.HAMMER);
        setCollidable(true);
        setState(Alive.ALIVE);
    }

    public void paint(GraphicsContext gc) {
        gc.drawImage(stoneImage, this.getHitBox().getX(), this.getHitBox().getY(), this.getHitBox().getWidth(), this.getHitBox().getHeight());
        updateHitBox();

        drawHitBox(this.getGraphicsContext());

    }


}
