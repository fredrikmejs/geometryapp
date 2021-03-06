package com.example.geometryapp.LevelAnswers.SymmetryAnswers;

import com.example.geometryapp.Coordinate;
import com.example.geometryapp.GameState;
import com.example.geometryapp.Interface.LevelAnswer;
import com.example.geometryapp.Singleton;
import com.example.geometryapp.ValidatedAnswer;

/**
 * This class calculate and validates the answer of find point with point symmetry category
 */
public class AnswerFindPointWithPointSymmetry implements LevelAnswer {

    //Validates if answer was correct
    @Override
    public ValidatedAnswer isAnswerCorrect(GameState gameState, int levelIndex) {

        ValidatedAnswer validatedAnswer = new ValidatedAnswer(false, false, false);

        Singleton singleton = Singleton.getInstance();
        double xSelected = gameState.getOrigin().getX() + (double) gameState.getSelectedDot().getCoordinate().getX() / gameState.getXScale(); //selected x
        double ySelected = gameState.getOrigin().getY() + (double) gameState.getSelectedDot().getCoordinate().getY()/ gameState.getYScale(); //selected y
        double xTarget = gameState.getTargetDot().getCoordinate().getX(); //The given x
        double yTarget = gameState.getTargetDot().getCoordinate().getY(); //the given y
        double xSymmetryPoint = gameState.getSymmetryPoint().getX(); //x for sym
        double ySymmetryPoint = gameState.getSymmetryPoint().getY(); //y for sym
        double correctY, correctX;

        //Validates the answer
        if (xSymmetryPoint <= xTarget) {
            double x = xTarget - xSymmetryPoint;
            correctX =  xSymmetryPoint - x;
            if (correctX == xSelected) {
                validatedAnswer.setIsXCorrect(true);
            } else {
                validatedAnswer.setIsXCorrect(false);
            }
            double y = ySymmetryPoint - yTarget;
            correctY = y + ySymmetryPoint;

            if (correctY == ySelected) {
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
            correctY = y + ySymmetryPoint;

            if (correctY == ySelected) {
                validatedAnswer.setIsYCorrect(true);
            } else {
                validatedAnswer.setIsYCorrect(false);
            }
        }
        if (correctX == xSelected && correctY == ySelected){
            validatedAnswer.setIsAnswerCorrect(true);
            gameState.setAnsweredCorrectly(true);
        }

        singleton.setXCoordinate(-1);
        singleton.setYCoordinate(-1);

        if (gameState.getSelectedDot().getCoordinate().getX() % gameState.getXScale() == 0 && gameState.getSelectedDot().getCoordinate().getY() % gameState.getYScale() == 0){
            if(!validatedAnswer.isAnswerCorrect()) {
                if ((xSelected <= 10 && xSelected >= 0) && (ySelected <= 10 && ySelected >= 0)) {
                    gameState.setCoordinateCorrectAnswer(new Coordinate((int) correctX, (int) correctY));
                    singleton.setXCoordinate((int) xSelected);
                    singleton.setYCoordinate((int) ySelected);
                }
            }
        }

        if (validatedAnswer.isAnswerCorrect() || gameState.getAttempt() >=2){
            validatedAnswer.setCorrectAnswer(new Coordinate((int) correctX, (int) correctY));
            singleton.setXCoordinate((int) correctX);
            singleton.setYCoordinate((int) correctY);
        }


        return validatedAnswer;
    }
}
