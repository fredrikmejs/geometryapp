package com.example.geometryapp.DrawObjects;

import com.example.geometryapp.Coordinate;

import java.util.ArrayList;

public class SelectedDot {

    private boolean purpleOn;
    private Coordinate coordinate;
    private ArrayList<Coordinate> previousCoordinates;

    //This is the teal/purple dot which changes place when user clicks the screen
    public SelectedDot(Coordinate coordinate) {
        this.coordinate = coordinate;
        this.previousCoordinates = new ArrayList<>();
        this.purpleOn = true;
        previousCoordinates.add(new Coordinate(5,5));
    }

    public void setPurpleColorOn(boolean purpleTeal) {
        this.purpleOn = purpleTeal;
    }

    public boolean isPurpleColorOn() {
        return purpleOn;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.previousCoordinates.add(coordinate);
        this.coordinate = coordinate;
    }

    public ArrayList<Coordinate> getPreviousCoordinates() {
        return previousCoordinates;
    }

    public void setPreviousCoordinates(ArrayList<Coordinate> previousCoordinates) {
        this.previousCoordinates = previousCoordinates;
    }


    public void goPreviousCoordinate() {
        if (previousCoordinates.size() > 1) {
            previousCoordinates.remove(previousCoordinates.size()-1);
            this.coordinate = previousCoordinates.get(previousCoordinates.size()-1);
        }else{
            previousCoordinates = new ArrayList<>();
            previousCoordinates.add(new Coordinate(5,5));
        }
    }
}
