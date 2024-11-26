package com.example.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public class Tree extends Obstacle implements Collidable {
    private final String PATH = "/com/example/img/trees";
    private Image treeImage; // Imagen del árbol

    public Tree(Canvas canvas, double x, double y, double width, double height) {
        super(canvas, x, y);
        this.treeImage = new Image(getClass().getResourceAsStream(PATH + "/tree.png"));
        this.setRequiredTool(ToolType.AXE);
    }

    public void paint(GraphicsContext gc) {
        // Dibujar la imagen del árbol
        gc.drawImage(treeImage, this.getHitBox().getX(), this.getHitBox().getY(), this.getHitBox().getWidth(), this.getHitBox().getHeight());
        // Actualiza la hitbox después de dibujar el sprite
        updateHitBox();

        // Opcional: Dibuja la hitbox para depuración. Comentar antes de jugar.
        drawHitBox(this.getGraphicsContext());
    }
}
