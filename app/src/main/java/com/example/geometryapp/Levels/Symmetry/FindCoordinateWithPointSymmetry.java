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

public class FindCoordinateWithPointSymmetry implements Level {

    private static Random random = new Random();
    private Coordinate origin;
    private int Xscale;
    private int Yscale;
    private Categories category = FINDCOORDINATEWITHPOINTSYMMETRY;
    private SelectedDot selectedDot = new SelectedDot(new Coordinate(0, 0));
    private Coordinate symmetryPoint;
    private TargetDot targetDot;

    //Creates the correct gameState
    public FindCoordinateWithPointSymmetry(int levelNum) {
        if (levelNum == 0) {
            levelNum = randomPoint(1, 5);
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
        } else {
            throw new IllegalArgumentException("Level does not exist! Level index was " + levelNum);
        }
    }

    public void level1() {
        origin = new Coordinate(0, 0);
        Xscale = 1;
        Yscale = 1;
        symmetryPoint = new Coordinate(5, 5);
        selectedDot = new SelectedDot(new Coordinate(5,5));
        Coordinate targetCoordinate = new Coordinate(randomPoint(0, 10), randomPoint(0, 10));
        while (isCorrectAnswerNotOnGrid(targetCoordinate, symmetryPoint)) {
            targetCoordinate = new Coordinate(randomPoint(0, 10), randomPoint(0, 10));
        }
        targetDot = new TargetDot(targetCoordinate);
    }

    public void level2() {
        origin = new Coordinate(0, 0);
        Xscale = 1;
        Yscale = 1;
        symmetryPoint = new Coordinate(randomPoint(3, 7), randomPoint(3, 7));
        selectedDot = new SelectedDot(new Coordinate(5,5));
        Coordinate targetCoordinate = new Coordinate(randomPoint(0, 10), randomPoint(0, 10));
        while (isCorrectAnswerNotOnGrid(targetCoordinate, symmetryPoint)) {
            targetCoordinate = new Coordinate(randomPoint(0, 10), randomPoint(0, 10));
        }
        targetDot = new TargetDot(targetCoordinate);
    }

    public void level3() {
        origin = new Coordinate(randomPoint(1, 9), randomPoint(1, 9));
        Xscale = 1;
        Yscale = 1;
        symmetryPoint = new Coordinate(randomPoint(3, 7), randomPoint(3, 7));
        selectedDot = new SelectedDot(new Coordinate(5,5));
        Coordinate targetCoordinate = new Coordinate(randomPoint(0, 10), randomPoint(0, 10));
        while (isCorrectAnswerNotOnGrid(targetCoordinate, symmetryPoint)) {
            targetCoordinate = new Coordinate(randomPoint(0, 10), randomPoint(0, 10));
        }
        targetDot = new TargetDot(targetCoordinate);
    }

    public void level4() {
        int[] easyNumbers = {2,3,4,5,10};
        origin = new Coordinate(randomPoint(1, 9), randomPoint(1, 9));
        Xscale = easyNumbers[(int) (Math.random()*easyNumbers.length)];
        Yscale = easyNumbers[(int) (Math.random()*easyNumbers.length)];
        symmetryPoint = new Coordinate(randomPoint(3, 7), randomPoint(3, 7));
        selectedDot = new SelectedDot(new Coordinate(5,5));
        Coordinate targetCoordinate = new Coordinate(randomPoint(0, 10), randomPoint(0, 10));
        while (isCorrectAnswerNotOnGrid(targetCoordinate, symmetryPoint)) {
            targetCoordinate = new Coordinate(randomPoint(0, 10), randomPoint(0, 10));
        }
        targetDot = new TargetDot(targetCoordinate);
    }

    public void level5() {
        int[] advancedNumbers = {6,7,8,9};
        origin = new Coordinate(randomPoint(1, 9), randomPoint(1, 9));
        Xscale = advancedNumbers[(int) (Math.random()*advancedNumbers.length)];
        Yscale = advancedNumbers[(int) (Math.random()*advancedNumbers.length)];
        symmetryPoint = new Coordinate(randomPoint(3, 7), randomPoint(3, 7));
        selectedDot = new SelectedDot(new Coordinate(5,5));
        Coordinate targetCoordinate = new Coordinate(randomPoint(0, 10), randomPoint(0, 10));
        while (isCorrectAnswerNotOnGrid(targetCoordinate, symmetryPoint)) {
            targetCoordinate = new Coordinate(randomPoint(0, 10), randomPoint(0, 10));
        }
        targetDot = new TargetDot(targetCoordinate);
    }

    public int randomPoint(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    private boolean isCorrectAnswerNotOnGrid(Coordinate targetDot, Coordinate symmetryPoint) {
        if ((symmetryPoint.getX() + Math.abs(symmetryPoint.getX() - targetDot.getX()) <= 10
                && symmetryPoint.getX() - Math.abs((symmetryPoint.getX() - targetDot.getX())) >= 0)
                && (symmetryPoint.getY() + Math.abs((symmetryPoint.getY() - targetDot.getY())) <= 10
                && symmetryPoint.getY() - Math.abs((symmetryPoint.getY() - targetDot.getY())) >= 0)
                && (symmetryPoint.getX() != targetDot.getX() || targetDot.getY() != symmetryPoint.getY())) {
            return false;
        }
        return true;
    }

    @Override
    public GameState getDefaultLevelState(Context context) {
        GameStateBuilder gameStateBuilder = new GameStateBuilder();
        gameStateBuilder.setOrigin(origin)
                .setXScale(Xscale)
                .setYScale(Yscale)
                .setCategory(category)
                .setSymmetryPoint(symmetryPoint)
                .setSelectedDot(selectedDot)
                .setQuestion(context.getResources().getString(R.string.FindPointWithLineSymmetry))
                .setTargetDot(targetDot);
        return gameStateBuilder.build();
    }
}
