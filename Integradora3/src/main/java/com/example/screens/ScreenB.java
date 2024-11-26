package com.example.screens;
import com.example.control.Controller;
import com.example.model.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.Random;

public class ScreenB implements Screen {
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

    private ArrayList<Crop> crops; // Lista de árboles

    private ScreenA previousScreen;

    public Player getPlayer() {
        return player;
    }
    public ScreenB(Canvas canvas, Player player) {
        this.canvas = canvas;
        this.graphicsContext = this.canvas.getGraphicsContext2D();
        this.player = player;
        this.controller = Controller.getInstance();  // Obtener la instancia del controlador
        this.obstacles = new ArrayList<>();
        this.trees = new ArrayList<>();
        this.stones = new ArrayList<>();
        this.crops = new ArrayList<>();
        animals = new ArrayList<>();
        tools = new ArrayList<>();
        player.setPosition(750, 800); // Coordenadas donde está parado en la imagen
        initEnemies();
        initTools();
        initObstacles(); // Inicializar obstáculos
        initTrees(); // Inicializar árboles
        initCrops(); // Inicializar cultivos
        initStones();
    }

    /**
     * The function initializes enemy objects with specific positions and adds them to a list of
     * enemies.
     */
    private void initTrees() {
        trees.clear();
        // Zona boscosa
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                int x = 50 + i * 70; // Espaciado
                int y = 50 + j * 90;
                trees.add(new Tree(canvas, x, y, 80, 110)); // Árbol grande
            }
        }

        // Árboles individuales en otras áreas
        trees.add(new Tree(canvas, 500, 250, 80, 110));
        trees.add(new Tree(canvas, 600, 350, 80, 110));
        obstacles.addAll(trees);
    }

    private void initCrops() {
        crops.clear();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                int x = 700 + i * 50; // Más a la derecha
                int y = 650 + j * 50; // Más abajo
                crops.add(new Crop(canvas, x, y));
            }
        }
        obstacles.addAll(crops);
    }



    private void initStones() {
        stones.clear();
        stones.add(new Stone(canvas, 450, 200, 50, 50));
        stones.add(new Stone(canvas, 550, 300, 50, 50));
        stones.add(new Stone(canvas, 400, 350, 50, 50)); // Mejor distribución
        stones.add(new Stone(canvas, 350, 450, 50, 50)); // Ajustado para mejor balance
        stones.add(new Stone(canvas, 600, 400, 50, 50));
        obstacles.addAll(stones);
    }



    private void initEnemies(){
        animals.clear();

        // Colocar animales en posiciones más separadas y equilibradas
        animals.add(new Sheep(canvas, 400, 450)); // Separado de otros elementos
        animals.add(new Cow(canvas, 650, 350));  // Más cerca del borde superior derecho
        animals.add(new Goat(canvas, 200, 500)); // Más hacia la parte inferior izquierda
        animals.add(new Sheep(canvas, 500, 150)); // Cerca del centro, pero sin superposición
        animals.add(new Cow(canvas, 300, 200)); // Zona superior izquierda
        animals.add(new Goat(canvas, 600, 500)); // Inferior derecha, alejado del resto

    }
    private void initObstacles() {
        obstacles.add(new Obstacle(canvas, ToolType.AXE, 400, 400));
        obstacles.add(new Obstacle(canvas, ToolType.HAMMER, 600, 300));
        obstacles.add(new Obstacle(canvas, ToolType.SWORD, 200, 500));
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
        tools.clear();
        // Espada y martillo únicamente
        Tool sword = new Tool(canvas, ToolType.SWORD, 750, 150); // Esquina superior derecha
        Tool hammer = new Tool(canvas, ToolType.HAMMER, 150, 450); // Parte inferior izquierda
        tools.add(sword);
        tools.add(hammer);
    }


    /**
     * The paint() function is responsible for drawing the game elements on the screen, handling
     * collisions between the player and enemies, and checking for level completion.
     */
    @Override
    public void paint() {
        Image image = new Image(getClass().getResourceAsStream(PATH + "/MountainSprite2.png"));
        graphicsContext.drawImage(image, 0, 0, 1230, 1002);

        // Detectar si el jugador llega al borde izquierdo para regresar a la pantalla anterior
        if (player.getPosition().getY() > canvas.getHeight()) { // Detectar borde inferior
            System.out.println("Cambiando a ScreenA desde el borde inferior...");
            controller.switchToScreenA(); // Cambiar a ScreenA
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
     * @param event The event parameter is of type KeyEvent, which represents a key press event.
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
                            case AXE -> player.toolsCollected[0] = true;
                            case HAMMER -> player.toolsCollected[1] = true;
                            case SWORD -> player.toolsCollected[2] = true;
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
     * event.
     */
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

    public void onKeyRelease(KeyEvent event){
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