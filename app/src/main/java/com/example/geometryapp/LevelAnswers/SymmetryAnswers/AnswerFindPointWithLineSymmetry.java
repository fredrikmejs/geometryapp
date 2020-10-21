package com.example.geometryapp.LevelAnswers.SymmetryAnswers;

import com.example.geometryapp.Coordinate;
import com.example.geometryapp.GameState;
import com.example.geometryapp.Interface.LevelAnswer;
import com.example.geometryapp.Singleton;
import com.example.geometryapp.ValidatedAnswer;

/**
 * This class calculate and validates the answer of find point with line symmetry category
 */
public class AnswerFindPointWithLineSymmetry implements LevelAnswer {

    //Validates if answer was correct
    @Override
    public ValidatedAnswer isAnswerCorrect(GameState gameState, int levelIndex) {

        Singleton singleton = Singleton.getInstance();
        ValidatedAnswer validatedAnswer = new ValidatedAnswer(false, false, false);
        int randomNum = singleton.getRandomNum();
        int level = gameState.getLevel();
        double xSelected = gameState.getOrigin().getX() + (double) gameState.getSelectedDot().getCoordinate().getX() / gameState.getXScale();
        double ySelected = gameState.getOrigin().getY() + (double) gameState.getSelectedDot().getCoordinate().getY() / gameState.getYScale();
        double xTarget = gameState.getTargetDot().getCoordinate().getX();
        double yTarget = gameState.getTargetDot().getCoordinate().getY();
        double y, x, yAnswer, xAnswer;

        //Checks if level is 1 and finds the answer of that type for symmetry
        if (level == 1) {
            if (randomNum == 0) {
                if (ySelected == yTarget) {
                    validatedAnswer.setIsYCorrect(true);
                } else {
                    validatedAnswer.setIsYCorrect(false);
                }
                yAnswer = yTarget;

                //Calculates and checks the x coordinate
                if (xTarget > 5) {
                    x = xTarget - 5;
                    x = 5 - x;
                } else {
                    x = 5 - xTarget;
                    x = 5 + x;
                }
                if (x == xSelected) {
                    validatedAnswer.setIsXCorrect(true);
                } else {
                    validatedAnswer.setIsXCorrect(false);
                }
                xAnswer = x;
                if (x == xSelected && ySelected == yTarget) {
                    validatedAnswer.setIsAnswerCorrect(true);
                    gameState.setAnsweredCorrectly(true);
                } else {
                    validatedAnswer.setIsAnswerCorrect(false);
                    gameState.setAnsweredCorrectly(false);
                }
            } else {
                if (xSelected == xTarget) {
                    validatedAnswer.setIsXCorrect(true);
                } else {
                    validatedAnswer.setIsXCorrect(false);
                }
                xAnswer = xTarget;
                //Calculate and checks the y coordinate
                if (yTarget > 5) {
                    y = yTarget - 5;
                    y = 5 - y;
                } else {
                    y = 5 - yTarget;
                    y = 5 + y;
                }
                if (y == ySelected) {
                    validatedAnswer.setIsYCorrect(true);
                } else {
                    validatedAnswer.setIsYCorrect(false);
                }
                yAnswer = y;
                if (xSelected == xTarget && y == ySelected) {
                    validatedAnswer.setIsAnswerCorrect(true);
                    gameState.setAnsweredCorrectly(true);
                } else {
                    validatedAnswer.setIsAnswerCorrect(false);
                    gameState.setAnsweredCorrectly(false);
                }
            }

        } else {
            //Calculate answer for 45 degree symmetry
            if (randomNum == 0) {
                if (xSelected == yTarget) {
                    validatedAnswer.setIsXCorrect(true);
                } else {
                    validatedAnswer.setIsXCorrect(false);
                }
                xAnswer = yTarget;
                if (ySelected == xTarget) {
                    validatedAnswer.setIsYCorrect(true);
                } else {
                    validatedAnswer.setIsYCorrect(false);
                }
                yAnswer = xTarget;
                if (xSelected == yTarget && xTarget == ySelected) {
                    validatedAnswer.setIsAnswerCorrect(true);
                    gameState.setAnsweredCorrectly(true);
                } else {
                    validatedAnswer.setIsAnswerCorrect(false);
                    gameState.setAnsweredCorrectly(false);
                }
            } else {
                boolean xBool = false, yBool = false;
                if (ySelected == (10 - xTarget)) {
                    yBool = true;
                    validatedAnswer.setIsYCorrect(true);

                } else {
                    validatedAnswer.setIsYCorrect(false);
                }
                yAnswer = 10 - xTarget;
                if (xSelected == (10 - yTarget)) {
                    xBool = true;
                    validatedAnswer.setIsXCorrect(true);
                } else {
                    validatedAnswer.setIsXCorrect(false);
                }
                xAnswer = 10 - yTarget;
                if (xBool && yBool) {
                    validatedAnswer.setIsAnswerCorrect(true);
                    gameState.setAnsweredCorrectly(true);
                } else {
                    validatedAnswer.setIsAnswerCorrect(false);
                    gameState.setAnsweredCorrectly(false);
                }
            }
        }

        singleton.setXCoordinate(-1);
        singleton.setYCoordinate(-1);

        //Checks if the answer is precise
        if (gameState.getSelectedDot().getCoordinate().getX() % gameState.getXScale() == 0 && gameState.getSelectedDot().getCoordinate().getY() % gameState.getYScale() == 0) {
            if ((xSelected <= 10 && xSelected >= 0) && (ySelected <= 10 && ySelected >= 0)) {
                if (!validatedAnswer.isAnswerCorrect()) {
                    gameState.setCoordinateCorrectAnswer(new Coordinate((int) xAnswer, (int) yAnswer));
                    singleton.setXCoordinate((int) xSelected);
                    singleton.setYCoordinate((int) ySelected);
                }
            }
        }

        //Sets the correct answer dot
        if (validatedAnswer.isAnswerCorrect() || gameState.getAttempt() >= 2) {
            validatedAnswer.setCorrectAnswer(new Coordinate((int) xAnswer, (int) yAnswer));
            singleton.setXCoordinate(((int) xAnswer));
            singleton.setYCoordinate((int) yAnswer);
        }

        return validatedAnswer;


    }
}
