package com.example.model;

public class AchievementTree {
    private class Node {
        Achievement achievement;
        Node left, right;

        public Node(Achievement achievement) {
            this.achievement = achievement;
        }
    }

    private Node root;

    // Método para agregar un logro al árbol
    public void add(Achievement achievement) {
        root = addRecursive(root, achievement);
    }

    private Node addRecursive(Node current, Achievement achievement) {
        if (current == null) {
            return new Node(achievement);
        }

        if (achievement.compareTo(current.achievement) < 0) {
            current.left = addRecursive(current.left, achievement);
        } else if (achievement.compareTo(current.achievement) > 0) {
            current.right = addRecursive(current.right, achievement);
        }

        return current; // Sin duplicados
    }

    // Buscar un logro en el árbol
    public Achievement find(String name) {
        return findRecursive(root, name);
    }

    private Achievement findRecursive(Node current, String name) {
        if (current == null) {
            return null;
        }

        if (name.equals(current.achievement.getName())) {
            return current.achievement;
        }

        return name.compareTo(current.achievement.getName()) < 0
                ? findRecursive(current.left, name)
                : findRecursive(current.right, name);
    }

    // Imprimir todos los logros (en orden)
    public void printAchievements() {
        printAchievementsRecursive(root);
    }

    private void printAchievementsRecursive(Node node) {
        if (node != null) {
            printAchievementsRecursive(node.left);
            System.out.println(node.achievement);
            printAchievementsRecursive(node.right);
        }
    }
}
