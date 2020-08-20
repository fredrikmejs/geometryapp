package com.example.geometryapp.LevelAnswers.SymmetryAnswers;

import android.util.Pair;

import com.example.geometryapp.Coordinate;
import com.example.geometryapp.GameState;
import com.example.geometryapp.Interface.LevelAnswer;
import com.example.geometryapp.Singleton;
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
        Singleton singleton = Singleton.getInstance();
        int randomNum = singleton.getRandomNum();
        int level = gameState.getLevel();
        int xSelected = gameState.getOrigin().getX() + coordinates.first / gameState.getXScale();
        int ySelected = gameState.getOrigin().getY() + coordinates.second / gameState.getYScale();

        int xTarget = gameState.getTargetDot().getCoordinate().getX();



        //int xTarget = (a - b)*gameState.getXScale();// (gameState.getTargetDot().getCoordinate().getX() - gameState.getOrigin().getX()) * gameState.getXScale();

        int yTarget = gameState.getTargetDot().getCoordinate().getY();


       // int yTarget = (gameState.getTargetDot().getCoordinate().getY() - gameState.getOrigin().getY()) * gameState.getYScale();

         if (level != 7) {
             if (randomNum == 0){

                 if (ySelected == yTarget){
                     validatedAnswer.setIsYCorrect(true);
                 } else{
                     validatedAnswer.setIsYCorrect(false);
                 }
                 int x;
                 if (xTarget > 5){
                     x = xTarget - 5;
                     x = 5 - x;
                     if (x == xSelected){
                         validatedAnswer.setIsXCorrect(true);
                     } else{
                         validatedAnswer.setIsXCorrect(false);
                     }
                 } else{
                     x = 5 - xTarget;
                     x = 5 + x;
                     if (x == xSelected){
                         validatedAnswer.setIsXCorrect(true);
                     } else{
                         validatedAnswer.setIsXCorrect(false);
                     }

                 }
                 if (x == xSelected && ySelected == yTarget){
                     validatedAnswer.setIsAnswerCorrect(true);
                     gameState.setAnsweredCorrectly(true);
                 } else {
                     validatedAnswer.setIsAnswerCorrect(false);
                     gameState.setAnsweredCorrectly(false);
                 }
             } else {
                 int y;
                if (xSelected == xTarget){
                    validatedAnswer.setIsXCorrect(true);
                } else{
                    validatedAnswer.setIsXCorrect(false);
                }
                if (yTarget > 5){
                    y = yTarget -5;
                    y = 5 - y;

                    if (y == ySelected){
                        validatedAnswer.setIsYCorrect(true);
                    } else {
                        validatedAnswer.setIsYCorrect(false);
                    }
                }else {
                    y = 5 - yTarget;
                    y = 5 + y;
                    if (y == ySelected){
                        validatedAnswer.setIsYCorrect(true);
                    } else {
                        validatedAnswer.setIsYCorrect(false);
                    }
                }
                if (xSelected == xTarget && y == ySelected){
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
                 if (ySelected == xTarget) {
                     validatedAnswer.setIsYCorrect(true);
                 } else {
                     validatedAnswer.setIsYCorrect(false);
                 }

                 if (xSelected == yTarget && xTarget == ySelected) {
                     validatedAnswer.setIsAnswerCorrect(true);
                     gameState.setAnsweredCorrectly(true);
                 } else {
                     validatedAnswer.setIsAnswerCorrect(false);
                     gameState.setAnsweredCorrectly(false);
                 }
             } else {
                 boolean xbool = false, ybool = false;

                 if (ySelected == (10-xTarget)){
                     ybool = true;
                     validatedAnswer.setIsYCorrect(true);
                 } else {
                     validatedAnswer.setIsYCorrect(false);
                 }
                 if (xSelected == (10-yTarget)){
                     xbool = true;
                     validatedAnswer.setIsXCorrect(true);
                 } else {
                     validatedAnswer.setIsXCorrect(false);
                 }
                 if (xbool && ybool) {
                     validatedAnswer.setIsAnswerCorrect(true);
                     gameState.setAnsweredCorrectly(true);
                 } else {
                     validatedAnswer.setIsAnswerCorrect(false);
                     gameState.setAnsweredCorrectly(false);
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
