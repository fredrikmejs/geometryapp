package com.example.geometryapp.Levels.Coordinates;

import android.content.Context;

import com.example.geometryapp.Coordinate;
import com.example.geometryapp.DrawObjects.SelectedDot;
import com.example.geometryapp.DrawObjects.TargetDot;
import com.example.geometryapp.Enum.Categories;
import com.example.geometryapp.GameState;
import com.example.geometryapp.Interface.Level;
import com.example.geometryapp.R;
import com.example.geometryapp.GameStateBuilder;

import java.util.Random;

import static com.example.geometryapp.Enum.Categories.COORDINATESFROMPOINT;

/**
 * This class creates the levels for coordinate from point
 */
public class CoordinatesFromPoint implements Level {

    private static Random random = new Random();
    private Coordinate origin;
    private int xScale;
    private int yScale;
    private Categories category = COORDINATESFROMPOINT;
    private TargetDot targetDot;

    //Creates the correct gameState
    public CoordinatesFromPoint(int levelNum) {
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

    /**
     * Creates level 1
     */
    public void level1() {
        origin = new Coordinate(0, 0);
        xScale = 1;
        yScale = 1;
        targetDot = new TargetDot(new Coordinate(randomPoint(0, 10), randomPoint(0, 10)));
    }

    /**
     * Creates level 2
     */
    public void level2() {
        origin = new Coordinate(0, 0);
        xScale = 2;
        yScale = 2;
        targetDot = new TargetDot(new Coordinate(randomPoint(0, 10), randomPoint(0, 10)));
    }

    /**
     * Creates level 3
     */
    public void level3() {
        origin = new Coordinate(5, 5);
        xScale = 1;
        yScale = 1;
        targetDot = new TargetDot(new Coordinate(randomPoint(0, 10), randomPoint(0, 10)));
    }

    /**
     * Creates level 4
     */
    public void level4() {
        origin = new Coordinate(5, 5);
        xScale = 10;
        yScale = 10;
        targetDot = new TargetDot(new Coordinate(randomPoint(0, 10), randomPoint(0, 10)));
    }

    /**
     * Creates level 5
     */
    public void level5() {
        origin = new Coordinate(randomPoint(1,9), randomPoint(1,9));
        xScale = 1;
        yScale = 1;
        targetDot = new TargetDot(new Coordinate(randomPoint(0, 10), randomPoint(0, 10)));
    }

    /**
     * Creates level 6
     */
    public void level6() {
        origin = new Coordinate(randomPoint(1, 9), randomPoint(1, 9));
        xScale = randomPoint(1, 10);
        yScale = randomPoint(1, 10);
        targetDot = new TargetDot(new Coordinate(randomPoint(0, 10), randomPoint(0, 10)));
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
                .setTargetDot(targetDot)
                .setCategory(category)
                .setQuestion(context.getResources()
                .getString(R.string.CoordinatesFromPointQuestion));
        return gameStateBuilder.build();
    }
}
