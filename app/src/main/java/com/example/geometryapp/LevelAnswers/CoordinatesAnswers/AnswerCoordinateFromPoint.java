package com.example.geometryapp.LevelAnswers.CoordinatesAnswers;

import android.util.Pair;

import com.example.geometryapp.GameState;
import com.example.geometryapp.Interface.LevelAnswer;
import com.example.geometryapp.ValidatedAnswer;

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
        }
        if ((xTarget - xOrigin) * xScale == xTyped) {
            validatedAnswer.setIsXCorrect(true);
        }
        if ((yTarget - yOrigin) * yScale == yTyped) {
            validatedAnswer.setIsYCorrect(true);
        }
        return validatedAnswer;
    }
}

