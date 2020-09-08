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
        } catch (IllegalArgumentException e) {
            return new ValidatedAnswer(false, false, false);
        }

        int xSelected = gameState.getOrigin().getX() + coordinates.first / gameState.getXScale(); //selected x
        int ySelected = gameState.getOrigin().getY() + coordinates.second / gameState.getYScale(); //selected y
        int xTarget = gameState.getTargetDot().getCoordinate().getX(); //The given x
        int yTarget = gameState.getTargetDot().getCoordinate().getY(); //the given y
        int xSymmetryPoint = gameState.getSymmetryPoint().getX(); //x for sym
        int ySymmetryPoint = gameState.getSymmetryPoint().getY(); //y for sym
        int b, a;

        if (xSymmetryPoint <= xTarget) {
            int x = xTarget - xSymmetryPoint;
             a =  xSymmetryPoint - x;
            if (a == xSelected) {
                validatedAnswer.setIsXCorrect(true);
            } else {
                validatedAnswer.setIsXCorrect(false);
            }
            int y = ySymmetryPoint - yTarget;
             b = y + ySymmetryPoint;

            if (b == ySelected) {
                validatedAnswer.setIsYCorrect(true);
            } else {
                validatedAnswer.setIsYCorrect(false);
            }
        } else {
            int x = xSymmetryPoint - xTarget;
             a = x + xSymmetryPoint;
            if (a == xSelected) {
                validatedAnswer.setIsXCorrect(true);
            } else {
                validatedAnswer.setIsXCorrect(false);
            }

            int y = ySymmetryPoint - yTarget;
            b = y + ySymmetryPoint;

            if (b == ySelected) {
                validatedAnswer.setIsYCorrect(true);
            } else {
                validatedAnswer.setIsYCorrect(false);
            }
        }
        if (a == xSelected && b == ySelected){
            validatedAnswer.setIsAnswerCorrect(true);
            gameState.setAnsweredCorrectly(true);
        }



        /**  double distanceSymmetryPointTarget = Math.sqrt((xTarget - xSymmetryPoint) * (xTarget - xSymmetryPoint)
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
        } */
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
