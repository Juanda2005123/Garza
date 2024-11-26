package com.example.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

public class Sheep extends Animal{
    private final String PATH = "/com/example/img/animals/sheep";

    public Sheep(Canvas canvas, double x, double y, Position position1, Position position2, boolean horizontal) {
        super(canvas, x, y, "/com/example/img/animals/sheep", position1,position2, horizontal);
    }


}
