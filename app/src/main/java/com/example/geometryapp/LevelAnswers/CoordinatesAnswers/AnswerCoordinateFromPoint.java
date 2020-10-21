package com.example.geometryapp.LevelAnswers.CoordinatesAnswers;

import android.util.Pair;

import com.example.geometryapp.Coordinate;
import com.example.geometryapp.GameState;
import com.example.geometryapp.Interface.LevelAnswer;
import com.example.geometryapp.Singleton;
import com.example.geometryapp.ValidatedAnswer;

/**
 * This class calculate the answer for the coordinate from point category and validates it
 */
public class AnswerCoordinateFromPoint implements LevelAnswer {

    //Validates if answer was correct
    @Override
    public ValidatedAnswer isAnswerCorrect(GameState gameState, int levelIndex) {
        if (gameState.getTypedCoordinateAnswer() == null) {
            return new ValidatedAnswer(false,false,false);
        }
        Pair<Integer, Integer> coordinates;
        try {
            coordinates = new Pair<>(Integer.parseInt(gameState.getTypedCoordinateAnswer().first)
                    , Integer.parseInt(gameState.getTypedCoordinateAnswer().second));
        }catch (IllegalArgumentException e){
            return new ValidatedAnswer(false,false,false);
        }
        int xTyped = coordinates.first;
        int yTyped = coordinates.second;
        int xTarget = gameState.getTargetDot().getCoordinate().getX();
        int yTarget = gameState.getTargetDot().getCoordinate().getY();
        int xOrigin = gameState.getOrigin().getX();
        int yOrigin = gameState.getOrigin().getY();
        int xScale = gameState.getXScale();
        int yScale = gameState.getYScale();
        ValidatedAnswer validatedAnswer = new ValidatedAnswer(false,false,false);
        if ((xTarget - xOrigin) * xScale == xTyped && (yTarget - yOrigin) * yScale == yTyped) {
            validatedAnswer.setIsAnswerCorrect(true);
            gameState.setAnsweredCorrectly(true);
        }
        if ((xTarget - xOrigin) * xScale == xTyped) {
            validatedAnswer.setIsXCorrect(true);
        }
        if ((yTarget - yOrigin) * yScale == yTyped) {
            validatedAnswer.setIsYCorrect(true);
        }

        //Sued to set the text of the two answer boxes and correct answer dot
        Singleton singleton = Singleton.getInstance();
        if (!validatedAnswer.isAnswerCorrect() || gameState.getAttempt() >= 2){
            int xWrong = (xTyped/xScale) + xOrigin;
            int yWrong = (yTyped/yScale) + yOrigin;
            if (gameState.getAttempt()<3 && (xWrong <=10 && xWrong >= 0) && (yWrong <=10 && yWrong >= 0)) {
                gameState.setCoordinateCorrectAnswer(new Coordinate(xTarget,yTarget));
                singleton.setXCoordinate(xWrong);
                singleton.setYCoordinate(yWrong);
            }
        }
        //Used to set the text of the answer box and correct answer dot
        if(validatedAnswer.isAnswerCorrect() || gameState.getAttempt() == 2){
            gameState.setCoordinateCorrectAnswer(new Coordinate(xTarget,yTarget));
            singleton.setXCoordinate(xTarget);
            singleton.setYCoordinate(yTarget);
        }

        singleton.setL2X((xTarget-xOrigin)*xScale);
        singleton.setL2Y((yTarget-yOrigin)*yScale);

        return validatedAnswer;
    }
}

