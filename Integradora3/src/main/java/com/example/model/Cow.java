package com.example.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

public class Cow extends Animal{
    private final String PATH = "/com/example/img/animals/cow";


    public Cow(Canvas canvas, double x, double y) {

        super(canvas, x, y, "/com/example/img/animals/cow");
        this.setHP(this.getHP() + 4);
    }



}
