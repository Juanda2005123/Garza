package com.example.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public class Tree {
    private final String PATH = "/com/example/img/trees";
    private Rectangle hitBox; // Hitbox del árbol para colisiones
    private Image treeImage; // Imagen del árbol

    public Tree(double x, double y, double width, double height) {
        this.hitBox = new Rectangle(x, y, width, height);
        this.treeImage = new Image(getClass().getResourceAsStream(PATH + "/tree.png"));
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public void paint(GraphicsContext gc) {
        // Dibujar la imagen del árbol
        gc.drawImage(treeImage, hitBox.getX(), hitBox.getY(), hitBox.getWidth(), hitBox.getHeight());
    }
}
