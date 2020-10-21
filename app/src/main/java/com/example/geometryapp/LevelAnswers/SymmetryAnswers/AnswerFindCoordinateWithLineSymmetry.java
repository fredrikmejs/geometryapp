package com.example.geometryapp.LevelAnswers.SymmetryAnswers;

import android.util.Pair;

import com.example.geometryapp.Coordinate;
import com.example.geometryapp.DrawObjects.SelectedDot;
import com.example.geometryapp.DrawObjects.TargetDot;
import com.example.geometryapp.GameState;
import com.example.geometryapp.Interface.LevelAnswer;
import com.example.geometryapp.Singleton;
import com.example.geometryapp.ValidatedAnswer;

/**
 * This class calculate and validates the answer of find coordinate with line symmetry
 */
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
        double xSelected1 = gameState.getOrigin().getX();
        double xSelected2 = (double) coordinates.first / gameState.getXScale();
        double xSelected = xSelected1 + xSelected2;
        double ySelected = gameState.getOrigin().getY() + (double)coordinates.second / gameState.getYScale();
        double xTarget = gameState.getTargetDot().getCoordinate().getX();

        double yTarget = gameState.getTargetDot().getCoordinate().getY();

        double y,x;
        double yAnswer, xAnswer;

        //Checks if the levels ain't 3,4,7 or 8
         if (!(level == 3 || level == 4 || level == 7 || level == 8)) {
             //Checks what type of symmetry it is
             if (randomNum == 0){
                 if (ySelected == yTarget){
                     validatedAnswer.setIsYCorrect(true);
                 } else{
                     validatedAnswer.setIsYCorrect(false);
                 }
                 yAnswer = yTarget;

                 //Checks if the x coordinate is correct
                 if (xTarget > 5){
                     x = xTarget - 5;
                     x = 5 - x;
                 } else{
                     x = 5 - xTarget;
                     x = 5 + x;
                 }
                 if (x == xSelected){
                     validatedAnswer.setIsXCorrect(true);
                 } else{
                     validatedAnswer.setIsXCorrect(false);
                 }
                 xAnswer = x;
                 if (x == xSelected && ySelected == yTarget){
                     validatedAnswer.setIsAnswerCorrect(true);
                     gameState.setAnsweredCorrectly(true);
                 } else {
                     validatedAnswer.setIsAnswerCorrect(false);
                     gameState.setAnsweredCorrectly(false);
                 }
             } else {
                if (xSelected == xTarget){
                    validatedAnswer.setIsXCorrect(true);
                } else{
                    validatedAnswer.setIsXCorrect(false);
                }
                xAnswer = xTarget;

                //Checks if the y coordinate is correct
                if (yTarget > 5){
                    y = yTarget -5;
                    y = 5 - y;

                }else {
                    y = 5 - yTarget;
                    y = 5 + y;
                }
                 if (y == ySelected){
                     validatedAnswer.setIsYCorrect(true);
                 } else {
                     validatedAnswer.setIsYCorrect(false);
                 }
                 yAnswer = y;
                 //Checks if both coordinates is correct
                 if (xSelected == xTarget && y == ySelected){
                    validatedAnswer.setIsAnswerCorrect(true);
                    gameState.setAnsweredCorrectly(true);
                } else {
                    validatedAnswer.setIsAnswerCorrect(false);
                    gameState.setAnsweredCorrectly(false);
                }
             }

        } //Checks the other form for symmetry
        else {
             if (randomNum == 0) {
                 if (xSelected == yTarget) {
                     validatedAnswer.setIsXCorrect(true);
                 } else {
                     validatedAnswer.setIsXCorrect(false);
                 }
                 //Because the answer is opposite if the correct answers coordinates x = y and y = x
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
                 //local variables to check if the answer is correct
                 //10-xTarget because of placement under this form for symmetry
                 boolean xBool = false, yBool = false;
                 if (ySelected == (10-xTarget)){
                     yBool = true;
                     validatedAnswer.setIsYCorrect(true);

                 } else {
                     validatedAnswer.setIsYCorrect(false);
                 }
                 yAnswer = 10-xTarget;
                 if (xSelected == (10-yTarget)){
                     xBool = true;
                     validatedAnswer.setIsXCorrect(true);
                 } else {
                     validatedAnswer.setIsXCorrect(false);
                 }
                 xAnswer = 10-yTarget;
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

         //Checks if answer is correct and not "close to"
         if (coordinates.first % gameState.getXScale() == 0 && coordinates.second % gameState.getYScale() == 0){
             if ((xSelected <= 10 && xSelected >= 0) && (ySelected <= 10 && ySelected >=0)) {
                 if (!validatedAnswer.isAnswerCorrect()) {
                     gameState.setCoordinateCorrectAnswer(new Coordinate((int) xAnswer, (int) yAnswer));
                     singleton.setXCoordinate((int) xSelected);
                     singleton.setYCoordinate((int) ySelected);
                 }
             }
         }
         //Sets coordinate for correctanswer dot in canvas
         if (validatedAnswer.isAnswerCorrect() || gameState.getAttempt() >= 2){
                   validatedAnswer.setCorrectAnswer(new Coordinate((int)xAnswer, (int)yAnswer));
                   singleton.setXCoordinate(((int) xAnswer));
                   singleton.setYCoordinate((int) yAnswer);
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
