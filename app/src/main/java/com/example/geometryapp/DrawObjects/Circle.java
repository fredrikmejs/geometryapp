package com.example.geometryapp.DrawObjects;

import com.example.geometryapp.Coordinate;
import com.example.geometryapp.GameState;

public class Circle {

    private Coordinate center;
    private Coordinate drawCenter = new Coordinate(1,1);
    private int radius;

    //Circle object. Contains basic information for drawing a circle.
    public Circle(Coordinate center, int radius) {
        this.center = center;
        this.radius = radius;
    }

    public Coordinate getCenter() {
        return center;
    }

    public Coordinate getDrawCircle() {
        int x = center.getX() + radius;
        int y = center.getY();
        drawCenter.setX(x);
        drawCenter.setY(y);
        return drawCenter;}

    public void setCenter(Coordinate center) {
        this.center = center;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public double calculateArea(int scale){
        // Y and X scale are always same
        return  scale*scale*radius*radius*3.14159;
    }

    public double calculatePerimeter(int scale){
        // Y and X scale are always same
        return scale*2*radius*3.141;
    }
}
