package com.example.model;

public class Position {
    private double x;
    private double y;
    // The `public Position(double x, double y)` is a constructor method for the `Position` class. It
    // takes in two parameters, `x` and `y`, and assigns them to the corresponding instance variables
    // `this.x` and `this.y`. This constructor is used to create a new `Position` object with the
    // specified `x` and `y` coordinates.
    public Position(double x, double y) {

        this.x = x;
        this.y = y;
    }
    /**
     * The getX() function returns the value of the variable x.
     * 
     * @return The method is returning the value of the variable "x" as a double.
     */
    public double getX() {
        return x;
    }

    /**
     * The function sets the value of the variable "x" to the given input.
     * 
     * @param x The parameter "x" is a double data type.
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * The function returns the value of the variable "y".
     * 
     * @return The method is returning the value of the variable "y" as a double.
     */
    public double getY() {
        return y;
    }

    /**
     * The function sets the value of the variable "y" to the given input.
     * 
     * @param y The parameter "y" is a double data type.
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * The function adds a given step value to the variable y.
     * 
     * @param step step is an integer value that represents the amount by which the variable y should
     * be incremented.
     */
    public void addY(int step){
        y+= step;
    }
    /**
     * The addX function adds a given step value to the variable x.
     * 
     * @param step step is an integer value that represents the amount by which the variable x will be
     * incremented.
     */
    public void addX(int step){
        x+= step;
    }
    /**
     * The function calculates the distance between two positions using the Pythagorean theorem.
     * 
     * @param other The "other" parameter is an object of the Position class. It represents the
     * position of another point in a two-dimensional coordinate system.
     * @return The method is returning the distance between the current position (this) and the
     * position passed as a parameter (other).
     */
    public double distance(Position other1){
        Position other = new Position(other1.getX()-25,other1.getY()-50);
        return Math.sqrt(
                Math.pow(this.x - other.getX(), 2) +
                        Math.pow(this.y - other.getY(), 2)
        );
    }

    /**
     * The normalize function calculates the magnitude of a vector and divides its components by the
     * magnitude to normalize the vector.
     */
    public void normalize(){
        double normal = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        if(normal != 0){
            x /= normal;
            y /= normal;
        }
    }

    /**
     * The function sets the speed of an object by multiplying its x and y coordinates by the given
     * speed value.
     * 
     * @param speed The "speed" parameter is an integer value that represents the desired speed at
     * which the x and y values should be multiplied.
     */
    public void setSpeed(int speed){
        x *= speed;
        y *= speed;
    }
}
