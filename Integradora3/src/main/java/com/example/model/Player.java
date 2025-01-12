package com.example.model;

import com.example.control.Controller;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.shape.*;
public class Player {
    private final String PATH = "/com/example/img/character";
    //graficos
    private Canvas canvas;
    private GraphicsContext graphicsContext;
    //0-3
    private ArrayList<Image> idle; //0. down 1. up 2. left 3. right
    //0-3
    private ArrayList<Image> animationSwordRight;
    private ArrayList<Image> animationToolRight;
    private ArrayList<Image> animationSwordLeft;
    private ArrayList<Image> animationToolLeft;
    //0-2
    private ArrayList<Image> runRight;
    //0-2
    private ArrayList<Image> runLeft;
    //0-2
    private ArrayList<Image> runUp;
    //0-2
    private ArrayList<Image> runDown;
    private ArrayList<Log> logsInventory;
    private ArrayList<SmallStone> stonesInventory;
    //0-6
    private int frame;
    private int animation;
    private int countAnimation;
    private Alive alive;
    //espaciales
    private Position position;
    private State state;
    private boolean[] keyPressed; //0. down 1. up 2. left 3. right
    //bombs
    private double speed;
    private Controller controller;

    private ToolType currentTool; // Herramienta actualmente equipada
    private Tool[] inventory;
    public boolean[] toolsCollected; // Registro de herramientas recogidas
    private Rectangle hitBox;
    private Circle interactionArea; // Área de interacción
    private double interactionRadius = 70; // Radio del área de interacción

    /**
     * The function sets the position of an object.
     *
     * @param position The "position" parameter is of type "Position".
     */
    public void setPosition(Position position) {
        this.position = position;
    }


    public Player(Canvas canvas) {
        controller = Controller.getInstance();
        this.canvas = canvas;
        this.graphicsContext = this.canvas.getGraphicsContext2D();
        state = State.IDLEDOWN;
        frame = 0;
        animation = 0;
        countAnimation = 0;
        alive = Alive.ALIVE;
        idle = new ArrayList<>();
        runDown = new ArrayList<>();
        runUp = new ArrayList<>();
        runLeft = new ArrayList<>();
        runRight = new ArrayList<>();
        animationToolLeft = new ArrayList<>();
        animationSwordLeft = new ArrayList<>();
        animationToolRight = new ArrayList<>();
        animationSwordRight = new ArrayList<>();
        position = new Position( 0, 0);
        hitBox = new Rectangle(position.getX(), position.getY(), 51, 90);
        this.interactionArea = new Circle(position.getX() + 25, position.getY() + 45, interactionRadius); // Centro + radio
        speed = 12;
        keyPressed = new boolean[5];
        for (int i = 0; i < 5; i++) {
            keyPressed[i] = false;
        }
        this.currentTool = null; // Sin herramienta equipada al inicio
        this.toolsCollected = new boolean[3]; // Tres herramientas posibles: Hacha, Martillo, Espada
        this.inventory = new Tool[3];
        for (int i = 0; i < toolsCollected.length; i++) {
            toolsCollected[i] = false; // Ninguna herramienta recogida inicialmente
            inventory[i] = null;
        }
        System.out.println("antes imagenes");
        logsInventory = new ArrayList<>();
        stonesInventory = new ArrayList<>();
        initImages();
        toolsCollected[0] = false;
        toolsCollected[1] = false;
        toolsCollected[2] = false;
    }


    
    public void updateInteractionArea() {
        interactionArea.setCenterX(position.getX() + hitBox.getWidth() / 2);
        interactionArea.setCenterY(position.getY() + hitBox.getHeight() / 2);
    }

    /**
     * The function returns the value of the variable "frame".
     *
     * @return The method is returning the value of the variable "frame".
     */
    public int getFrame() {
        return frame;
    }

    /**
     * The function returns the graphics context.
     *
     * @return The method is returning a GraphicsContext object.
     */
    public GraphicsContext getGraphicsContext() {
        return graphicsContext;
    }


    /**
     * The function returns the value of the animation variable.
     *
     * @return The value of the variable "animation" is being returned.
     */
    public int getAnimation() {
        return animation;
    }

    /**
     * The function returns the value of the countAnimation variable.
     *
     * @return The method is returning the value of the variable "countAnimation".
     */
    public int getCountAnimation() {
        return countAnimation;
    }

    /**
     * The function returns an object of type Alive.
     *
     * @return The method is returning an object of type "Alive".
     */
    public Alive getAlive() {
        return alive;
    }

