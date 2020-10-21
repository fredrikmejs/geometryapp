package com.example.geometryapp.DrawObjects;

import com.example.geometryapp.Coordinate;
import com.example.geometryapp.GameState;

/**
 * Used to give information about the Rectangle
 */
public class Rectangle {

    private Coordinate topLeft;
    private Coordinate topRight;
    private Coordinate bottomRight;
    private Coordinate bottomLeft;

    public Rectangle(Coordinate topLeft, Coordinate topRight, Coordinate bottomRight, Coordinate bottomLeft) {
        this.topLeft = topLeft;
        this.topRight = topRight;
        this.bottomRight = bottomRight;
        this.bottomLeft = bottomLeft;
    }

    public void setTopLeft(Coordinate topLeft) {
        this.topLeft = topLeft;
    }

    public void setTopRight(Coordinate topRight) {
        this.topRight = topRight;
    }

    public void setBottomRight(Coordinate bottomRight) {
        this.bottomRight = bottomRight;
    }

    public void setBottomLeft(Coordinate bottomLeft) {
        this.bottomLeft = bottomLeft;
    }

    public Coordinate getTopLeft() {
        return topLeft;
    }

    public Coordinate getTopRight() {
        return topRight;
    }

    public Coordinate getBottomRight() {
        return bottomRight;
    }

    public Coordinate getBottomLeft() {
        return bottomLeft;
    }

    public int getHeight(int yScale){
        return Math.abs(topLeft.getY() - bottomLeft.getY())*yScale;
    }

    public int getWidth(int xScale){
        return Math.abs(bottomRight.getX() - bottomLeft.getX())*xScale;
    }

    public double calculateArea(int xScale, int yScale){
        double height = getHeight(yScale);
        double width = getWidth(xScale);
        return height*width;
    }

    public double calculatePerimeter(int xScale, int yScale){
        double height = getHeight(yScale);
        double width = getWidth(xScale);
        return 2*height + 2*width;
    }
}
