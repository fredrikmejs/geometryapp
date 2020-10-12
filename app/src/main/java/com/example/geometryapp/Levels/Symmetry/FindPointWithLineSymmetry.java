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

public class FindPointWithLineSymmetry implements Level {

    private static Random random = new Random();
    private Coordinate origin;
    private int Xscale;
    private int Yscale;
    private Categories category = FINDPOINTWITHLINESYMMETRY;
    private TargetDot targetDot;
    private SelectedDot selectedDot;
    private SymmetryLine symmetryLine;
    private int randomNum;
    private Singleton singleton;


    //Creates the correct gameState
    public FindPointWithLineSymmetry(int levelNum) {


        singleton = Singleton.getInstance();

        randomNum = randomPoint(0,1);

        singleton.setRandomNum(randomNum);

        if (levelNum == 0) {
            levelNum = randomPoint(1, 2);
        }
        if (levelNum == 1) {
            level1();
        } else if (levelNum == 2) {
            level2();
        } else {
            throw new IllegalArgumentException("Level does not exist! Level index was " + levelNum);
        }
    }

    public void level1() {
        origin = new Coordinate(0, 0);
        Xscale = 1;
        Yscale = 1;
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

    public void level2() {
        origin = new Coordinate(0, 0);
        Xscale = 1;
        Yscale = 1;
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
        if(targetDot.getCoordinate().getY()+targetDot.getCoordinate().getX()==10){
            return true;
        }
        if(targetDot.getCoordinate().getY()==targetDot.getCoordinate().getX()){
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
                .setSelectedDot(new SelectedDot(new Coordinate(0,0)))
                .setSelectedDot(selectedDot)
                .setTargetDot(targetDot);
        return gameStateBuilder.build();
    }
}
