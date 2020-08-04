package com.example.geometryapp.Levels.Coordinates;

import android.content.Context;

import com.example.geometryapp.Coordinate;
import com.example.geometryapp.DrawObjects.SelectedDot;
import com.example.geometryapp.Enum.Categories;
import com.example.geometryapp.GameStateBuilder;
import com.example.geometryapp.Interface.Level;
import com.example.geometryapp.GameState;
import com.example.geometryapp.R;

import java.util.Random;

import static com.example.geometryapp.Enum.Categories.POINTFROMCOORDINATES;

public class PointFromCoordinates implements Level {

    private static Random random = new Random();
    private Coordinate origin;
    private int Xscale;
    private int Yscale;
    private Coordinate targetPoint;
    private Categories category = POINTFROMCOORDINATES;

    //Creates the correct gameState
    public PointFromCoordinates(int levelNum) {
        if (levelNum == 0) {
            levelNum = randomPoint(1, 6);
        }
        if (levelNum == 1) {
            level1();
        } else if (levelNum == 2) {
            level2();
        } else if (levelNum == 3) {
            level3();
        } else if (levelNum == 4) {
            level4();
        } else if (levelNum == 5) {
            level5();
        } else if (levelNum == 6) {
            level6();
        } else {
            throw new IllegalArgumentException("Level does not exist! Level index was " + levelNum);
        }
    }

    public void level1() {
        origin = new Coordinate(0, 0);
        Xscale = 1;
        Yscale = 1;
        targetPoint = new Coordinate(randomPoint(-origin.getX(), (10 - origin.getX())) * Xscale
                , randomPoint(-origin.getY(), (10 - origin.getY())) * Yscale);
    }

    public void level2() {
        origin = new Coordinate(0, 0);
        Xscale = 2;
        Yscale = 2;
        targetPoint = new Coordinate(randomPoint(-origin.getX(), (10 - origin.getX())) * Xscale
                , randomPoint(-origin.getY(), (10 - origin.getY())) * Yscale);
    }

    public void level3() {
        origin = new Coordinate(5, 5);
        Xscale = 1;
        Yscale = 1;
        targetPoint = new Coordinate(randomPoint(-origin.getX(), (10 - origin.getX())) * Xscale
                , randomPoint(-origin.getY() * Yscale, (10 - origin.getY())) * Yscale);
    }

    public void level4() {
        origin = new Coordinate(5, 5);
        Xscale = 10;
        Yscale = 10;
        targetPoint = new Coordinate(randomPoint(-origin.getX(), (10 - origin.getX())) * Xscale
                , randomPoint(-origin.getY(), (10 - origin.getY())) * Yscale);
    }

    public void level5() {
        origin = new Coordinate(5, 5);
        Xscale = 10;
        Yscale = 10;
        targetPoint = new Coordinate(randomPoint(-origin.getX(), (10 - origin.getX())) * Xscale
                , randomPoint(-origin.getY(), (10 - origin.getY())) * Yscale);
    }

    public void level6() {
        origin = new Coordinate(randomPoint(1, 9), randomPoint(1, 9));
        Xscale = randomPoint(1, 10);
        Yscale = randomPoint(1, 10);
        targetPoint = new Coordinate(randomPoint(-origin.getX(), (10 - origin.getX())) * Xscale
                , randomPoint(-origin.getY(), (10 - origin.getY())) * Yscale);
    }

    public int randomPoint(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    @Override
    public GameState getDefaultLevelState(Context context) {
        GameStateBuilder gameStateBuilder = new GameStateBuilder();
        gameStateBuilder.setOrigin(origin)
                .setSelectedDot(new SelectedDot(new Coordinate(0,0)))
                .setXScale(Xscale)
                .setYScale(Yscale)
                .setCategory(category)
                .setSelectedDot(new SelectedDot(new Coordinate(5,5)))
                .setQuestion(context.getResources()
                .getString(R.string.PointFromCoordinatesQuestion))
                .setTargetPoint(targetPoint);
        return gameStateBuilder.build();
    }
}
