package com.example.geometryapp.LevelAnswers.SymmetryAnswers;

import android.util.Pair;

import com.example.geometryapp.Coordinate;
import com.example.geometryapp.GameState;
import com.example.geometryapp.Interface.LevelAnswer;
import com.example.geometryapp.ValidatedAnswer;

public class AnswerFindCoordinateWithLineSymmetry implements LevelAnswer {

    //Validates if answer was correct
    @Override
    public ValidatedAnswer isAnswerCorrect(GameState gameState, int levelIndex) {
        ValidatedAnswer validatedAnswer = new ValidatedAnswer(false, false, false);
        if (gameState.getTypedCoordinateAnswer() == null) {
            for (int x = 0; x < 11; x++) {
                for (int y = 0; y < 11; y++) {
                    if (isPointCorrectAnswer(gameState, x, y)) {
                        validatedAnswer.setCorrectAnswer(new Coordinate(x, y));
                        return validatedAnswer;
                    }
                }
            }
            return validatedAnswer;
        }
        Pair<Integer, Integer> coordinates;
        try {
            coordinates = new Pair<>(Integer.parseInt(gameState.getTypedCoordinateAnswer().first)
                    , Integer.parseInt(gameState.getTypedCoordinateAnswer().second));
        }catch (IllegalArgumentException e){
            return new ValidatedAnswer(false,false,false);
        }
        int xSelected = coordinates.first;
        int ySelected = coordinates.second;
        int xTarget = (gameState.getTargetDot().getCoordinate().getX() - gameState.getOrigin().getX()) * gameState.getXScale();
        int yTarget = (gameState.getTargetDot().getCoordinate().getY() - gameState.getOrigin().getY()) * gameState.getYScale();
        //Adding scaling to coordinates
        Coordinate newstartCoordinate = new Coordinate(gameState.getSymmetryLine().getStartCoordinate().getX(), gameState.getSymmetryLine().getStartCoordinate().getY());
        Coordinate newendCoordinate = new Coordinate(gameState.getSymmetryLine().getEndCoordinate().getX(), gameState.getSymmetryLine().getEndCoordinate().getY());
        newstartCoordinate.setX((newstartCoordinate.getX() - gameState.getOrigin().getX()) * gameState.getXScale());
        newstartCoordinate.setY((newstartCoordinate.getY() - gameState.getOrigin().getY()) * gameState.getYScale());
        newendCoordinate.setX((newendCoordinate.getX() - gameState.getOrigin().getX()) * gameState.getXScale());
        newendCoordinate.setY((newendCoordinate.getY() - gameState.getOrigin().getY()) * gameState.getYScale());
        //Calculates distances from symmetry lines start and end to target and selected point.  Because of symmetry distance should be same if answer is correct.
        double distanceSelectedToSymmetryLineStart = Math.sqrt((xSelected - newstartCoordinate.getX()) * (xSelected - newstartCoordinate.getX())
                + (ySelected - newstartCoordinate.getY()) * (ySelected - newstartCoordinate.getY()));
        double distanceSelectedToSymmetryLineEnd = Math.sqrt((xSelected - newendCoordinate.getX()) * (xSelected - newendCoordinate.getX())
                + (ySelected - newendCoordinate.getY()) * (ySelected - newendCoordinate.getY()));
        double distanceTargetToSymmetryLineStart = Math.sqrt((xTarget - newstartCoordinate.getX()) * (xTarget - newstartCoordinate.getX())
                + (yTarget - newstartCoordinate.getY()) * (yTarget - newstartCoordinate.getY()));
        double distanceTargetToSymmetryLineEnd = Math.sqrt((xTarget - newendCoordinate.getX()) * (xTarget - newendCoordinate.getX())
                + (yTarget - newendCoordinate.getY()) * (yTarget - newendCoordinate.getY()));
        if (distanceSelectedToSymmetryLineStart == distanceTargetToSymmetryLineStart && distanceSelectedToSymmetryLineEnd == distanceTargetToSymmetryLineEnd
                && (xSelected != xTarget || ySelected != yTarget)) {
            validatedAnswer.setIsAnswerCorrect(true);
            validatedAnswer.setIsYCorrect(true);
            validatedAnswer.setIsXCorrect(true);
            gameState.setAnsweredCorrectly(true);
        }
        for (int x = 0; x < 11; x++) {
            for (int y = 0; y < 11; y++) {
                if (isPointCorrectAnswer(gameState, x, y)) {
                    validatedAnswer.setCorrectAnswer(new Coordinate(x, y));
                    return validatedAnswer;
                }
            }
        }
        return validatedAnswer;
    }

    //Checks if point x and y is correct
    public boolean isPointCorrectAnswer(GameState gameState, int x, int y) {
        int xSelected = (x - gameState.getOrigin().getX()) * gameState.getXScale();
        int ySelected = (y - gameState.getOrigin().getY()) * gameState.getYScale();
        int xTarget = (gameState.getTargetDot().getCoordinate().getX() - gameState.getOrigin().getX()) * gameState.getXScale();
        int yTarget = (gameState.getTargetDot().getCoordinate().getY() - gameState.getOrigin().getY()) * gameState.getYScale();
        //Adding scaling to coordinates
        Coordinate newStartCoordinate = new Coordinate(gameState.getSymmetryLine().getStartCoordinate().getX(), gameState.getSymmetryLine().getStartCoordinate().getY());
        Coordinate newEndCoordinate = new Coordinate(gameState.getSymmetryLine().getEndCoordinate().getX(), gameState.getSymmetryLine().getEndCoordinate().getY());
        newStartCoordinate.setX((newStartCoordinate.getX() - gameState.getOrigin().getX()) * gameState.getXScale());
        newStartCoordinate.setY((newStartCoordinate.getY() - gameState.getOrigin().getY()) * gameState.getYScale());
        newEndCoordinate.setX((newEndCoordinate.getX() - gameState.getOrigin().getX()) * gameState.getXScale());
        newEndCoordinate.setY((newEndCoordinate.getY() - gameState.getOrigin().getY()) * gameState.getYScale());
        //Calculates distances from symmetry lines start and end to target and selected point.  Because of symmetry distance should be same if answer is correct.
        double distanceSelectedToSymmetryLineStart = Math.sqrt((xSelected - newStartCoordinate.getX()) * (xSelected - newStartCoordinate.getX())
                + (ySelected - newStartCoordinate.getY()) * (ySelected - newStartCoordinate.getY()));
        double distanceSelectedToSymmetryLineEnd = Math.sqrt((xSelected - newEndCoordinate.getX()) * (xSelected - newEndCoordinate.getX())
                + (ySelected - newEndCoordinate.getY()) * (ySelected - newEndCoordinate.getY()));
        double distanceTargetToSymmetryLineStart = Math.sqrt((xTarget - newStartCoordinate.getX()) * (xTarget - newStartCoordinate.getX())
                + (yTarget - newStartCoordinate.getY()) * (yTarget - newStartCoordinate.getY()));
        double distanceTargetToSymmetryLineEnd = Math.sqrt((xTarget - newEndCoordinate.getX()) * (xTarget - newEndCoordinate.getX())
                + (yTarget - newEndCoordinate.getY()) * (yTarget - newEndCoordinate.getY()));
        if (distanceSelectedToSymmetryLineStart == distanceTargetToSymmetryLineStart
                && distanceSelectedToSymmetryLineEnd == distanceTargetToSymmetryLineEnd
                && (xSelected != xTarget || ySelected != yTarget)) {
            return true;
        }
        return false;
    }

}
