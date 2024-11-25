package com.example.model;

import javafx.scene.shape.Rectangle;
public interface Collidable {
    Rectangle getHitBox(); // Devuelve la hitbox del objeto
    boolean isCollidable(); // Define si el objeto puede colisionar
}
