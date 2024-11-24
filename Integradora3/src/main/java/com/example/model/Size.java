package com.example.model;

public class Size {
    private double width;
    private double height;

    // The `public Size(double width, double height)` is a constructor method for the `Size` class. It
    // is used to create a new instance of the `Size` class and initialize its `width` and `height`
    // properties with the values passed as arguments.
    public Size(double width, double height) {
        this.width = width;
        this.height = height;
    }

    /**
     * The getWidth() function returns the width of an object.
     * 
     * @return The method is returning the value of the variable "width" as a double.
     */
    public double getWidth() {
        return width;
    }

    /**
     * The function sets the value of the width variable.
     * 
     * @param width The "width" parameter is a double value that represents the width of an object or
     * element.
     */
    public void setWidth(double width) {
        this.width = width;
    }

    /**
     * The getHeight() function returns the height value.
     * 
     * @return The method is returning the value of the variable "height" as a double.
     */
    public double getHeight() {
        return height;
    }

    /**
     * The function sets the height of an object.
     * 
     * @param height The height parameter is a double data type, which means it can store decimal
     * values. It is used to set the height of an object or variable.
     */
    public void setHeight(double height) {
        this.height = height;
    }
}
