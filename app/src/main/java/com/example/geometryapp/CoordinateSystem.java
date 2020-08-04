package com.example.geometryapp;

import android.util.Pair;

import java.util.HashMap;

public class CoordinateSystem {
    private HashMap<Coordinate, Pair<Integer, Integer>> coordinatePairHashMap;//key is the coordinate system coordinate
    private HashMap<Pair<Integer, Integer>, Coordinate> pairCoordinateHashMap;//key is the real canvas coordinate


    //Coordinate system has points from 0-10. Each of the points has real coordinate at the screen
    //This class saves the real coordinates and coordinate system coordinates so that things can be drawn into the correct spot at the screen
    public CoordinateSystem() {
        this.coordinatePairHashMap = new HashMap<>();
        this.pairCoordinateHashMap = new HashMap<>();
    }

    public HashMap<Coordinate, Pair<Integer, Integer>> getCoordinatePairHashMap() {
        return coordinatePairHashMap;
    }

    public void addCoordinate(Coordinate coordinate, int x, int y) {
        Pair pair = new Pair(x, y);
        //Screen coordinates are started from the top left corner. Coordinate system starts from the
        //bottom left corner. This line changes the coordinate system to start from the bottom left.
        coordinate.setY((coordinate.getY() - 10) * -1);
        coordinatePairHashMap.put(coordinate, pair);
        pairCoordinateHashMap.put(pair, coordinate);
    }

    public Pair<Integer, Integer> getCanvasRealCoordinate(Coordinate coordinate) {
        for (Coordinate i : coordinatePairHashMap.keySet()) {
            if (coordinate.getX() == i.getX() && coordinate.getY() == i.getY()) {
                return coordinatePairHashMap.get(i);
            }
        }
        return null;
    }

    public Coordinate getClosestCoordinateSystemCoordinate(int realX, int realY) {
        Coordinate closestCoordinate = null;
        int distance = 0;
        for (Pair<Integer, Integer> coordinate : pairCoordinateHashMap.keySet()) {
            if (closestCoordinate == null) {
                closestCoordinate = pairCoordinateHashMap.get(coordinate);
                distance = calculateDistance(realX, realY, coordinate.first, coordinate.second);
            } else {
                if (distance > calculateDistance(realX, realY, coordinate.first, coordinate.second)) {
                    closestCoordinate = pairCoordinateHashMap.get(coordinate);
                    distance = calculateDistance(realX, realY, coordinate.first, coordinate.second);
                }
            }
        }
        return closestCoordinate;
    }

    public int calculateDistance(int clickCoordinateX, int clickCoordinateY, int dotRealCoordinateX, int dotRealCoordinateY) {
        return (int) Math.sqrt((clickCoordinateX - dotRealCoordinateX) * (clickCoordinateX - dotRealCoordinateX)
                + (clickCoordinateY - dotRealCoordinateY) * (clickCoordinateY - dotRealCoordinateY));
    }
}
