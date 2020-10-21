package com.example.geometryapp.LevelAnswers.AreaAnswers;

import android.util.Log;

import com.example.geometryapp.GameState;
import com.example.geometryapp.Interface.LevelAnswer;
import com.example.geometryapp.ValidatedAnswer;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * This class checks the answers for the area of a figure category
 */
public class AnswerCompleteFigureFromArea implements LevelAnswer {

    //Validates if answer was correct
    @Override
    public ValidatedAnswer isAnswerCorrect(GameState gameState, int levelIndex) {
        ValidatedAnswer validatedAnswer = new ValidatedAnswer(false, false, false);
        double answer;
        try {
            //Checks for the value of PI if the answer contains it, remove it and times the reminding value with 3,14159
            String answerText = gameState.getTypedValueAnswer();
            int count = answerText.length() - answerText.replace("\uD835\uDF0B", "").length();
            answerText = answerText.replace("\uD835\uDF0B", "");
            if (answerText.length() == 0) {
                answerText = "1";
            }
            answer = Double.parseDouble(answerText);
            for (int i = 0; i < count; i += 2) {
                answer *= 3.14159
                ;
            }
        } catch (NumberFormatException e) {
            return validatedAnswer;
        }
        //Calculate the area of a rectangle
        if (gameState.getRectangle() != null) {
            double area = gameState.getRectangle().calculateArea(gameState.getXScale(), gameState.getYScale());
                if (roundUp(area, 1) == roundUp(answer, 1) ||
                    roundDown(area, 1) == roundDown(answer, 1)) {
                gameState.setAnsweredCorrectly(true);
                return new ValidatedAnswer(true, true, true);
            }
        } //Calculate the area of a circle
        else if (gameState.getCircle() != null) {
            if (roundUp(gameState.getCircle().calculateArea(gameState.getXScale()), 1) == roundUp(answer, 1)
                    || roundDown(gameState.getCircle().calculateArea(gameState.getXScale()), 1) == roundDown(answer, 1)) {
                gameState.setAnsweredCorrectly(true);
                return new ValidatedAnswer(true, true, true);
            }
        } //Calculate the area of a triangle
        else if (gameState.getTriangle() != null) {
            if (roundUp(gameState.getTriangle().calculateArea(gameState.getXScale(), gameState.getYScale(),gameState.getLevel(),gameState.getCategory()), 1) == roundUp(answer, 1)
                    || roundDown(gameState.getTriangle().calculateArea(gameState.getXScale(), gameState.getYScale(),gameState.getLevel(),gameState.getCategory()), 1) == roundDown(answer, 1)) {
                gameState.setAnsweredCorrectly(true);
                return new ValidatedAnswer(true, true, true);
            }
        } //Calculate the area of a shapefourcorners
        else if (gameState.getShapeFourCorners() != null) {
            if (roundUp(gameState.getShapeFourCorners().calculateArea(gameState.getXScale(), gameState.getYScale(),gameState.getLevel(),gameState.getCategory()), 1) == roundUp(answer, 1)
                    || roundDown(gameState.getShapeFourCorners().calculateArea(gameState.getXScale(), gameState.getYScale(),gameState.getLevel(),gameState.getCategory()), 1) == roundDown(answer, 1)) {
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