    // The above code is defining a method called `getState()` which returns the value of a variable
    // called `state`. The `state` variable is of type `State`, which is likely an enum or a custom
    // class representing the state of something.
    public State getState() {
        return state;
    }

    /**
     * The function returns an array of booleans representing the state of each key on the keyboard.
     *
     * @return The method is returning an array of booleans called "keyPressed".
     */
    public boolean[] getKeyPressed() {
        return keyPressed;
    }

    /**
     * The function returns the value of the speed variable.
     *
     * @return The method is returning the value of the variable "speed" as a double.
     */
    public double getSpeed() {
        return speed;
    }





    /**
     * The function initializes and loads various images into different lists for different animations
     * and states.
     */
    private void initImages(){
        for(int i = 1 ; i <= 3 ; i++){ //runs
            Image imageDown = new Image(getClass().getResourceAsStream(PATH+"/runs/down/down"+i+".png"));
            runDown.add(imageDown);
            Image imageUp = new Image(getClass().getResourceAsStream(PATH+"/runs/up/up"+i+".png"));
            runUp.add(imageUp);
            Image imageLeft = new Image(getClass().getResourceAsStream(PATH+"/runs/left/left"+i+".png"));
            runLeft.add(imageLeft);
            Image imageRight = new Image(getClass().getResourceAsStream(PATH+"/runs/right/right"+i+".png"));
            runRight.add(imageRight);

        }
        Image down = new Image(getClass().getResourceAsStream(PATH+"/idle/down/downIdle.png"));
        idle.add(down);

        Image up = new Image(getClass().getResourceAsStream(PATH+"/idle/up/upIdle.png"));
        idle.add(up);

        Image left = new Image(getClass().getResourceAsStream(PATH+"/idle/left/leftIdle.png"));
        idle.add(left);

        Image right = new Image(getClass().getResourceAsStream(PATH+"/idle/right/rightIdle.png"));
        idle.add(right);
        for(int i = 1 ; i <= 2 ; i++){
            Image animation = new Image(getClass().getResourceAsStream(PATH+"/animation/sword/right/sword"+i+".png"));
            animationSwordRight.add(animation);
            animation = new Image(getClass().getResourceAsStream(PATH+"/animation/sword/left/sword"+i+".png"));
            animationSwordLeft.add(animation);
        }
        for(int i = 1 ; i <= 5 ; i++){
            Image animation = new Image(getClass().getResourceAsStream(PATH+"/animation/tool/right/tool"+i+".png"));
            animationToolRight.add(animation);
            animation = new Image(getClass().getResourceAsStream(PATH+"/animation/tool/left/tool"+i+".png"));
            animationToolLeft.add(animation);
        }

    }
    /**
     * The function sets the position of an object to the specified x and y coordinates.
     * 
     * @param x The x-coordinate of the position.
     * @param y The y parameter represents the y-coordinate of the position.
     */
    public void setPosition(double x, double y){
        if(position==null)
            position = new Position(x,y);
        else {
            position.setX(x);
            position.setY(y);
        }
    }
    /**
     * The paint() function is responsible for rendering the player character on the screen based on
     * its current state and animation frames.
     */
    public void paint(ArrayList<Obstacle> obstacles, ArrayList<Animal> animals) {
        onMove(obstacles, animals); // Actualiza la posición
        if (alive == Alive.ALIVE) {
            // Dibuja el sprite según el estado actual
            switch (state) {
                case IDLEDOWN -> graphicsContext.drawImage(idle.get(0), position.getX(), position.getY(), 51, 90);
                case IDLEUP -> graphicsContext.drawImage(idle.get(1), position.getX(), position.getY(), 51, 90);
                case IDLELEFT -> graphicsContext.drawImage(idle.get(2), position.getX(), position.getY(), 51, 90);
                case IDLERIGHT -> graphicsContext.drawImage(idle.get(3), position.getX(), position.getY(), 51, 90);
                case DOWN -> graphicsContext.drawImage(runDown.get(frame % 3), position.getX(), position.getY(), 51, 90);
                case UP -> graphicsContext.drawImage(runUp.get(frame % 3), position.getX(), position.getY(), 51, 90);
                case LEFT -> graphicsContext.drawImage(runLeft.get(frame % 3), position.getX(), position.getY(), 51, 90);
                case RIGHT -> graphicsContext.drawImage(runRight.get(frame % 3), position.getX(), position.getY(), 51, 90);
            }

            // Actualiza la hitbox después de dibujar el sprite
            updateHitBox();
            updateInteractionArea();
            // Opcional: Dibuja la hitbox para depuración. Comentar antes de jugar.
            drawHitBox(graphicsContext);
            drawInteractionArea(graphicsContext);

            frame++;
            if (frame > 5000) frame = 0;
        } else {
            System.out.println("No está alive");
        }
    }



