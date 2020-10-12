package com.example.geometryapp.LevelAnswers.SymmetryAnswers;

import com.example.geometryapp.Coordinate;
import com.example.geometryapp.GameState;
import com.example.geometryapp.Interface.LevelAnswer;
import com.example.geometryapp.Singleton;
import com.example.geometryapp.ValidatedAnswer;

public class AnswerFindPointWithLineSymmetry implements LevelAnswer {

    //Validates if answer was correct
    @Override
    public ValidatedAnswer isAnswerCorrect(GameState gameState, int levelIndex) {

        /*
        ValidatedAnswer validatedAnswer = new ValidatedAnswer(false, false, false);
        int xSelected = gameState.getSelectedDot().getCoordinate().getX();
        int ySelected = gameState.getSelectedDot().getCoordinate().getY();
        int xTarget = gameState.getTargetDot().getCoordinate().getX();
        int yTarget = gameState.getTargetDot().getCoordinate().getY();
        Coordinate symmetryLineStart = gameState.getSymmetryLine().getStartCoordinate();
        Coordinate symmetryLineEnd = gameState.getSymmetryLine().getEndCoordinate();
        //Creates perpendicular line

         */


        Singleton singleton = Singleton.getInstance();
        ValidatedAnswer validatedAnswer = new ValidatedAnswer(false, false, false);
        int randomNum = singleton.getRandomNum();
        int level = gameState.getLevel();
        double xSelected = gameState.getOrigin().getX() + (double) gameState.getSelectedDot().getCoordinate().getX() / gameState.getXScale();
        double ySelected = gameState.getOrigin().getY() + (double) gameState.getSelectedDot().getCoordinate().getY() / gameState.getYScale();
        double xTarget = gameState.getTargetDot().getCoordinate().getX();

        double yTarget = gameState.getTargetDot().getCoordinate().getY();

        double y = 0, x = 0;
        double yAnswer = 0, xAnswer = 0;


        if (level == 1) {
            if (randomNum == 0) {
                if (ySelected == yTarget) {
                    validatedAnswer.setIsYCorrect(true);
                } else {
                    validatedAnswer.setIsYCorrect(false);
                }
                yAnswer = yTarget;
                if (xTarget > 5) {
                    x = xTarget - 5;
                    x = 5 - x;
                    if (x == xSelected) {
                        validatedAnswer.setIsXCorrect(true);
                    } else {
                        validatedAnswer.setIsXCorrect(false);
                    }
                    xAnswer = x;
                } else {
                    x = 5 - xTarget;
                    x = 5 + x;
                    if (x == xSelected) {
                        validatedAnswer.setIsXCorrect(true);
                    } else {
                        validatedAnswer.setIsXCorrect(false);
                    }
                    xAnswer = x;
                }
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
                if (yTarget > 5) {
                    y = yTarget - 5;
                    y = 5 - y;

                    if (y == ySelected) {
                        validatedAnswer.setIsYCorrect(true);
                    } else {
                        validatedAnswer.setIsYCorrect(false);
                    }
                    yAnswer = y;
                } else {
                    y = 5 - yTarget;
                    y = 5 + y;
                    if (y == ySelected) {
                        validatedAnswer.setIsYCorrect(true);
                    } else {
                        validatedAnswer.setIsYCorrect(false);
                    }
                    yAnswer = y;
                }
                if (xSelected == xTarget && y == ySelected) {
                    validatedAnswer.setIsAnswerCorrect(true);
                    gameState.setAnsweredCorrectly(true);
                } else {
                    validatedAnswer.setIsAnswerCorrect(false);
                    gameState.setAnsweredCorrectly(false);
                }
            }

        } else {
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
                boolean xbool = false, ybool = false;
                if (ySelected == (10 - xTarget)) {
                    ybool = true;
                    validatedAnswer.setIsYCorrect(true);

                } else {
                    validatedAnswer.setIsYCorrect(false);
                }
                yAnswer = 10 - xTarget;
                if (xSelected == (10 - yTarget)) {
                    xbool = true;
                    validatedAnswer.setIsXCorrect(true);
                } else {
                    validatedAnswer.setIsXCorrect(false);
                }
                xAnswer = 10 - yTarget;
                if (xbool && ybool) {
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

        if (gameState.getSelectedDot().getCoordinate().getX() % gameState.getXScale() == 0 && gameState.getSelectedDot().getCoordinate().getY() % gameState.getYScale() == 0) {
            if ((xSelected <= 10 && xSelected >= 0) && (ySelected <= 10 && ySelected >= 0)) {
                if (!validatedAnswer.isAnswerCorrect()) {
                    gameState.setCoordinateCorrectAnswer(new Coordinate((int) xAnswer, (int) yAnswer));
                    singleton.setXCoordinate((int) xSelected);
                    singleton.setYCoordinate((int) ySelected);
                }
            }
        }


        if (validatedAnswer.isAnswerCorrect() || gameState.getAttempt() >= 2) {
            validatedAnswer.setCorrectAnswer(new Coordinate((int) xAnswer, (int) yAnswer));
            singleton.setXCoordinate(((int) xAnswer));
            singleton.setYCoordinate((int) yAnswer);
        }

        return validatedAnswer;


    }

/*



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
            gameState.setAnsweredCorrectly(true);
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
        double distanceTargetToSymmetryLineStart = Math.sqrt((xxTarget - symmetryLineStart.getX()) * (xTarget - symmetryLineStart.getX())
                + (yTarget - symmetryLineStart.getY()) * (yTarget - symmetryLineStart.getY()));
        double distanceTargetToSymmetryLineEnd = Math.sqrt((xTarget - symmetryLineEnd.getX()) * (xTarget - symmetryLineEnd.getX())
                + (yTarget - symmetryLineEnd.getY()) * (yTarget - symmetryLineEnd.getY()));
        if (distanceSelectedToSymmetryLineStart == distanceTargetToSymmetryLineStart && distanceSelectedToSymmetryLineEnd == distanceTargetToSymmetryLineEnd
                && (xSelected != xTarget || ySelected != yTarget)) {
            return true;
        }
        return false;
    }

 */

}
