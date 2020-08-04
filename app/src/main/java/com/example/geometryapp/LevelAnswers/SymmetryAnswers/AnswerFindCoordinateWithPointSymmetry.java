package com.example.geometryapp.LevelAnswers.SymmetryAnswers;

import android.util.Pair;

import com.example.geometryapp.Coordinate;
import com.example.geometryapp.GameState;
import com.example.geometryapp.Interface.LevelAnswer;
import com.example.geometryapp.ValidatedAnswer;

public class AnswerFindCoordinateWithPointSymmetry implements LevelAnswer {

    //Validates if answer was correct
    @Override
    public ValidatedAnswer isAnswerCorrect(GameState gameState, int levelIndex) {
        ValidatedAnswer validatedAnswer = new ValidatedAnswer(false, false, false);
        Pair<Integer, Integer> coordinates;
        try {
            coordinates = new Pair<>(Integer.parseInt(gameState.getTypedCoordinateAnswer().first)
                    , Integer.parseInt(gameState.getTypedCoordinateAnswer().second));
        }catch (IllegalArgumentException e){
            return new ValidatedAnswer(false,false,false);
        }
        int xSelected = gameState.getOrigin().getX() + coordinates.first / gameState.getXScale();
        int ySelected = gameState.getOrigin().getY() + coordinates.second / gameState.getYScale();
        int xTarget = gameState.getTargetDot().getCoordinate().getX();
        int yTarget = gameState.getTargetDot().getCoordinate().getY();
        int xSymmetryPoint = gameState.getSymmetryPoint().getX();
        int ySymmetryPoint = gameState.getSymmetryPoint().getY();
        double distanceSymmetryPointTarget = Math.sqrt((xTarget - xSymmetryPoint) * (xTarget - xSymmetryPoint)
                + (yTarget - ySymmetryPoint) * (yTarget - ySymmetryPoint));
        double distanceSymmetryPointSelected = Math.sqrt((xSelected - xSymmetryPoint) * (xSelected - xSymmetryPoint)
                + (ySelected - ySymmetryPoint) * (ySelected - ySymmetryPoint));
        if (distanceSymmetryPointSelected == distanceSymmetryPointTarget
                && (xSymmetryPoint == (xSelected + xTarget) / 2 && ySymmetryPoint == (ySelected + yTarget) / 2)) {
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

    public boolean isPointCorrectAnswer(GameState gameState, int x, int y) {
        int xSelected = x;
        int ySelected = y;
        int xTarget = gameState.getTargetDot().getCoordinate().getX();
        int yTarget = gameState.getTargetDot().getCoordinate().getY();
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
