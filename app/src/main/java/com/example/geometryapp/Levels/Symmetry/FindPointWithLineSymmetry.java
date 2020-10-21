package com.example.geometryapp.Levels.Symmetry;

import android.content.Context;

import com.example.geometryapp.Coordinate;
import com.example.geometryapp.DrawObjects.SelectedDot;
import com.example.geometryapp.DrawObjects.SymmetryLine;
import com.example.geometryapp.DrawObjects.TargetDot;
import com.example.geometryapp.Enum.Categories;
import com.example.geometryapp.GameState;
import com.example.geometryapp.GameStateBuilder;
import com.example.geometryapp.Interface.Level;
import com.example.geometryapp.R;
import com.example.geometryapp.Singleton;

import java.util.Random;

import static com.example.geometryapp.Enum.Categories.FINDPOINTWITHLINESYMMETRY;

/**
 * Creates levels for find point with line symmetry
 */
public class FindPointWithLineSymmetry implements Level {

    private static Random random = new Random();
    private Coordinate origin;
    private int xScale;
    private int yScale;
    private Categories category = FINDPOINTWITHLINESYMMETRY;
    private TargetDot targetDot;
    private SelectedDot selectedDot;
    private SymmetryLine symmetryLine;
    private int randomNum;


    //Creates the correct gameState
    public FindPointWithLineSymmetry(int levelNum) {
        Singleton singleton = Singleton.getInstance();
        randomNum = randomPoint(0,1);
        singleton.setRandomNum(randomNum);
        switch (levelNum){
            case 1:
                level1();
                break;
            case 2:
                level2();
                break;
            default:
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
        selectedDot = new SelectedDot(new Coordinate(5, 5));

        if (randomNum == 0) {
            symmetryLine = new SymmetryLine(new Coordinate(5, 0), new Coordinate(5, 10));
        } else {
            symmetryLine = new SymmetryLine(new Coordinate(0, 5), new Coordinate(10, 5));
        }
        targetDot = new TargetDot(new Coordinate(randomPoint(0, 10), randomPoint(0, 10)));
        while (targetDotIsOnSymmetryLine(targetDot.getCoordinate())) {
            targetDot = new TargetDot(new Coordinate(randomPoint(0, 10), randomPoint(0, 10)));
        }
    }

    /**
     * Creates level 2
     */
    public void level2() {
        origin = new Coordinate(0, 0);
        xScale = 1;
        yScale = 1;
        selectedDot = new SelectedDot(new Coordinate(5, 5));

        if (randomNum == 0) {
            symmetryLine = new SymmetryLine(new Coordinate(0, 0), new Coordinate(10, 10));
        } else {
            symmetryLine = new SymmetryLine(new Coordinate(0, 10), new Coordinate(10, 0));
        }
        targetDot = new TargetDot(new Coordinate(randomPoint(0, 10), randomPoint(0, 10)));
        while (targetDotIsOnSymmetryLine(targetDot.getCoordinate())) {
            targetDot = new TargetDot(new Coordinate(randomPoint(0, 10), randomPoint(0, 10)));
        }
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

    /**
     * Checks if target is on the symmestry line
     * @param coordinate coordinate of the target
     * @return boolean
     */
    public boolean targetDotIsOnSymmetryLine(Coordinate coordinate) {
        //Target point cannot locate on diagonal line or x = 5 or y = 5
        if (symmetryLine.getStartCoordinate().getX() == 5 && coordinate.getX() == 5) {
            return true;
        }
        if (symmetryLine.getStartCoordinate().getY() == 5 && coordinate.getY() == 5) {
            return true;
        }
        if(targetDot.getCoordinate().getY()+targetDot.getCoordinate().getX()==10){
            return true;
        }
        return targetDot.getCoordinate().getY() == targetDot.getCoordinate().getX();
    }

    @Override
    public GameState getDefaultLevelState(Context context) {
        GameStateBuilder gameStateBuilder = new GameStateBuilder();
        gameStateBuilder.setOrigin(origin)
                .setXScale(xScale)
                .setYScale(yScale)
                .setCategory(category)
                .setSymmetryLine(symmetryLine)
                .setQuestion(context.getResources().getString(R.string.FindPointWithLineSymmetry))
                .setSelectedDot(new SelectedDot(new Coordinate(0,0)))
                .setSelectedDot(selectedDot)
                .setTargetDot(targetDot);
        return gameStateBuilder.build();
    }
}