    public Circle getInteractionArea(){
        return this.interactionArea;
    }

    /**
     * The function handles key press events and updates the state and key pressed array accordingly.
     * 
     * @param event The parameter "event" is of type KeyEvent. It represents the key event that
     * occurred, such as a key press or release.
     */
    public void onKeyPressed(KeyEvent event){
        switch (event.getCode()){
            case DOWN -> {
                state = State.DOWN;
                keyPressed[0] = true;
            }
            case UP -> {
                state = State.UP;
                keyPressed[1] = true;
            }
            case LEFT -> {
                state = State.LEFT;
                keyPressed[2] = true;
            }
            case RIGHT -> {
                state = State.RIGHT;
                keyPressed[3] = true;
            }

        }
    }
    /**
     * The function updates the state and key pressed status based on the released key event.
     * 
     * @param event The parameter "event" is of type KeyEvent, which represents a key release event.
     */
    public void onKeyRelease(KeyEvent event){
        switch (event.getCode()){
            case DOWN -> {
                state = State.IDLEDOWN;
                keyPressed[0] = false;
            }
            case UP -> {
                state = State.IDLEUP;
                keyPressed[1] = false;
            }
            case LEFT -> {
                state = State.IDLELEFT;
                keyPressed[2] = false;
            }
            case RIGHT -> {
                state = State.IDLERIGHT;
                keyPressed[3] = false;
            }
        }
    }
    /**
     * The function `onMove()` handles the movement of an object based on the keys pressed, while also
     * checking for collisions and updating the object's position accordingly.
     */
    public void onMove(ArrayList<Obstacle> obstacles, ArrayList<Animal> animals) {
        if (alive == Alive.ALIVE) {
            double nextX = position.getX();
            double nextY = position.getY();

            if (keyPressed[0]) { // DOWN
                nextY += speed;
            } else if (keyPressed[1]) { // UP
                nextY -= speed;
            } else if (keyPressed[2]) { // LEFT
                nextX -= speed;
            } else if (keyPressed[3]) { // RIGHT
                nextX += speed;
            }

            // Verificar colisiones con bordes del mapa
            if (nextX >= 0 && nextX + hitBox.getWidth() <= canvas.getWidth() &&
                    nextY >= 0 && nextY + hitBox.getHeight() <= canvas.getHeight()) {

                boolean collisionDetected = false;

                // Verificar colisiones con obstáculos
                for (Obstacle obstacle : obstacles) {
                    if (obstacle.isCollidable() && checkCollisionWithObstacle(nextX, nextY, obstacle)) {
                        collisionDetected = true;
                        break;
                    }
                }

                // Solo actualiza la posición si no hay colisión
                if (!collisionDetected) {
                    position.setX(nextX);
                    position.setY(nextY);
                    updateHitBox();
                    updateInteractionArea();
                } else {
                    System.out.println("Colisión con un obstáculo.");
                }
            } else {
                // Colisión con el borde del mapa
                Stage stage = Controller.getInstance().getStage();
                if (nextY < 0) { // Detectar borde superior
                    if(stage==Stage.FIRSTSTAGE){
                        System.out.println("Jugador en el borde superior. Cambiando a ScreenB...");
                        Controller.getInstance().switchToScreenB(); // Cambiar a ScreenB
                    } else if (stage==Stage.SECONDSTAGE){
                        System.out.println("Jugador en el borde superior. Cambiando a ScreenC...");
                        Controller.getInstance().switchToScreenC(); // Cambiar a ScreenC
                    }

                } else if (nextY + hitBox.getHeight() > canvas.getHeight()) { // Detectar borde inferior
                    if(stage==Stage.SECONDSTAGE){
                        System.out.println("Jugador en el borde inferior. Cambiando a ScreenA...");
                        Controller.getInstance().switchToScreenA(); // Cambiar a ScreenA
                    } else if (stage==Stage.THIRDSTAGE){
                        System.out.println("Jugador en el borde inferior. Cambiando a ScreenB...");
                        Controller.getInstance().switchToScreenB(); // Cambiar a ScreenB
                    }

                } else {
                    System.out.println("Colisión con el borde del mapa.");
                }
            }
        }
    }



