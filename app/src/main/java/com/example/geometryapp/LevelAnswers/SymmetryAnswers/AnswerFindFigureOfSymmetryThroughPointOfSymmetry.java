package com.example.geometryapp.LevelAnswers.SymmetryAnswers;

import com.example.geometryapp.Coordinate;
import com.example.geometryapp.DrawObjects.Line;
import com.example.geometryapp.DrawObjects.LineFigure;
import com.example.geometryapp.GameState;
import com.example.geometryapp.Interface.LevelAnswer;
import com.example.geometryapp.ValidatedAnswer;

import java.util.ArrayList;

public class AnswerFindFigureOfSymmetryThroughPointOfSymmetry implements LevelAnswer {

    //Validates if answer was correct
    @Override
    public ValidatedAnswer isAnswerCorrect(GameState gameState, int levelIndex) {
        ValidatedAnswer validatedAnswer = new ValidatedAnswer(false, false, false);
        boolean allPointCorrect = true;
        if (gameState.getSelectedDot().getPreviousCoordinates().size() - 2 != gameState.getSymmetryFigure().getMaxAmountOfLines()) {
            allPointCorrect = false;
        }else{
            //Test if shapes is same as symmetry shape. Testing is done twice because shape can be drawn from start to end or from end to start
            for (int i = 0; i < gameState.getSymmetryFigure().getLines().size(); i++) {
                Coordinate coordinateDrawn = gameState.getSelectedDot().getPreviousCoordinates().get(gameState.getSelectedDot().getPreviousCoordinates().size() - i - 1);
                Coordinate coordinateSymmetry = gameState.getSymmetryFigure().getLines().get(i).getStartCoordinate();
                int xSelected = coordinateDrawn.getX();
                int ySelected = coordinateDrawn.getY();
                int xTarget = coordinateSymmetry.getX();
                int yTarget = coordinateSymmetry.getY();
                int xSymmetryPoint = gameState.getSymmetryPoint().getX();
                int ySymmetryPoint = gameState.getSymmetryPoint().getY();
                double distanceSymmetryPointTarget = Math.sqrt((xTarget - xSymmetryPoint) * (xTarget - xSymmetryPoint)
                        + (yTarget - ySymmetryPoint) * (yTarget - ySymmetryPoint));
                double distanceSymmetryPointSelected = Math.sqrt((xSelected - xSymmetryPoint) * (xSelected - xSymmetryPoint)
                        + (ySelected - ySymmetryPoint) * (ySelected - ySymmetryPoint));
                if (!(distanceSymmetryPointSelected == distanceSymmetryPointTarget
                        && (xSymmetryPoint == (xSelected + xTarget) / 2 && ySymmetryPoint == (ySelected + yTarget) / 2))) {
                    allPointCorrect = false;
                }
                if (i + 1 == gameState.getSymmetryFigure().getLines().size()) {
                    coordinateDrawn = gameState.getSelectedDot().getPreviousCoordinates().get(gameState.getSelectedDot().getPreviousCoordinates().size() - i - 1);
                    coordinateSymmetry = gameState.getSymmetryFigure().getLines().get(i).getStartCoordinate();
                    xSelected = coordinateDrawn.getX();
                    ySelected = coordinateDrawn.getY();
                    xTarget = coordinateSymmetry.getX();
                    yTarget = coordinateSymmetry.getY();
                    xSymmetryPoint = gameState.getSymmetryPoint().getX();
                    ySymmetryPoint = gameState.getSymmetryPoint().getY();
                    distanceSymmetryPointTarget = Math.sqrt((xTarget - xSymmetryPoint) * (xTarget - xSymmetryPoint)
                            + (yTarget - ySymmetryPoint) * (yTarget - ySymmetryPoint));
                    distanceSymmetryPointSelected = Math.sqrt((xSelected - xSymmetryPoint) * (xSelected - xSymmetryPoint)
                            + (ySelected - ySymmetryPoint) * (ySelected - ySymmetryPoint));
                    if (!(distanceSymmetryPointSelected == distanceSymmetryPointTarget
                            && (xSymmetryPoint == (xSelected + xTarget) / 2 && ySymmetryPoint == (ySelected + yTarget) / 2))) {
                        allPointCorrect = false;
                    }
                }
            }
            if (!allPointCorrect) {
                allPointCorrect = true;
                for (int i = 0; i < gameState.getSymmetryFigure().getLines().size(); i++) {
                    Coordinate coordinateDrawn = gameState.getSelectedDot().getPreviousCoordinates().get(gameState.getSelectedDot().getPreviousCoordinates().size() - i - 1);
                    Coordinate coordinateSymmetry = gameState.getSymmetryFigure().getLines().get(1 - i).getEndCoordinate();
                    int xSelected = coordinateDrawn.getX();
                    int ySelected = coordinateDrawn.getY();
                    int xTarget = coordinateSymmetry.getX();
                    int yTarget = coordinateSymmetry.getY();
                    int xSymmetryPoint = gameState.getSymmetryPoint().getX();
                    int ySymmetryPoint = gameState.getSymmetryPoint().getY();
                    double distanceSymmetryPointTarget = Math.sqrt((xTarget - xSymmetryPoint) * (xTarget - xSymmetryPoint)
                            + (yTarget - ySymmetryPoint) * (yTarget - ySymmetryPoint));
                    double distanceSymmetryPointSelected = Math.sqrt((xSelected - xSymmetryPoint) * (xSelected - xSymmetryPoint)
                            + (ySelected - ySymmetryPoint) * (ySelected - ySymmetryPoint));
                    if (!(distanceSymmetryPointSelected == distanceSymmetryPointTarget
                            && (xSymmetryPoint == (xSelected + xTarget) / 2 && ySymmetryPoint == (ySelected + yTarget) / 2))) {
                        allPointCorrect = false;
                    }
                    if (i - 1 == gameState.getSymmetryFigure().getLines().size()) {
                        coordinateDrawn = gameState.getSelectedDot().getPreviousCoordinates().get(gameState.getSelectedDot().getPreviousCoordinates().size() - i - 1);
                        coordinateSymmetry = gameState.getSymmetryFigure().getLines().get(1 - i).getEndCoordinate();
                        xSelected = coordinateDrawn.getX();
                        ySelected = coordinateDrawn.getY();
                        xTarget = coordinateSymmetry.getX();
                        yTarget = coordinateSymmetry.getY();
                        xSymmetryPoint = gameState.getSymmetryPoint().getX();
                        ySymmetryPoint = gameState.getSymmetryPoint().getY();
                        distanceSymmetryPointTarget = Math.sqrt((xTarget - xSymmetryPoint) * (xTarget - xSymmetryPoint)
                                + (yTarget - ySymmetryPoint) * (yTarget - ySymmetryPoint));
                        distanceSymmetryPointSelected = Math.sqrt((xSelected - xSymmetryPoint) * (xSelected - xSymmetryPoint)
                                + (ySelected - ySymmetryPoint) * (ySelected - ySymmetryPoint));
                        if (!(distanceSymmetryPointSelected == distanceSymmetryPointTarget
                                && (xSymmetryPoint == (xSelected + xTarget) / 2 && ySymmetryPoint == (ySelected + yTarget) / 2))) {
                            allPointCorrect = false;
                        }
                    }
                }
            }
        }
        //Create correct answer
        ArrayList<Coordinate> coordinate = new ArrayList<>();
        if (allPointCorrect) {
            validatedAnswer.setIsAnswerCorrect(true);
            validatedAnswer.setIsYCorrect(true);
            validatedAnswer.setIsXCorrect(true);
        } else {
            boolean answerFound = false;
            for (int x = 0; x < 11; x++) {
                for (int y = 0; y < 11; y++) {
                    if (isPointCorrectAnswer(gameState, x, y, gameState.getSymmetryFigure().getLine(0).getStartCoordinate()) && !answerFound) {
                        coordinate.add(new Coordinate(x, y));
                        answerFound = true;
                    }
                }
            }
            answerFound = false;
            for (int x = 0; x < 11; x++) {
                for (int y = 0; y < 11; y++) {
                    if (isPointCorrectAnswer(gameState, x, y, gameState.getSymmetryFigure().getLine(0).getEndCoordinate()) && !answerFound) {
                        coordinate.add(new Coordinate(x, y));
                        answerFound = true;
                    }
                }
            }
            answerFound = false;
            for (int x = 0; x < 11; x++) {
                for (int y = 0; y < 11; y++) {
                    if (isPointCorrectAnswer(gameState, x, y, gameState.getSymmetryFigure().getLine(1).getEndCoordinate()) && !answerFound) {
                        coordinate.add(new Coordinate(x, y));
                        answerFound = true;
                    }
                }
            }
            LineFigure lineFigure = new LineFigure(2);
            lineFigure.addLine(new Line(coordinate.get(0), coordinate.get(1)));
            lineFigure.addLine(new Line(coordinate.get(1), coordinate.get(2)));
            gameState.setLineFigureCorrectAnswer(lineFigure);
            validatedAnswer.LineFigure(lineFigure);
        }
        return validatedAnswer;
    }

    public boolean isPointCorrectAnswer(GameState gameState, int x, int y, Coordinate symmetryFigureCoordinate) {
        int xSelected = x;
        int ySelected = y;
        int xTarget = symmetryFigureCoordinate.getX();
        int yTarget = symmetryFigureCoordinate.getY();
        int xSymmetryPoint = gameState.getSymmetryPoint().getX();
        int ySymmetryPoint = gameState.getSymmetryPoint().getY();
        double distanceSymmetryPointTarget = Math.sqrt((xTarget - xSymmetryPoint) * (xTarget - xSymmetryPoint)
                + (yTarget - ySymmetryPoint) * (yTarget - ySymmetryPoint));
        double distanceSymmetryPointSelected = Math.sqrt((xSelected - xSymmetryPoint) * (xSelected - xSymmetryPoint)
                + (ySelected - ySymmetryPoint) * (ySelected - ySymmetryPoint));
        if (distanceSymmetryPointSelected == distanceSymmetryPointTarget
                && (xSymmetryPoint == (xSelected + xTarget) / 2 && ySymmetryPoint == (ySelected + yTarget) / 2)) {
            return true;
        }
        return false;
    }
}
