package com.example.screens;

import com.example.control.Controller;
import com.example.model.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.Random;

public class ScreenC implements Screen {
    private final String PATH = "/com/example/img/stage";

    private Canvas canvas;
    private GraphicsContext graphicsContext;
    private Player player;
    private ArrayList<Animal> animals;
    private ArrayList<Tool> tools;
    private Controller controller;  // Instancia del controlador central

    private ArrayList<Obstacle> obstacles; // Lista de obstáculos

    private ArrayList<Tree> trees; // Lista de árboles
    private ArrayList<Stone> stones; // Lista de árboles

    private ScreenB nextScreen;

    public Player getPlayer() {
        return player;
    }

    public ScreenC(Canvas canvas) {
        this.canvas = canvas;
        this.graphicsContext = this.canvas.getGraphicsContext2D();
        this.player = new Player(this.canvas);
        this.controller = Controller.getInstance();  // Obtener la instancia del controlador
        this.trees = new ArrayList<>();
        this.obstacles = new ArrayList<Obstacle>();
        this.stones = new ArrayList<>();
        animals = new ArrayList<>();
        tools = new ArrayList<>();
        player.setPosition(167, 210);
        //initEnemies();
        initTools();
        initStones();
        initTrees(); // Inicializar árboles
        initCrops(); // Inicializar cultivos
        initEnemies();
    }


    private void initTrees() {
        trees.add(new Tree(canvas, 300, 400, 50, 70));
        trees.add(new Tree(canvas, 500, 300, 50, 70));
        trees.add(new Tree(canvas, 700, 200, 50, 70));
        obstacles.addAll(trees);
    }

    private void initCrops() {
        Crop crop1 = new Crop(canvas, 200, 300);
        Crop crop2 = new Crop(canvas, 400, 500);
        obstacles.add(crop1); //
        obstacles.add(crop2);
    }
    private void initEnemies(){
        animals.clear();
        Position position1 = new Position(400,300);
        Position position2 = new Position(600, 200);
        Position position3 = new Position(700, 150);
        Position position4 = new Position(1000, 1000);
        Position position5 = new Position(800, 200);
        Position position6 = new Position(850, 1100);
        Position position7 = new Position(800, 750);
        Position position8 = new Position(850, 1050);
        Position position9 = new Position(300, 200);
        Position position10 = new Position(1050, 450);

        animals.add(new Sheep(canvas, 400, 250, position1, position2, true)); // Separado de otros elementos
        animals.add(new Cow(canvas, 1000, 350, position3,position4, false));  // Más cerca del borde superior derecho
        animals.add(new Goat(canvas, 800, 400,position5,position6, false)); // Más hacia la parte inferior izquierda
        animals.add(new Sheep(canvas, 500, 800,position7,position8, true)); // Cerca del centro, pero sin superposición
        animals.add(new Cow(canvas, 300, 900, position9,position10, true)); // Zona superior izquierda

    }

    public void setNextScreen(ScreenB screen) {
        this.nextScreen = screen;
    }

    private void initStones() {
        // Piedras distribuidas de manera estratégica, evitando obstrucciones directas
        stones.add(new Stone(canvas, 300, 50, 50, 50));  // Zona superior
        stones.add(new Stone(canvas, 550, 300, 50, 50)); // Centro-derecha
        stones.add(new Stone(canvas, 200, 350, 50, 50)); // Centro-izquierda
        stones.add(new Stone(canvas, 450, 550, 50, 50)); // Cerca de cultivos
        stones.add(new Stone(canvas, 700, 450, 50, 50)); // Esquina derecha
        obstacles.addAll(stones);
    }

    private void updateInterfaceWithTool(ToolType toolType) {
        switch (toolType) {
            case AXE -> {
                controller.updateAxe();  // Actualizar solo el espacio del hacha
                System.out.println("Hacha seleccionada");
            }
            case HAMMER -> {
                controller.updateHammer();  // Actualizar solo el espacio del martillo
                System.out.println("Martillo seleccionado");
            }
            case SWORD -> {
                controller.updateSword();  // Actualizar solo el espacio de la espada
                System.out.println("Espada seleccionada");
            }
        }
    }

    private void initTools() {

        //if (player.toolsCollected[2]){
            Tool sword = new Tool(canvas, ToolType.SWORD, 100, 100);
            tools.add(sword);
        //}

    }


    private void updateInterfaceWithToolDeleted(ToolType toolType) {
        switch (toolType) {
            case AXE -> {
                controller.deleteAxe();  // Actualizar solo el espacio del hacha
                System.out.println("Hacha seleccionada");
            }
            case HAMMER -> {
                controller.deleteHammer();  // Actualizar solo el espacio del martillo
                System.out.println("Martillo seleccionado");
            }
            case SWORD -> {
                controller.deleteSword();  // Actualizar solo el espacio de la espada
                System.out.println("Espada seleccionada");
            }
        }
    }

