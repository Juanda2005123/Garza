package com.example.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Timer;
import java.util.TimerTask;
public class Crop extends Obstacle implements Collidable {

    private final String PATH = "/com/example/img/crops/bosque";
    private Image emptyImage; // Imagen cuando no hay nada plantado
    private Image plantedImage; // Imagen cuando algo está plantado
    private Image grownImage; // Imagen cuando el cultivo ha crecido
    private CropState cropState; // Estado del cultivo
    private Timer growthTimer; // Temporizador para el crecimiento
    public Crop(Canvas canvas, double x, double y) {
        super(canvas, ToolType.NA, x, y); // No necesita herramienta para interactuar
        this.cropState = CropState.EMPTY; // Inicialmente vacío
        setCollidable(true); // Bloquea al jugador
        initImages();
        this.growthTimer = new Timer();
    }

    private void initImages() {
        // Inicializar imágenes para los distintos estados del cultivo
        emptyImage = new Image(getClass().getResourceAsStream(PATH + "/Tierra.png"));
        plantedImage = new Image(getClass().getResourceAsStream(PATH + "/bosque2.png"));
        grownImage = new Image(getClass().getResourceAsStream(PATH + "/bosque8.png"));
    }

    public CropState getCropState() {
        return cropState;
    }

    public void setCropState(CropState cropState) {
        this.cropState = cropState;
    }

    public void paint(GraphicsContext gc) {
        // Dibujar la imagen correspondiente al estado del cultivo
        switch (cropState) {
            case EMPTY -> gc.drawImage(emptyImage, getHitBox().getX(), getHitBox().getY(), getHitBox().getWidth(), getHitBox().getHeight());
            case PLANTED -> gc.drawImage(plantedImage, getHitBox().getX(), getHitBox().getY(), getHitBox().getWidth(), getHitBox().getHeight());
            case GROWN -> gc.drawImage(grownImage, getHitBox().getX(), getHitBox().getY(), getHitBox().getWidth(), getHitBox().getHeight());
        }

        // Opcional: dibujar la hitbox para depuración
        drawHitBox(gc);
    }

    public void plant() {
        if (cropState == CropState.EMPTY) {
            cropState = CropState.PLANTED;
            startGrowthTimer();
            System.out.println("Cultivo plantado.");

        } else {
            System.out.println("No puedes plantar aquí.");
        }
    }

    public void harvest() {
        if (cropState == CropState.GROWN) {
            cropState = CropState.EMPTY;
            System.out.println("Cosecha realizada.");
        } else {
            System.out.println("El cultivo aún no está listo.");
        }
    }

    private void startGrowthTimer() {
        // Duración aleatoria entre 30 segundos y 3 minutos (en milisegundos)
        int growthDuration = 30000 + (int) (Math.random() * (180000 - 30000));

        System.out.println("El cultivo crecerá en " + growthDuration / 1000 + " segundos.");

        growthTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                cropState = CropState.GROWN; // Cambia el estado a GROWN
                System.out.println("El cultivo está listo para cosechar.");
            }
        }, growthDuration);
    }
}

