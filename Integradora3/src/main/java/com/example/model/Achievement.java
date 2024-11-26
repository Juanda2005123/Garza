package com.example.model;

public class Achievement implements Comparable<Achievement> {
    private String name;
    private String description;
    private boolean completed;

    // Constructor
    public Achievement(String name, String description) {
        this.name = name;
        this.description = description;
        this.completed = false; // Por defecto, los logros no están completados
    }

    // Métodos getters
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    // Completar el logro
    public void complete() {
        this.completed = true;
    }

    // Implementar comparación (por nombre)
    @Override
    public int compareTo(Achievement other) {
        return this.name.compareTo(other.name);
    }

    @Override
    public String toString() {
        return name + " - " + description + " [" + (completed ? "Completado" : "Pendiente") + "]";
    }
}
