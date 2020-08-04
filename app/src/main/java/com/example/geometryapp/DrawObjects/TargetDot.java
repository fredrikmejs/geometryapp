package com.example.geometryapp.DrawObjects;

import com.example.geometryapp.Coordinate;

public class TargetDot {

    //Dot that is drawn to the screen. For example in category 2. Coordinates from point user sees a dot and tries to find its coordinate
    private Coordinate coordinate;

    public TargetDot(Coordinate coordinate){
        this.coordinate = coordinate;
    }
    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
