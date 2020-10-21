package com.example.geometryapp.Levels.Symmetry;

import android.content.Context;

import com.example.geometryapp.Coordinate;
import com.example.geometryapp.DrawObjects.Line;
import com.example.geometryapp.DrawObjects.LineFigure;
import com.example.geometryapp.DrawObjects.SelectedDot;
import com.example.geometryapp.Enum.Categories;
import com.example.geometryapp.GameState;
import com.example.geometryapp.GameStateBuilder;
import com.example.geometryapp.Interface.Level;
import com.example.geometryapp.R;

import java.util.Random;

import static com.example.geometryapp.Enum.Categories.FINDFIGUREOFSYMMETRYTHROUGHPOINTOFSYMMETRY;

/**
 * This class creates the levels for find figure of symmetry through point of symmetry category
 */
public class FindFigureOfSymmetryThroughPointOfSymmetry implements Level {

    private static Random random = new Random();
    private Coordinate origin;
    private int xScale;
    private int yScale;
    private Categories category = FINDFIGUREOFSYMMETRYTHROUGHPOINTOFSYMMETRY;
    private SelectedDot selectedDot;
    private Coordinate symmetryPoint;
    private LineFigure symmetryLineFigure;

    //Creates the correct gameState
    public FindFigureOfSymmetryThroughPointOfSymmetry(int levelNum) {
        switch (levelNum){
            case 1:
                level1();
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
        selectedDot = new SelectedDot(new Coordinate(1, 1));
        symmetryPoint = new Coordinate(randomPoint(4, 6), randomPoint(4, 6));
        Coordinate startCoordinate = new Coordinate(randomPoint(0, 10), randomPoint(0, 10));
        Coordinate endCoordinate = new Coordinate(randomPoint(0, 10), randomPoint(0, 10));
        symmetryLineFigure = new LineFigure(2);
        while (isCorrectAnswerNotOnGrid(startCoordinate, symmetryPoint) || isCorrectAnswerNotOnGrid(endCoordinate, symmetryPoint)
                || (startCoordinate.getY() == endCoordinate.getY() && startCoordinate.getX() == endCoordinate.getX())) {
            if (symmetryLineFigure.getLines().size() == 0) {
                startCoordinate = new Coordinate(randomPoint(0, 10), randomPoint(0, 10));
            }
            endCoordinate = new Coordinate(randomPoint(0, 10), randomPoint(0, 10));
        }
        symmetryLineFigure.addLine(new Line(startCoordinate, endCoordinate));
        startCoordinate = new Coordinate(endCoordinate.getX(), endCoordinate.getY());
        endCoordinate = new Coordinate(randomPoint(0, 10), randomPoint(0, 10));
        while (isCorrectAnswerNotOnGrid(endCoordinate, symmetryPoint) || (startCoordinate.getY() == endCoordinate.getY() && startCoordinate.getX() == endCoordinate.getX())
                || isCoordinateBetweenCoordinates(symmetryLineFigure.getLines().get(0).getStartCoordinate(), symmetryLineFigure.getLines().get(0).getEndCoordinate(), endCoordinate)) {
            endCoordinate = new Coordinate(randomPoint(0, 10), randomPoint(0, 10));
        }
        symmetryLineFigure.addLine(new Line(startCoordinate, endCoordinate));
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

    private boolean isCoordinateBetweenCoordinates(Coordinate startCoordinate, Coordinate endCoordinate, Coordinate betweenCoordinate) {
        double slopeStartEnd = 0;
        double slopeEndBetween = 0;
        try {
            slopeStartEnd = (startCoordinate.getX() - endCoordinate.getX()) / (startCoordinate.getY() - endCoordinate.getY());
        } catch (ArithmeticException ignored) {
        }
        try {
            slopeEndBetween = (betweenCoordinate.getX() - endCoordinate.getX()) / (betweenCoordinate.getY() - endCoordinate.getY());
        } catch (ArithmeticException ignored) {
        }
        return slopeStartEnd == slopeEndBetween;
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
                .setQuestion(context.getResources().getString(R.string.FindSymmetryFigure))
                .setSelectedDot(selectedDot)
                .setSymmetryFigure(symmetryLineFigure);
        return gameStateBuilder.build();
    }
}
