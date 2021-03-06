package com.example.geometryapp.LevelAnswers.PerimeterAnswers;

import android.util.Log;

import com.example.geometryapp.GameState;
import com.example.geometryapp.Interface.LevelAnswer;
import com.example.geometryapp.ValidatedAnswer;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Class to calculate and validate the answer for find perimeter of a figure category
 */
public class AnswerFindThePerimeterOfAFigure implements LevelAnswer {

    //Validates if answer was correct
    @Override
    public ValidatedAnswer isAnswerCorrect(GameState gameState, int levelIndex) {
        ValidatedAnswer validatedAnswer = new ValidatedAnswer(false, false, false);
        double answer;
        try{
            //Checks for the answer contains PI and times it with the value of PI
            String answerText = gameState.getTypedValueAnswer();
            int count = answerText.length() - answerText.replace("\uD835\uDF0B", "").length();
            answerText = answerText.replace("\uD835\uDF0B","");
            answer = Double.parseDouble(answerText);
            for(int i = 0; i < count; i += 2){
                answer *= 3.14159;
            }
        } catch (NumberFormatException e){
            return validatedAnswer;
        }
        //validates the answer for rectangle
        if (gameState.getRectangle() != null) {
            if (roundUp(gameState.getRectangle().calculatePerimeter(gameState.getXScale(),gameState.getYScale()), 1) == roundUp(answer, 1)
            || roundDown(gameState.getRectangle().calculatePerimeter(gameState.getXScale(),gameState.getYScale()), 1) == roundUp(answer, 1)) {
                gameState.setAnsweredCorrectly(true);
                return new ValidatedAnswer(true, true, true);
            }
        }  //validates the answer for circle
        else if (gameState.getCircle() != null) {
            if (roundUp(gameState.getCircle().calculatePerimeter(gameState.getXScale()), 1) == roundUp(answer, 1)
            || roundDown(gameState.getCircle().calculatePerimeter(gameState.getXScale()), 1) == roundUp(answer, 1)) {
                gameState.setAnsweredCorrectly(true);
                return new ValidatedAnswer(true, true, true);
            }
        }  //validates the answer for triangle
        else if (gameState.getTriangle() != null) {
            if (roundUp(gameState.getTriangle().calculatePerimeter(gameState.getXScale(),gameState.getYScale()), 1) == roundUp(answer, 1)
            || roundDown(gameState.getTriangle().calculatePerimeter(gameState.getXScale(),gameState.getYScale()), 1) == roundDown(answer, 1)) {
                gameState.setAnsweredCorrectly(true);
                return new ValidatedAnswer(true, true, true);
            }
        }
        return validatedAnswer;
    }
    /**
     * Round the value up to check if the given answer has been rounded
     * @param value is the area given answer from the user
     * @param places number of decimals
     * @return the rounded value
     */
    public static double roundUp(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.UP);
        return bd.doubleValue();
    }
    /**
     * Round the value down to check if the given answer has been rounded
     * @param value is the area given answer from the user
     * @param places number of decimals
     * @return the rounded value
     */
    public static double roundDown(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.DOWN);
        return bd.doubleValue();
    }
}
