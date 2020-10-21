package com.example.geometryapp.LevelAnswers.CoordinatesAnswers;

import com.example.geometryapp.Coordinate;
import com.example.geometryapp.GameState;
import com.example.geometryapp.Interface.LevelAnswer;
import com.example.geometryapp.Singleton;
import com.example.geometryapp.ValidatedAnswer;

public class AnswerPointFromCoordinate implements LevelAnswer {

    //Validates if answer was correct
    @Override
    public ValidatedAnswer isAnswerCorrect(GameState gameState, int levelIndex){
        int xSelected = gameState.getSelectedDot().getCoordinate().getX();
        int ySelected = gameState.getSelectedDot().getCoordinate().getY();
        int xTarget = gameState.getTargetPoint().getX();
        int yTarget = gameState.getTargetPoint().getY();
        int xOrigin = gameState.getOrigin().getX();
        int yOrigin = gameState.getOrigin().getY();
        int xScale = gameState.getXScale();
        int yScale= gameState.getYScale();
        ValidatedAnswer validatedAnswer = new ValidatedAnswer(false,false,false);
        if((xSelected-xOrigin)*xScale==xTarget&&(ySelected-yOrigin)*yScale==yTarget){
            validatedAnswer.setIsAnswerCorrect(true);
        }
        if((xSelected-xOrigin)*xScale==xTarget){
            validatedAnswer.setIsXCorrect(true);
        }
        if((ySelected-yOrigin)*yScale==yTarget){
            validatedAnswer.setIsYCorrect(true);
        }

        if (validatedAnswer.isAnswerCorrect() || gameState.getAttempt() >= 2){
            Singleton singleton = Singleton.getInstance();
            validatedAnswer.setCorrectAnswer(new Coordinate(xTarget, yTarget));
            singleton.setXCoordinate((xTarget/xScale)+xOrigin);
            singleton.setYCoordinate((yTarget/yScale)+yOrigin);
        }
            return validatedAnswer;
    }
}
