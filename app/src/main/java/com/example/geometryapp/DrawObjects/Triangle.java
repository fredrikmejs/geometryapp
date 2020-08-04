package com.example.geometryapp.DrawObjects;

import com.example.geometryapp.Coordinate;
import com.example.geometryapp.GameState;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Triangle {
    private Coordinate firstPoint;
    private Coordinate secondPoint;
    private Coordinate thirdPoint;

    //Any triangle with three points.
    public Triangle(Coordinate firstPoint, Coordinate secondPoint, Coordinate thirdPoint) {
        this.firstPoint = firstPoint;
        this.secondPoint = secondPoint;
        this.thirdPoint = thirdPoint;
    }

    public Coordinate getFirstPoint() {
        return firstPoint;
    }

    public void setFirstPoint(Coordinate firstPoint) {
        this.firstPoint = firstPoint;
    }

    public Coordinate getSecondPoint() {
        return secondPoint;
    }

    public void setSecondPoint(Coordinate secondPoint) {
        this.secondPoint = secondPoint;
    }

    public Coordinate getThirdPoint() {
        return thirdPoint;
    }

    public void setThirdPoint(Coordinate thirdPoint) {
        this.thirdPoint = thirdPoint;
    }

    public double calculateArea(int xScale, int yScale) {
        return  Math.abs((double) (firstPoint.getX() * (secondPoint.getY() * yScale - thirdPoint.getY() * yScale) * xScale
                + secondPoint.getX() * (thirdPoint.getY() * yScale - firstPoint.getY() * yScale) * xScale
                + thirdPoint.getX() * (firstPoint.getY() * yScale - secondPoint.getY() * yScale) * xScale) / 2);
    }

    public double calculateDistanceTwoCoordinates(Coordinate start, Coordinate end, int xScale, int yScale, boolean roundOneDecimal){
        if(roundOneDecimal){
            return round(Math.sqrt((start.getX() - end.getX()) * (start.getX() - end.getX()) * xScale
                    + (start.getY() - end.getY()) * (start.getY() - end.getY()) * yScale), 1);
        }
        return Math.sqrt((start.getX() - end.getX()) * (start.getX() - end.getX()) * xScale
                + (start.getY() - end.getY()) * (start.getY() - end.getY()) * yScale);
    }

    public double calculatePerimeter(int xScale, int yScale) {
        double firstSecondLength = Math.sqrt((firstPoint.getX() - secondPoint.getX()) * (firstPoint.getX() - secondPoint.getX()) * xScale
                + (firstPoint.getY() - secondPoint.getY()) * (firstPoint.getY() - secondPoint.getY()) * yScale);
        double secondThirdLength = Math.sqrt((secondPoint.getX() - thirdPoint.getX()) * (secondPoint.getX() - thirdPoint.getX()) * xScale
                + (secondPoint.getY() - thirdPoint.getY()) * (secondPoint.getY() - thirdPoint.getY()) * yScale);
        double thirdFirstLength = Math.sqrt((thirdPoint.getX() - firstPoint.getX()) * (thirdPoint.getX() - firstPoint.getX()) * xScale
                + (thirdPoint.getY() - firstPoint.getY()) * (thirdPoint.getY() - firstPoint.getY()) * yScale);
        return firstSecondLength + secondThirdLength + thirdFirstLength;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
