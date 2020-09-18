package com.example.geometryapp.LevelAnswers.SymmetryAnswers;

import android.util.Pair;

import com.example.geometryapp.Coordinate;
import com.example.geometryapp.GameState;
import com.example.geometryapp.Interface.LevelAnswer;
import com.example.geometryapp.Singleton;
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

        Singleton singleton = Singleton.getInstance();
        double xSelected = gameState.getOrigin().getX() + (double) coordinates.first / gameState.getXScale(); //selected x
        double ySelected = gameState.getOrigin().getY() + (double) coordinates.second / gameState.getYScale(); //selected y
        double xTarget = gameState.getTargetDot().getCoordinate().getX(); //The given x
        double yTarget = gameState.getTargetDot().getCoordinate().getY(); //the given y
        double xSymmetryPoint = gameState.getSymmetryPoint().getX(); //x for sym
        double ySymmetryPoint = gameState.getSymmetryPoint().getY(); //y for sym
        double corrextY, correctX;

        if (xSymmetryPoint <= xTarget) {
            double x = xTarget - xSymmetryPoint;
             correctX =  xSymmetryPoint - x;
            if (correctX == xSelected) {
                validatedAnswer.setIsXCorrect(true);
            } else {
                validatedAnswer.setIsXCorrect(false);
            }
            double y = ySymmetryPoint - yTarget;
             corrextY = y + ySymmetryPoint;

            if (corrextY == ySelected) {
                validatedAnswer.setIsYCorrect(true);
            } else {
                validatedAnswer.setIsYCorrect(false);
            }
        } else {
            double x = xSymmetryPoint - xTarget;
             correctX = x + xSymmetryPoint;
            if (correctX == xSelected) {
                validatedAnswer.setIsXCorrect(true);
            } else {
                validatedAnswer.setIsXCorrect(false);
            }

            double y = ySymmetryPoint - yTarget;
            corrextY = y + ySymmetryPoint;

            if (corrextY == ySelected) {
                validatedAnswer.setIsYCorrect(true);
            } else {
                validatedAnswer.setIsYCorrect(false);
            }
        }
        if (correctX == xSelected && corrextY == ySelected){
            validatedAnswer.setIsAnswerCorrect(true);
            gameState.setAnsweredCorrectly(true);
        }

        if (validatedAnswer.isAnswerCorrect() || gameState.getAttempt() >=2){
            validatedAnswer.setCorrectAnswer(new Coordinate((int)correctX, (int)corrextY));
            singleton.setXCoordinate((int) correctX);
            singleton.setYCoordinate((int) corrextY);
        }


        return validatedAnswer;
    }
}
