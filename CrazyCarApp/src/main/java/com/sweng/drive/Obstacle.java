package com.sweng.drive;

import javafx.scene.shape.Rectangle;

public class Obstacle extends Rectangle {
    boolean isObstacle = true;

    public Obstacle(double x, double y, double width, double height) {
        super(x,y,width,height);
    }

    public boolean isObstacle() {
        return isObstacle;
    }
}
