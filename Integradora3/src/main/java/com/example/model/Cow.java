package com.example.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

public class Cow extends Animal{
    private final String PATH = "/com/example/img/animals/cow";


    public Cow(Canvas canvas, double x, double y, Position position1, Position position2, boolean horizontal) {

        super(canvas, x, y, "/com/example/img/animals/cow", position1,position2, horizontal);
        this.setHP(this.getHP() + 4);
    }



}