    /**
     * The paint() function is responsible for drawing the game elements on the screen, handling
     * collisions between the player and enemies, and checking for level completion.
     */
    @Override
    public void paint() {
        Image image = new Image(getClass().getResourceAsStream(PATH + "/MountainSprite3.png"));
        graphicsContext.drawImage(image, 0, 0, 1230, 1002);

        if (player.getPosition().getY() < 0) { // Detectar borde superior
            System.out.println("Cambiando a ScreenB desde el borde superior...");
            controller.switchToScreenB(); // Cambiar a ScreenB
        }


        player.paint(obstacles, animals);


        // Pintar herramientas restantes y mostrar "G" si el jugador está cerca
        for (Tool tool : tools) {
            tool.paint();

            // Mostrar la letra "G" si el jugador está cerca
            if (player.checkCollision(tool.getPosition(), 50, 50)) {
                graphicsContext.fillText("G", tool.getPosition().getX() + 20, tool.getPosition().getY() - 10);
            }
        }

        // Pintar animales y manejar su movimiento
        for (Animal animal : animals) {
            animal.paint();
            animal.onMove();
        }
        // Pintar obstacles
        for (Obstacle obstacle : obstacles) {
            if (obstacle instanceof Tree){
                ((Tree)obstacle).paint(this.canvas.getGraphicsContext2D());
            }else if(obstacle instanceof Crop){
                ((Crop)obstacle).paint(this.canvas.getGraphicsContext2D());
            } else if (obstacle instanceof Stone) {
                ((Stone)obstacle).paint(this.canvas.getGraphicsContext2D());
            }
        }
    }

    /**
     * The function onKeyPressed calls the onKeyPressed method of the bomberMan object, passing in the
     * KeyEvent event as a parameter.
     *
     * @param(event) The event parameter is of type KeyEvent, which represents a key press event.
     */
    public void onKeyPressed(KeyEvent event) {
        player.onKeyPressed(event);

        switch (event.getCode().toString()) {
            case "G" -> { // Interacción con herramientas
                for (int i = 0; i < tools.size(); i++) {
                    Tool tool = tools.get(i);
                    if (player.checkCollision(tool.getPosition(), 50, 50)) {
                        tools.remove(i); // Eliminar herramienta del mapa
                        updateInterfaceWithTool(tool.getToolType()); // Actualizar interfaz
                        controller.updatePoints(10); // Incrementar puntos
                        // Marcar herramienta como recogida
                        switch (tool.getToolType()) {
                            case AXE -> {
                                player.toolsCollected[0] = true;
                                player.getInventory()[0] = tool;
                            }
                            case HAMMER -> {
                                player.toolsCollected[1] = true;
                                player.getInventory()[1] = tool;
                            }
                            case SWORD -> {
                                player.toolsCollected[2] = true;
                                player.getInventory()[2] = tool;
                            }
                        }

                        break;
                    }
                }
            }
            case "H" -> {
                //Interacción con Animales
                for(Animal animal : animals){
                    if (player.getInteractionArea().intersects(animal.getHitBox().getX(), animal.getHitBox().getY(), animal.getHitBox().getWidth(), animal.getHitBox().getHeight())){
                        if(damage(player, animal)){
                            controller.updatePoints(5);
                        }else{
                            animals.remove(animal);
                            controller.updatePoints(10);
                        }
                    }
                }
                // Interacción con árboles
                // Interacción con cultivos
                for (Obstacle obstacle : obstacles) {
                    if(obstacle instanceof Tree tree){
                        if (player.getInteractionArea().intersects(tree.getHitBox().getX(), tree.getHitBox().getY(), tree.getHitBox().getWidth(), tree.getHitBox().getHeight())){
                            if(damage(player, tree)){
                                controller.updatePoints(5);
                            }else{
                                obstacles.remove(tree);
                                controller.updatePoints(10);
                            }
                        }
                    }else if(obstacle instanceof Crop crop) {

                        //Si el jugador puede plantar

                        if (player.getInteractionArea().intersects(
                                crop.getHitBox().getX(), crop.getHitBox().getY(),
                                crop.getHitBox().getWidth(), crop.getHitBox().getHeight())) {
                            if (crop.getCropState() == CropState.EMPTY) {
                                crop.plant(); // Planta algo en el cultivo
                                controller.updatePoints(5);
                            } else if (crop.getCropState() == CropState.GROWN) {
                                crop.harvest(); // Cosecha
                                controller.updatePoints(10);
                            } else {
                                System.out.println("Este cultivo ya está ocupado.");
                            }
                            break;
                        }
                    } else if(obstacle instanceof Stone stone){
                        if (player.getInteractionArea().intersects(stone.getHitBox().getX(), stone.getHitBox().getY(), stone.getHitBox().getWidth(), stone.getHitBox().getHeight())){
                            if(damage(player, stone)){
                                controller.updatePoints(5);
                            }else{
                                obstacles.remove(stone);
                                controller.updatePoints(10);
                            }
                        }
                    }
                }
            }
            case "DIGIT1" -> { // Seleccionar hacha
                player.equipTool(0);
                controller.getGameScreenController().highlightTool(ToolType.AXE, player.getToolsCollected());
                controller.getGameScreenController().showToolMessage("Hacha seleccionada");
            }
            case "DIGIT2" -> { // Seleccionar martillo
                player.equipTool(1);
                controller.getGameScreenController().highlightTool(ToolType.HAMMER, player.getToolsCollected());
                controller.getGameScreenController().showToolMessage("Martillo seleccionado");
            }
            case "DIGIT3" -> { // Seleccionar espada
                player.equipTool(2);
                controller.getGameScreenController().highlightTool(ToolType.SWORD, player.getToolsCollected());
                controller.getGameScreenController().showToolMessage("Espada seleccionada");
            }
            default -> System.out.println("Tecla no asignada: " + event.getCode());
        }
    }

