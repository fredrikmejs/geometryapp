package com.example.geometryapp.DrawObjects;

import com.example.geometryapp.Coordinate;

public class Line {
    private Coordinate startCoordinate;
    private Coordinate endCoordinate;

    public Line(Coordinate startCoordinate, Coordinate endCoordinate) {
        if (startCoordinate.getX() == endCoordinate.getX() && startCoordinate.getY() == endCoordinate.getY()) {
            throw new IllegalArgumentException("Lines start and end coordinate should be different!");
        }
        this.startCoordinate = startCoordinate;
        this.endCoordinate = endCoordinate;
    }

    public Coordinate getStartCoordinate() {
        return startCoordinate;
    }

    public void setStartCoordinate(Coordinate startCoordinate) {
        this.startCoordinate = startCoordinate;
    }

    public Coordinate getEndCoordinate() {
        return endCoordinate;
    }

    public void setEndCoordinate(Coordinate endCoordinate) {
        this.endCoordinate = endCoordinate;
    }
}
