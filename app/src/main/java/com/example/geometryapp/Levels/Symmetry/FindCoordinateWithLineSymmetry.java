package com.example.geometryapp.Levels.Symmetry;

import android.content.Context;
import android.widget.Toast;

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

import static com.example.geometryapp.Enum.Categories.FINDCOORDINATEWITHSYMMETRY;

public class FindCoordinateWithLineSymmetry implements Level {

    private static Random random = new Random();
    private Coordinate origin;
    private int Xscale;
    private int Yscale;
    private Categories category = FINDCOORDINATEWITHSYMMETRY;
    private TargetDot targetDot;
    private SelectedDot selectedDot;
    private SymmetryLine symmetryLine;
    private Singleton singleton;
    private int randomNum;

    //Creates the correct gameState
    public FindCoordinateWithLineSymmetry(int levelNum) {

        singleton = Singleton.getInstance();

        randomNum = randomPoint(0,1);

        singleton.setRandomNum(randomNum);

        switch (levelNum){
            case 0:
                levelNum = randomPoint(1,4);
                new FindCoordinateWithLineSymmetry(levelNum);
                break;
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
            case 6:
                level6();
                break;
            case 7:
                level7();
                break;
            case 8:
                level8();
                break;
            default:
                throw new IllegalArgumentException("Level does not exist! Level index was " + levelNum);
        }

    }

    public void level1() {
        int[] easyNumbers = {2,3,4,5,10};
        origin = new Coordinate(0, 0);
        Xscale = easyNumbers[(int) (Math.random()*easyNumbers.length)];
        Yscale = Xscale;
        selectedDot = new SelectedDot(new Coordinate(5,5));
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

    public void level2() {
        int[] advancedNumbers = {6,7,8,9};
        origin = new Coordinate(0, 0);
        Xscale = advancedNumbers[(int) (Math.random()*advancedNumbers.length)];
        Yscale = Xscale;
        selectedDot = new SelectedDot(new Coordinate(5,5));
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

    public void level3() {
        int[] easyNumbers = {2,3,4,5,10};
        origin = new Coordinate(0, 0);
        Xscale = easyNumbers[(int) (Math.random()*easyNumbers.length)];
        Yscale = Xscale;
        selectedDot = new SelectedDot(new Coordinate(5,5));
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

    public void level4() {
        int[] advancedNumbers = {6,7,8,9};
        origin = new Coordinate(0, 0);
        Xscale = advancedNumbers[(int) (Math.random()*advancedNumbers.length)];
        Yscale = Xscale;
        selectedDot = new SelectedDot(new Coordinate(5,5));
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

    public void level5() {
        int[] easyNumbers = {2,3,4,5,10};
        origin = new Coordinate(randomPoint(1,9), randomPoint(1,9));
        Xscale = easyNumbers[(int) (Math.random()*easyNumbers.length)];
        Yscale = Xscale;
        selectedDot = new SelectedDot(new Coordinate(5,5));
        if (randomNum == 0) {
            symmetryLine = new SymmetryLine(new Coordinate(5, 0), new Coordinate(5, 10));
        } else  {
            symmetryLine = new SymmetryLine(new Coordinate(0, 5), new Coordinate(10, 5));
        }
        targetDot = new TargetDot(new Coordinate(randomPoint(0, 10), randomPoint(0, 10)));
        while (targetDotIsOnSymmetryLine(targetDot.getCoordinate())) {
            targetDot = new TargetDot(new Coordinate(randomPoint(0, 10), randomPoint(0, 10)));
        }
    }

    public void level6() {
        int[] advancedNumbers = {6,7,8,9};
        origin = new Coordinate(0, 0);
        Xscale = advancedNumbers[(int) (Math.random()*advancedNumbers.length)];
        Yscale = Xscale;
        selectedDot = new SelectedDot(new Coordinate(5,5));
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

    public void level7() {
        int[] easyNumbers = {2,3,4,5,10};
        origin = new Coordinate(randomPoint(1,9), randomPoint(1,9));
        Xscale = easyNumbers[(int) (Math.random()*easyNumbers.length)];
        Yscale = Xscale;
        selectedDot = new SelectedDot(new Coordinate(5,5));
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
    public void level8() {
        int[] advancedNumbers = {6,7,8,9};
        origin = new Coordinate(0, 0);
        Xscale = advancedNumbers[(int) (Math.random()*advancedNumbers.length)];
        Yscale = Xscale;
        selectedDot = new SelectedDot(new Coordinate(5,5));
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

    public int randomPoint(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    public boolean targetDotIsOnSymmetryLine(Coordinate coordinate) {
        //Target point cannot locate on diagonal line or x = 5 or y = 5
        if (symmetryLine.getStartCoordinate().getX() == 5 && coordinate.getX() == 5) {
            return true;
        }
        if (symmetryLine.getStartCoordinate().getY() == 5 && coordinate.getY() == 5) {
            return true;
        }
        if (targetDot.getCoordinate().getY() + targetDot.getCoordinate().getX() == 10) {
            return true;
        }
        if (targetDot.getCoordinate().getY() == targetDot.getCoordinate().getX()) {
            return true;
        }
        return false;
    }

    @Override
    public GameState getDefaultLevelState(Context context) {
        GameStateBuilder gameStateBuilder = new GameStateBuilder();
        gameStateBuilder.setOrigin(origin)
                .setXScale(Xscale)
                .setYScale(Yscale)
                .setCategory(category)
                .setSymmetryLine(symmetryLine)
                .setQuestion(context.getResources().getString(R.string.FindPointWithLineSymmetry))
                .setSelectedDot(selectedDot)
                .setTargetDot(targetDot);
        return gameStateBuilder.build();
    }
}

