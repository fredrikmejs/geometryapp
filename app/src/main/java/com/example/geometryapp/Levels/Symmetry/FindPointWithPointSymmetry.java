package com.example.geometryapp.Levels.Symmetry;

import android.content.Context;

import com.example.geometryapp.Coordinate;
import com.example.geometryapp.DrawObjects.SelectedDot;
import com.example.geometryapp.DrawObjects.TargetDot;
import com.example.geometryapp.Enum.Categories;
import com.example.geometryapp.GameState;
import com.example.geometryapp.GameStateBuilder;
import com.example.geometryapp.Interface.Level;
import com.example.geometryapp.R;

import java.util.Random;

import static com.example.geometryapp.Enum.Categories.FINDPOINTWITHPOINTSYMMETRY;

/**
 * This class creates levels for find points with point symmetry category
 */
public class FindPointWithPointSymmetry implements Level {

    private static Random random = new Random();
    private Coordinate origin;
    private int xScale;
    private int yScale;
    private Categories category = FINDPOINTWITHPOINTSYMMETRY;
    private TargetDot targetDot;
    private SelectedDot selectedDot;
    private Coordinate symmetryPoint;


    //Creates the correct gameState
    public FindPointWithPointSymmetry(int levelNum) {
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
        symmetryPoint = new Coordinate(5, 5);
        Coordinate targetCoordinate = new Coordinate(randomPoint(0, 10), randomPoint(0, 10));
        while (isCorrectAnswerOnGrid(targetCoordinate, symmetryPoint)) {
            targetCoordinate = new Coordinate(randomPoint(0, 10), randomPoint(0, 10));
        }
        targetDot = new TargetDot(targetCoordinate);
    }

    /**
     * Creates level 2
     */
    public void level2() {
        origin = new Coordinate(0, 0);
        xScale = 1;
        yScale = 1;
        selectedDot = new SelectedDot(new Coordinate(5, 5));
        symmetryPoint = new Coordinate(randomPoint(3, 7), randomPoint(3, 7));
        Coordinate targetCoordinate = new Coordinate(randomPoint(0, 10), randomPoint(0, 10));
        while (isCorrectAnswerOnGrid(targetCoordinate, symmetryPoint)) {
            targetCoordinate = new Coordinate(randomPoint(0, 10), randomPoint(0, 10));
        }
        targetDot = new TargetDot(targetCoordinate);
    }

    /**
     * Createa a random point
     * @param min lowest possible value
     * @param max highest possible value
     * @return the random point between min and max
     */
    private int randomPoint(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    /**
     * Checks if answer is on grid
     * @param targetDot the answer
     * @param symmetryPoint
     * @return boolean
     */
    private boolean isCorrectAnswerOnGrid(Coordinate targetDot, Coordinate symmetryPoint) {
        return (symmetryPoint.getX() + Math.abs(symmetryPoint.getX() - targetDot.getX()) > 10
                || symmetryPoint.getX() - Math.abs((symmetryPoint.getX() - targetDot.getX())) < 0)
                || (symmetryPoint.getY() + Math.abs((symmetryPoint.getY() - targetDot.getY())) > 10
                || symmetryPoint.getY() - Math.abs((symmetryPoint.getY() - targetDot.getY())) < 0)
                || (symmetryPoint.getX() == targetDot.getX() && targetDot.getY() == symmetryPoint.getY());
    }

    @Override
    public GameState getDefaultLevelState(Context context) {
        GameStateBuilder gameStateBuilder = new GameStateBuilder();
        gameStateBuilder.setOrigin(origin)
                .setXScale(xScale)
                .setYScale(yScale)
                .setCategory(category)
                .setSymmetryPoint(symmetryPoint)
                .setQuestion(context.getResources().getString(R.string.FindPointWithLinePoint))
                .setSelectedDot(selectedDot)
                .setTargetDot(targetDot);
        return gameStateBuilder.build();
    }
}
