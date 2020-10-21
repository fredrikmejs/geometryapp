package com.example.geometryapp.DrawObjects;

import com.example.geometryapp.Coordinate;
import com.example.geometryapp.Enum.Categories;
import com.example.geometryapp.GameState;
import com.example.geometryapp.Singleton;

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


    /**
     * Calculates the area of the triangle
     * @param xScale
     * @param yScale
     * @param lvl the level is used to because in level 8 there are two different cases.
     * @param categories just to check category.
     * @return
     */
    public double calculateArea(int xScale, int yScale, int lvl, Categories categories ) {

        double area;
        if (lvl == 8 && categories == Categories.FINDAREAFROMFIGURE){
            Singleton singleton = Singleton.getInstance();
                if (singleton.getRandomNum() == 1){
                    double base = calculateDistanceTwoCoordinates(secondPoint,thirdPoint,xScale,yScale,true);
                    double height = calculateDistanceTwoCoordinates(firstPoint,secondPoint,xScale,yScale,true);
                    area = (base*height)/2;
                }else {
                    double height = Math.abs(firstPoint.getY()-thirdPoint.getY());
                    double base = calculateDistanceTwoCoordinates(firstPoint,secondPoint,xScale,yScale,true);
                    area = (base*height)/2;
                }
        } else {
            area = Math.abs((double) (firstPoint.getX() * (secondPoint.getY() * yScale - thirdPoint.getY() * yScale) * xScale
                    + secondPoint.getX() * (thirdPoint.getY() * yScale - firstPoint.getY() * yScale) * xScale
                    + thirdPoint.getX() * (firstPoint.getY() * yScale - secondPoint.getY() * yScale) * xScale) / 2);
        }

        return  area;
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


    public double getHeight(int xScale, int yScale, int lvl, Categories categories){
        double height;
        double distance;
        if (calculateDistanceTwoCoordinates(firstPoint,secondPoint,xScale,yScale,true) == calculateDistanceTwoCoordinates(firstPoint,thirdPoint,xScale,yScale,true)){
            distance = calculateDistanceTwoCoordinates(secondPoint,thirdPoint,xScale,yScale,true);
        } else if (calculateDistanceTwoCoordinates(firstPoint,secondPoint,xScale,yScale,true) == calculateDistanceTwoCoordinates(secondPoint,thirdPoint,xScale,yScale,true)){
            distance = calculateDistanceTwoCoordinates(firstPoint,thirdPoint,xScale,yScale,true);
        } else distance = calculateDistanceTwoCoordinates(firstPoint,secondPoint,xScale,yScale,true);

        height = (2*calculateArea(xScale, yScale, lvl, categories ))/distance;

        return height;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
