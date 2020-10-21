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

/**
 * This class creates the levels for point from coordinate category
 */
public class PointFromCoordinates implements Level {

    private static Random random = new Random();
    private Coordinate origin;
    private int xScale;
    private int yScale;
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
        } else if (levelNum == 7) {
            level7();
        } else {
            throw new IllegalArgumentException("Level does not exist! Level index was " + levelNum);
        }
    }

    /**
     * Creates level 1
     */
    public void level1() {
        origin = new Coordinate(0, 0);
        xScale = 1;
        yScale = 1;
        targetPoint = new Coordinate(randomPoint(-origin.getX(), (10 - origin.getX())) * xScale
                , randomPoint(-origin.getY(), (10 - origin.getY())) * yScale);
    }

    /**
     * Creates level 2
     */
    public void level2() {
        origin = new Coordinate(0, 0);
        xScale = 2;
        yScale = 2;
        targetPoint = new Coordinate(randomPoint(-origin.getX(), (10 - origin.getX())) * xScale
                , randomPoint(-origin.getY(), (10 - origin.getY())) * yScale);
    }

    /**
     * Creates level 3
     */
    public void level3() {
        origin = new Coordinate(5, 5);
        xScale = 1;
        yScale = 1;
        targetPoint = new Coordinate(randomPoint(-origin.getX(), (10 - origin.getX())) * xScale
                , randomPoint(-origin.getY() * yScale, (10 - origin.getY())) * yScale);
    }

    /**
     * Creates level 4
     */
    public void level4() {
        origin = new Coordinate(5, 5);
        xScale = 10;
        yScale = 10;
        targetPoint = new Coordinate(randomPoint(-origin.getX(), (10 - origin.getX())) * xScale
                , randomPoint(-origin.getY(), (10 - origin.getY())) * yScale);
    }

    /**
     * Creates level 5
     */
    public void level5() {
        origin = new Coordinate(randomPoint(1,9), randomPoint(1,9));
        xScale = 1;
        yScale = 1;
        targetPoint = new Coordinate(randomPoint(-origin.getX(), (10 - origin.getX())) * xScale
                , randomPoint(-origin.getY(), (10 - origin.getY())) * yScale);
    }

    /**
     * Creates level 6
     */
    public void level6() {
        int[] easyNumbers = {2,3,4,5,10};
        origin = new Coordinate(randomPoint(1, 9), randomPoint(1, 9));
        xScale = easyNumbers[(int) (Math.random()*easyNumbers.length)];
        yScale = easyNumbers[(int) (Math.random()*easyNumbers.length)];
        targetPoint = new Coordinate(randomPoint(-origin.getX(), (10 - origin.getX())) * xScale
                , randomPoint(-origin.getY(), (10 - origin.getY())) * yScale);
    }

    /**
     * Creates level 7
     */
    public void level7() {
        int[] advancedNumbers = {6,7,8,9};
        origin = new Coordinate(randomPoint(1, 9), randomPoint(1, 9));
        xScale = advancedNumbers[(int) (Math.random()*advancedNumbers.length)];
        yScale = advancedNumbers[(int) (Math.random()*advancedNumbers.length)];
        targetPoint = new Coordinate(randomPoint(-origin.getX(), (10 - origin.getX())) * xScale
                , randomPoint(-origin.getY(), (10 - origin.getY())) * yScale);
    }

    /**
     * Createa a random point
     * @param min lowest possible value
     * @param max highest possible value
     * @return the random point between min and max
     */
    public int randomPoint(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    @Override
    public GameState getDefaultLevelState(Context context) {
        GameStateBuilder gameStateBuilder = new GameStateBuilder();
        gameStateBuilder.setOrigin(origin)
                .setSelectedDot(new SelectedDot(new Coordinate(0,0)))
                .setXScale(xScale)
                .setYScale(yScale)
                .setCategory(category)
                .setSelectedDot(new SelectedDot(new Coordinate(5,5)))
                .setQuestion(context.getResources()
                .getString(R.string.PointFromCoordinatesQuestion))
                .setTargetPoint(targetPoint);
        return gameStateBuilder.build();
    }
}
