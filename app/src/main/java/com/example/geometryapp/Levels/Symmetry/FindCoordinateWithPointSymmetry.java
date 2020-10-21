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

import static com.example.geometryapp.Enum.Categories.FINDCOORDINATEWITHPOINTSYMMETRY;

/**
 * This class creates the levels for find coordinates with point symmetry category
 */
public class FindCoordinateWithPointSymmetry implements Level {

    private static Random random = new Random();
    private Coordinate origin;
    private int xScale;
    private int yScale;
    private Categories category = FINDCOORDINATEWITHPOINTSYMMETRY;
    private SelectedDot selectedDot = new SelectedDot(new Coordinate(0, 0));
    private Coordinate symmetryPoint;
    private TargetDot targetDot;

    //Creates the correct gameState
    public FindCoordinateWithPointSymmetry(int levelNum) {
        switch (levelNum){
            case 1:
                level1();
                break;
            case 2:
                level2();
                break;
            case 3:
                level3();
                break;
            case 4:
                level4();
                break;
            case 5:
                level5();
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
        symmetryPoint = new Coordinate(5, 5);
        selectedDot = new SelectedDot(new Coordinate(5,5));
        Coordinate targetCoordinate = new Coordinate(randomPoint(0, 10), randomPoint(0, 10));
        while (isCorrectAnswerNotOnGrid(targetCoordinate, symmetryPoint)) {
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
        symmetryPoint = new Coordinate(randomPoint(3, 7), randomPoint(3, 7));
        selectedDot = new SelectedDot(new Coordinate(5,5));
        Coordinate targetCoordinate = new Coordinate(randomPoint(0, 10), randomPoint(0, 10));
        while (isCorrectAnswerNotOnGrid(targetCoordinate, symmetryPoint)) {
            targetCoordinate = new Coordinate(randomPoint(0, 10), randomPoint(0, 10));
        }
        targetDot = new TargetDot(targetCoordinate);
    }

    /**
     * Creates level 3
     */
    public void level3() {
        origin = new Coordinate(randomPoint(1, 9), randomPoint(1, 9));
        xScale = 1;
        yScale = 1;
        symmetryPoint = new Coordinate(randomPoint(3, 7), randomPoint(3, 7));
        selectedDot = new SelectedDot(new Coordinate(5,5));
        Coordinate targetCoordinate = new Coordinate(randomPoint(0, 10), randomPoint(0, 10));
        while (isCorrectAnswerNotOnGrid(targetCoordinate, symmetryPoint)) {
            targetCoordinate = new Coordinate(randomPoint(0, 10), randomPoint(0, 10));
        }
        targetDot = new TargetDot(targetCoordinate);
    }

    /**
     * Creates level 4
     */
    public void level4() {
        int[] easyNumbers = {2,3,4,5,10};
        origin = new Coordinate(randomPoint(1, 9), randomPoint(1, 9));
        xScale = easyNumbers[(int) (Math.random()*easyNumbers.length)];
        yScale = easyNumbers[(int) (Math.random()*easyNumbers.length)];
        symmetryPoint = new Coordinate(randomPoint(3, 7), randomPoint(3, 7));
        selectedDot = new SelectedDot(new Coordinate(5,5));
        Coordinate targetCoordinate = new Coordinate(randomPoint(0, 10), randomPoint(0, 10));
        while (isCorrectAnswerNotOnGrid(targetCoordinate, symmetryPoint)) {
            targetCoordinate = new Coordinate(randomPoint(0, 10), randomPoint(0, 10));
        }
        targetDot = new TargetDot(targetCoordinate);
    }

    /**
     * Creates level 5
     */
    public void level5() {
        int[] advancedNumbers = {6,7,8,9};
        origin = new Coordinate(randomPoint(1, 9), randomPoint(1, 9));
        xScale = advancedNumbers[(int) (Math.random()*advancedNumbers.length)];
        yScale = advancedNumbers[(int) (Math.random()*advancedNumbers.length)];
        symmetryPoint = new Coordinate(randomPoint(3, 7), randomPoint(3, 7));
        selectedDot = new SelectedDot(new Coordinate(5,5));
        Coordinate targetCoordinate = new Coordinate(randomPoint(0, 10), randomPoint(0, 10));
        while (isCorrectAnswerNotOnGrid(targetCoordinate, symmetryPoint)) {
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
    public int randomPoint(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    private boolean isCorrectAnswerNotOnGrid(Coordinate targetDot, Coordinate symmetryPoint) {
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
                .setSelectedDot(selectedDot)
                .setQuestion(context.getResources().getString(R.string.FindPointWithLineSymmetry))
                .setTargetDot(targetDot);
        return gameStateBuilder.build();
    }
}
