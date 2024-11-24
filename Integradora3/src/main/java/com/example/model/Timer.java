package com.example.model;

public class Timer {
    private long startTime;
    private long endTime;
    // The `public Timer(){ }` is a constructor for the `Timer` class. Constructors are special methods
    // that are used to initialize objects of a class. In this case, the constructor is empty, meaning
    // it does not contain any code. It is used to create a new instance of the `Timer` class without
    // any specific initialization logic.
    public Timer(){

    }

    /**
     * The start() function sets the startTime variable to the current system time in milliseconds.
     */
    public void start(){
        startTime = System.currentTimeMillis();
    }
    /**
     * The function calculates the time elapsed between the start and end of a process and returns the
     * duration in milliseconds.
     * 
     * @return The method is returning the difference between the endTime and startTime, which is the
     * total time elapsed in milliseconds.
     */
    public long stop(){
        endTime = System.currentTimeMillis();
        return endTime - startTime;
    }
}
