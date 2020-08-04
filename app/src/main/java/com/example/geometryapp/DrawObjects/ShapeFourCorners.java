package com.example.geometryapp.DrawObjects;

import com.example.geometryapp.Coordinate;
import com.example.geometryapp.Enum.ShapeType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class ShapeFourCorners {

    private Coordinate topLeft;
    private Coordinate topRight;
    private Coordinate bottomRight;
    private Coordinate bottomLeft;
    private ShapeType shapeType;

    //Any shape which can be created from 4 corners.
    public ShapeFourCorners(Coordinate topLeft, Coordinate topRight, Coordinate bottomRight, Coordinate bottomLeft, ShapeType shapeType) {
        this.topLeft = topLeft;
        this.topRight = topRight;
        this.bottomRight = bottomRight;
        this.bottomLeft = bottomLeft;
        this.shapeType = shapeType;
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

    public ShapeType getShapeType() {
        return shapeType;
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

    public double calculateArea(int xScale, int yScale){
        //This functions does not work if the lines intersect or one corner is pointing center of the shape
        double firstTriangle = (new Triangle(topLeft,topRight,bottomRight)).calculateArea(xScale,yScale);
        double secondTriangle = (new Triangle(topLeft,bottomRight,bottomLeft)).calculateArea(xScale,yScale);
        return firstTriangle + secondTriangle;
    }

    public double calculatePerimeter(int xScale, int yScale){
        double firstSecondLength = calculateDistanceTwoCoordinates(topLeft,topRight,xScale,yScale,false);
        double secondThirdLength = calculateDistanceTwoCoordinates(topRight,bottomRight,xScale,yScale,false);
        double thirdFourthLength = calculateDistanceTwoCoordinates(bottomRight,bottomLeft,xScale,yScale,false);
        double fourthFirstLength = calculateDistanceTwoCoordinates(bottomLeft,topLeft,xScale,yScale,false);
        return firstSecondLength + secondThirdLength + thirdFourthLength + fourthFirstLength;
    }

    public double calculateDistanceTwoCoordinates(Coordinate start, Coordinate end, int xScale, int yScale, boolean roundOneDecimal){
        //Rounds answer to one decimal place
        if(roundOneDecimal){
            return round(Math.sqrt((start.getX() - end.getX()) * (start.getX() - end.getX()) * xScale
                    + (start.getY() - end.getY()) * (start.getY() - end.getY()) * yScale), 1);
        }
        return Math.sqrt((start.getX() - end.getX()) * (start.getX() - end.getX()) * xScale
                + (start.getY() - end.getY()) * (start.getY() - end.getY()) * yScale);
    }


    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
