package com.example.geometryapp.LevelAnswers.SymmetryAnswers;

import com.example.geometryapp.Coordinate;
import com.example.geometryapp.GameState;
import com.example.geometryapp.Interface.LevelAnswer;
import com.example.geometryapp.ValidatedAnswer;

public class AnswerFindPointWithLineSymmetry implements LevelAnswer {

    //Validates if answer was correct
    @Override
    public ValidatedAnswer isAnswerCorrect(GameState gameState, int levelIndex) {
        ValidatedAnswer validatedAnswer = new ValidatedAnswer(false, false, false);
        int xSelected = gameState.getSelectedDot().getCoordinate().getX();
        int ySelected = gameState.getSelectedDot().getCoordinate().getY();
        int xTarget = gameState.getTargetDot().getCoordinate().getX();
        int yTarget = gameState.getTargetDot().getCoordinate().getY();
        Coordinate symmetryLineStart = gameState.getSymmetryLine().getStartCoordinate();
        Coordinate symmetryLineEnd = gameState.getSymmetryLine().getEndCoordinate();
        //Creates perpendicular line
        double distanceSelectedToSymmetryLineStart = Math.sqrt((xSelected - symmetryLineStart.getX()) * (xSelected - symmetryLineStart.getX())
                + (ySelected - symmetryLineStart.getY()) * (ySelected - symmetryLineStart.getY()));
        double distanceSelectedToSymmetryLineEnd = Math.sqrt((xSelected - symmetryLineEnd.getX()) * (xSelected - symmetryLineEnd.getX())
                + (ySelected - symmetryLineEnd.getY()) * (ySelected - symmetryLineEnd.getY()));
        double distanceTargetToSymmetryLineStart = Math.sqrt((xTarget - symmetryLineStart.getX()) * (xTarget - symmetryLineStart.getX())
                + (yTarget - symmetryLineStart.getY()) * (yTarget - symmetryLineStart.getY()));
        double distanceTargetToSymmetryLineEnd = Math.sqrt((xTarget - symmetryLineEnd.getX()) * (xTarget - symmetryLineEnd.getX())
                + (yTarget - symmetryLineEnd.getY()) * (yTarget - symmetryLineEnd.getY()));
        if (distanceSelectedToSymmetryLineStart == distanceTargetToSymmetryLineStart && distanceSelectedToSymmetryLineEnd == distanceTargetToSymmetryLineEnd
                && (xSelected != xTarget || ySelected != yTarget)) {
            validatedAnswer.setIsAnswerCorrect(true);
            validatedAnswer.setIsYCorrect(true);
            validatedAnswer.setIsXCorrect(true);
        } else {
            for (int x = 0; x < 11; x++) {
                for (int y = 0; y < 11; y++) {
                    if (isPointCorrectAnswer(gameState,x,y)){
                        validatedAnswer.setCorrectAnswer(new Coordinate(x, y));
                        return validatedAnswer;
                    }
                }
            }
        }
        return validatedAnswer;
    }

    public boolean isPointCorrectAnswer(GameState gameState, int x, int y) {
        int xSelected = x;
        int ySelected = y;
        int xTarget = gameState.getTargetDot().getCoordinate().getX();
        int yTarget = gameState.getTargetDot().getCoordinate().getY();
        Coordinate symmetryLineStart = gameState.getSymmetryLine().getStartCoordinate();
        Coordinate symmetryLineEnd = gameState.getSymmetryLine().getEndCoordinate();
        double distanceSelectedToSymmetryLineStart = Math.sqrt((xSelected - symmetryLineStart.getX()) * (xSelected - symmetryLineStart.getX())
                + (ySelected - symmetryLineStart.getY()) * (ySelected - symmetryLineStart.getY()));
        double distanceSelectedToSymmetryLineEnd = Math.sqrt((xSelected - symmetryLineEnd.getX()) * (xSelected - symmetryLineEnd.getX())
                + (ySelected - symmetryLineEnd.getY()) * (ySelected - symmetryLineEnd.getY()));
        double distanceTargetToSymmetryLineStart = Math.sqrt((xTarget - symmetryLineStart.getX()) * (xTarget - symmetryLineStart.getX())
                + (yTarget - symmetryLineStart.getY()) * (yTarget - symmetryLineStart.getY()));
        double distanceTargetToSymmetryLineEnd = Math.sqrt((xTarget - symmetryLineEnd.getX()) * (xTarget - symmetryLineEnd.getX())
                + (yTarget - symmetryLineEnd.getY()) * (yTarget - symmetryLineEnd.getY()));
        if (distanceSelectedToSymmetryLineStart == distanceTargetToSymmetryLineStart && distanceSelectedToSymmetryLineEnd == distanceTargetToSymmetryLineEnd
                && (xSelected != xTarget || ySelected != yTarget)) {
            return true;
        }
        return false;
    }

}
