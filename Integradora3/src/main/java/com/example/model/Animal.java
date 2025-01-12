
package com.example.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public abstract class Animal implements Collidable {

    // Gráficos
    protected Canvas canvas;
    protected GraphicsContext graphicsContext;

    // Animaciones
    protected ArrayList<Image> runRight;
    protected ArrayList<Image> runLeft;
    protected ArrayList<Image> runUp;
    protected ArrayList<Image> runDown;
    protected ArrayList<Image> dead;

    // Control de frames
    protected int frame;
    protected int frameTime;
    protected Alive alive;

    // Espaciales
    protected Position position;
    protected State state;
    protected int currentTargetIndex;
    protected Timer timer;
    protected int distance;
    protected int speed;
    private Rectangle hitBox;
    private int healthPoints;
    private boolean horizontal;
    private ArrayList<Position> movementPattern;

    /**
     * The function returns a Timer object.
     * 
     * @return The method is returning a Timer object.
     */
    public Timer getTimer() {
        return timer;
    }

    /**
     * The function sets the value of the timer variable.
     * 
     * @param timer The "timer" parameter is an instance of the Timer class.
     */
    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    /**
     * The function returns the value of the distance variable.
     * 
     * @return The method is returning the value of the variable "distance".
     */
    public int getDistance() {
        return distance;
    }

    /**
     * The function sets the value of the distance variable.
     * 
     * @param distance The distance parameter is an integer that represents the distance value to be
     * set.
     */
    public void setDistance(int distance) {
        this.distance = distance;
    }

    public Animal(Canvas canvas, double x, double y, String Path, Position position1, Position position2, boolean horizontal) {
        movementPattern = new ArrayList<>();
        movementPattern.add(position1);
        movementPattern.add(position2);

        this.horizontal = horizontal;
        speed = 4;
        distance = 150;
        this.canvas = canvas;
        this.graphicsContext = this.canvas.getGraphicsContext2D();
        this.setPosition(x,y);
        state = State.DOWN;
        frame = 0;
        frameTime = 0;
        alive = Alive.ALIVE;
        healthPoints = 5;
        runDown = new ArrayList<>();
        runUp = new ArrayList<>();
        runLeft = new ArrayList<>();
        runRight = new ArrayList<>();
        dead = new ArrayList<>();
        hitBox = new Rectangle(position.getX(), position.getY(), 50, 50);
        currentTargetIndex = 0;
        initImages(Path);
    }
    @Override
    public Rectangle getHitBox() {
        return hitBox;
    }
    @Override
    public boolean isCollidable() {
        return true; // Siempre es colisionable
    }
    /**
     * The function initializes and loads images into different lists for different directions and
     * states.
     */
    protected void initImages(String PATH){
        for (int i = 1; i <= 4; i++) { //runs
            if(i < 4) {
                Image imageDown = new Image(getClass().getResourceAsStream(PATH + "/down/down" + i + ".png"));
                runDown.add(imageDown);
                Image imageUp = new Image(getClass().getResourceAsStream(PATH + "/up/up" + i + ".png"));
                runUp.add(imageUp);
                Image imageLeft = new Image(getClass().getResourceAsStream(PATH + "/left/left" + i + ".png"));
                runLeft.add(imageLeft);
                Image imageRight = new Image(getClass().getResourceAsStream(PATH + "/right/right" + i + ".png"));
                runRight.add(imageRight);
            }

            Image image = new Image(getClass().getResourceAsStream(PATH + "/dead/dead" + i + ".png"));
            dead.add(image);
        }
    }

    public Alive getAlive(){return alive;}
    public void updateHitBox() {
        hitBox.setX(position.getX());
        hitBox.setY(position.getY());
    }

    public void drawHitBox(GraphicsContext gc) {
        gc.setStroke(javafx.scene.paint.Color.RED); // Color rojo para la hitbox
        gc.strokeRect(hitBox.getX(), hitBox.getY(), hitBox.getWidth(), hitBox.getHeight());
    }

    /**
     * The function sets the position of an object to the specified x and y coordinates.
     * 
     * @param x The x-coordinate of the position.
     * @param y The y parameter represents the y-coordinate of the position.
     */
    public void setPosition(double x, double y) {
        if (position == null)
            position = new Position(x, y);
        else {
            position.setX(x);
            position.setY(y);
        }
    }

    /**
     * The function sets the "alive" variable to the given "Alive" object.
     * 
     * @param alive The "alive" parameter is of type "Alive". It is used to set the value of the
     * "alive" instance variable in the current object.
     */
    public void setAlive(Alive alive) {
        this.alive = alive;
    }
    public void setState(State state) {
        this.state = state;
    }
/**
 * The paint() function is responsible for drawing the character on the screen based on its state and
 * position.
 */

    public void paint() {
        if(alive==Alive.ALIVE){
            switch (state) {
                //runs

                case DOWN -> graphicsContext.drawImage(runDown.get(frame % 3), position.getX(), position.getY(), 51, 90);
                case UP -> graphicsContext.drawImage(runUp.get(frame % 3), position.getX(), position.getY(), 51, 90);
                case LEFT -> graphicsContext.drawImage(runLeft.get(frame % 3), position.getX(), position.getY(), 51, 90);
                case RIGHT -> graphicsContext.drawImage(runRight.get(frame % 3), position.getX(), position.getY(), 51, 90);
            }
            if (frameTime <= 3) {
                frame--;
                frameTime++;
            } else {
                frameTime = 0;

            }
            frame++;
            // Actualiza la hitbox después de dibujar el sprite
            updateHitBox();

            // Opcional: Dibuja la hitbox para depuración. Comentar antes de jugar.
            drawHitBox(graphicsContext);

        } else {
            if (frame > 10)
                frame = 0;

            graphicsContext.drawImage(dead.get(frame % 4), position.getX(), position.getY(), 51, 90);

            if(frame > 5) {
                System.out.println("MUERE Y DESAPARECE");
            }
        }

    }

    /**
     * The `onMove()` function is responsible for moving an object based on a predefined movement
     * pattern, while also checking for collisions and updating the current target index.
     */
    public void onMove() {

        if(alive==Alive.ALIVE){
            if(horizontal){
                if(currentTargetIndex==0){
                    state = State.LEFT;
                    position.setX(position.getX()-4);

                    if(position.getX()<movementPattern.get(currentTargetIndex).getX()){
                        currentTargetIndex = 1;
                    }

                } else {
                    state = State.RIGHT;
                    position.setX(position.getX()+4);


                    if(position.getX()>movementPattern.get(currentTargetIndex).getX()){
                        currentTargetIndex = 0;
                    }
                }

            } else {
                if(currentTargetIndex==0){
                    state = State.UP;
                    position.setY(position.getY()-4);


                    if(position.getY()<movementPattern.get(currentTargetIndex).getY()){
                        currentTargetIndex = 1;
                    }

                } else {
                    state = State.DOWN;
                    position.setY(position.getY()+4);

                    if(position.getY()>movementPattern.get(currentTargetIndex).getY()){
                        currentTargetIndex = 0;
                    }
                }
            }

        }
    }

    /**
     * The function returns the position.
     * 
     * @return The method is returning the value of the variable "position", which is of type
     * "Position".
     */
    public Position getPosition(){
        return position;
    }

    public int getHP(){return this.healthPoints;}

    public void setHP(int healthPoints){this.healthPoints = healthPoints;}

}






