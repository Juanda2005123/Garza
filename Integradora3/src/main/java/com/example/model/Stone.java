package com.example.model;

import javafx.scene.canvas.Canvas;

public class Stone extends Obstacle{
    public Stone(Canvas canvas, ToolType requiredTool, double x, double y, String PATH){
        super(canvas,requiredTool,x,y);
        this.setState(Alive.ALIVE);
        setHitBox(x, y, 50, 50);
    }
}