    public boolean checkCollisionWithObstacle(double nextX, double nextY, Obstacle obstacle) {
        // Crea un rectángulo simulado para verificar la posición prevista del jugador
        Rectangle futureHitBox = new Rectangle(nextX, nextY, hitBox.getWidth(), hitBox.getHeight());
        return futureHitBox.intersects(obstacle.getHitBox().getBoundsInLocal());
    }

    public void drawInteractionArea(GraphicsContext gc) {
        gc.setStroke(javafx.scene.paint.Color.BLUE); // Color azul para el área de interacción
        gc.strokeOval(
                interactionArea.getCenterX() - interactionArea.getRadius(),
                interactionArea.getCenterY() - interactionArea.getRadius(),
                interactionArea.getRadius() * 2, interactionArea.getRadius() * 2
        );
    }

    public boolean checkCollision(Position other, double width, double height) {
        return position.getX() < other.getX() + width &&
                position.getX() + 51 > other.getX() &&
                position.getY() < other.getY() + height &&
                position.getY() + 90 > other.getY();
    }

    public void updateHitBox() {
        hitBox.setX(position.getX());
        hitBox.setY(position.getY());
    }

    public void drawHitBox(GraphicsContext gc) {
        gc.setStroke(javafx.scene.paint.Color.RED); // Color rojo para la hitbox
        gc.strokeRect(hitBox.getX(), hitBox.getY(), hitBox.getWidth(), hitBox.getHeight());
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
    /**
     * The getCanvas() function returns the canvas object.
     * 
     * @return The method is returning a Canvas object.
     */
    public Canvas getCanvas(){
        return this.canvas;
    }

    public void equipTool(int toolIndex) {
        // Verificar que el índice sea válido
        if (toolIndex < 0 || toolIndex >= toolsCollected.length) {
            System.out.println("Índice de herramienta no válido.");
            return;
        }

        // Verificar si la herramienta ha sido recogida
        if (toolsCollected[toolIndex]) {
            // Equipar la herramienta según el índice
            switch (toolIndex) {
                case 0 -> currentTool = ToolType.AXE;
                case 1 -> currentTool = ToolType.HAMMER;
                case 2 -> currentTool = ToolType.SWORD;
            }
            System.out.println("Herramienta equipada: " + currentTool);
        } else {
            // Mensaje de error si la herramienta no ha sido recogida
            System.out.println("No has recogido esta herramienta aún.");
        }
    }



    public ToolType getCurrentTool() {
        System.out.println("Herramienta equipada: " + currentTool); // Debugging
        return currentTool;
    }

    public boolean[] getToolsCollected() {
        return toolsCollected; // Devuelve el estado de las herramientas recogidas
    }
    public int findCurrentToolFullDamage(ToolType toolType){
        for(int i = 0; i < inventory.length; i++){
            if(inventory[i] != null){
                if(inventory[i].getToolType() ==  toolType){
                    return inventory[i].getFullDamage();
                }
            }
        }
        return 0;
    }

    public int findCurrentToolMinDamage(ToolType toolType){
        for(int i = 0; i < inventory.length; i++){
            if(inventory[i] != null){
                if(inventory[i].getToolType() ==  toolType){
                    return inventory[i].getMinimunDamage();
                }
            }
        }
        return 1;
    }

    public Alive reduceDurabilityCurrentTool(ToolType toolType) {
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] != null && inventory[i].getToolType() == toolType) {
                System.out.println("Reduciendo durabilidad de: " + toolType + " Durabilidad actual: " + inventory[i].getDurability());
                Alive state = inventory[i].reduceDurability();
                if (state == Alive.DEAD) {
                    toolsCollected[i] = false;
                    inventory[i] = null; // Elimina la herramienta del inventario
                    System.out.println(toolType + " destruido.");
                }
                return state;
            }
        }
        System.out.println("Herramienta no encontrada: " + toolType);
        return Alive.ALIVE;
    }


    public void setCurrentTool(ToolType toolType){
        this.currentTool = toolType;
        if(this.currentTool == ToolType.NA){
            this.currentTool = null;
        }
    }

    public Tool[] getInventory() {
        return inventory;
    }
    public void addLogs(ArrayList<Log> logs) {
        logsInventory.addAll(logs);
        System.out.println("Has recogido " + logs.size() + " logs. Total: " + logsInventory.size());
    }

    public void addStones(ArrayList<SmallStone> stones) {
        stonesInventory.addAll(stones);
        System.out.println("Has recogido " + stones.size() + " small stones. Total: " + stonesInventory.size());
    }

    public ArrayList<Log> getLogsInventory() {
        return logsInventory;
    }

    public ArrayList<SmallStone> getStonesInventory() {
        return stonesInventory;
    }
}