    /**
     * The function calls the onKeyRelease method of the bomberMan object, passing in the KeyEvent
     * event as a parameter.
     *
     * @param event The event parameter is an object of type KeyEvent, which represents a key release
     *              event.
     */


    public void onKeyRelease(KeyEvent event) {
        this.player.onKeyRelease(event);
    }
    public boolean damage(Player player, Obstacle obstacle){
        Alive temporalState;
        ToolType tempCurrentTool = player.getCurrentTool();

        if(player.getCurrentTool() == obstacle.getRequiredTool()){
            if(obstacle.getHP() - player.findCurrentToolFullDamage(player.getCurrentTool()) > 0){
                obstacle.setHP(obstacle.getHP() - player.findCurrentToolFullDamage(player.getCurrentTool()));
            }else{
                obstacle.setHP(0);
                obstacle.setState(Alive.DEAD);
                if (obstacle instanceof Tree tree) {
                    lootLogs(player);
                } else if (obstacle instanceof Stone stone) {
                    lootStones(player);
                }
            }
            temporalState = player.reduceDurabilityCurrentTool(player.getCurrentTool());
        }else{
            if(obstacle.getHP() - player.findCurrentToolMinDamage(player.getCurrentTool()) > 0){
                obstacle.setHP(obstacle.getHP() - player.findCurrentToolMinDamage(player.getCurrentTool()));
            }else{
                obstacle.setHP(0);
                obstacle.setState(Alive.DEAD);
                if (obstacle instanceof Tree tree) {
                    lootLogs(player);
                } else if (obstacle instanceof Stone stone) {
                    lootStones(player);
                }
            }

            temporalState = player.reduceDurabilityCurrentTool(player.getCurrentTool());
        }
        if(temporalState == Alive.DEAD){
            updateInterfaceWithToolDeleted(tempCurrentTool);
            player.setCurrentTool(ToolType.NA);
            System.out.println("La herramienta " + tempCurrentTool + " se rompió.");
        }
        return (obstacle.getState() == Alive.ALIVE);
    }

    public boolean damage(Player player, Animal animal){
        Alive temporalState;
        ToolType tempCurrentTool = player.getCurrentTool();
        if(player.getCurrentTool() == ToolType.SWORD){
            if(animal.getHP() - player.findCurrentToolFullDamage(player.getCurrentTool()) > 0){
                animal.setHP(animal.getHP() - player.findCurrentToolFullDamage(player.getCurrentTool()));
            }else{
                animal.setHP(0);
                animal.setAlive(Alive.DEAD);

            }
            temporalState = player.reduceDurabilityCurrentTool(player.getCurrentTool());


        }else{
            if(animal.getHP() - player.findCurrentToolMinDamage(player.getCurrentTool()) > 0){
                animal.setHP(animal.getHP() - player.findCurrentToolMinDamage(player.getCurrentTool()));
            }else{
                animal.setHP(0);
                animal.setAlive(Alive.DEAD);
            }
            temporalState = player.reduceDurabilityCurrentTool(player.getCurrentTool());
        }
        if(temporalState == Alive.DEAD){
            updateInterfaceWithToolDeleted(tempCurrentTool);
            player.setCurrentTool(ToolType.NA);
            System.out.println("La herramienta " + tempCurrentTool + " se rompió.");
        }
        return (animal.getAlive() == Alive.ALIVE);
    }

    public void lootLogs(Player player) {
        Random random = new Random();
        int lootAmount = random.nextInt(8) + 3; // Genera entre 3 y 10 logs
        ArrayList<Log> logs = new ArrayList<>();
        for (int i = 0; i < lootAmount; i++) {
            logs.add(new Log());
        }
        player.addLogs(logs);
    }

    public void lootStones(Player player) {
        Random random = new Random();
        int lootAmount = random.nextInt(8) + 3; // Genera entre 3 y 10 small stones
        ArrayList<SmallStone> stones = new ArrayList<>();
        for (int i = 0; i < lootAmount; i++) {
            stones.add(new SmallStone());
        }
        player.addStones(stones);
    }
}
