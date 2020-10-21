package com.example.geometryapp.LevelAnswers.AreaAnswers;

import android.util.Log;

import com.example.geometryapp.GameState;
import com.example.geometryapp.Interface.LevelAnswer;
import com.example.geometryapp.ValidatedAnswer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

/**
 * This class calculates the are of area from a figure category
 */
public class AnswerFindAreaFromFigure implements LevelAnswer {


    double answer = 0;
    double oddScaleAnswer = 0;
    String answerText ="";
    //Validates if answer was correct
    @Override
    public ValidatedAnswer isAnswerCorrect(GameState gameState, int levelIndex) {
        ValidatedAnswer validatedAnswer = new ValidatedAnswer(false, false, false);

        int count = 0;
        try{
            //Checks if it contains PI
            answerText = gameState.getTypedValueAnswer();
            if (answerText.contains("\uD835\uDF0B")) {

                count = answerText.length() - answerText.replace("\uD835\uDF0B", "").length();
                answerText = answerText.replace("\uD835\uDF0B", "");
                if (answerText.equals("")){
                    answerText = "1";
                }
            }
            //Checks if answers contains a "×"
            if (answerText.contains("×")){
                containsX();
            }
            //Checks if the answer contains "/"
            if (answerText.contains("/")){
                containsDivision();
            }

            if (answer == 0){
                answer = Double.parseDouble(answerText);
            }
            //Times the number PI value used
            if (count > 0) {
                for (int i = 0; i < count; i += 2) {
                    answer *= 3.14159;
                }
            }
        } catch (NumberFormatException e){
            return validatedAnswer;
        }
        //Calculates the answer for the rectangle
        if (gameState.getRectangle() != null) {
            //Special cases for index 2 and 4
            if (levelIndex == 2 || levelIndex == 4){
                double correctAnswer = gameState.getRectangle().calculateArea(gameState.getXScale(),gameState.getYScale());
                if (oddScaleAnswer == correctAnswer ||answer == correctAnswer) {
                    gameState.setAnsweredCorrectly(true);
                    return new ValidatedAnswer(true, true, true);
                }
            } else if (roundUp(gameState.getRectangle().calculateArea(gameState.getXScale(),gameState.getYScale()), 1) == roundUp(answer, 1)
                    || roundDown(gameState.getRectangle().calculateArea(gameState.getXScale(),gameState.getYScale()), 1) == roundDown(answer, 1)) {
                    gameState.setAnsweredCorrectly(true);
                    return new ValidatedAnswer(true, true, true);
                }
            }
        //Calculates the answer for the Circle
        if (gameState.getCircle() != null) {
            if (roundUp(gameState.getCircle().calculateArea(gameState.getXScale()), 1) == roundUp(answer, 1)
            || roundDown(gameState.getCircle().calculateArea(gameState.getXScale()), 1) == roundDown(answer, 1)) {
                gameState.setAnsweredCorrectly(true);
                return new ValidatedAnswer(true, true, true);
            }
        }
        //Calculates the answer for the Triangle
        if (gameState.getTriangle() != null) {
            if (roundUp(gameState.getTriangle().calculateArea(gameState.getXScale(),gameState.getYScale(),gameState.getLevel(),gameState.getCategory()), 1) == roundUp(answer, 1)
            || roundDown(gameState.getTriangle().calculateArea(gameState.getXScale(),gameState.getYScale(), gameState.getLevel(),gameState.getCategory()), 1) == roundDown(answer, 1)) {
                gameState.setAnsweredCorrectly(true);
                return new ValidatedAnswer(true, true, true);
            }
        }
        //Calculates the answer for the fourcorner shapes
        if (gameState.getShapeFourCorners() != null) {
            if (roundUp(gameState.getShapeFourCorners().calculateArea(gameState.getXScale(),gameState.getYScale(),gameState.getLevel(),gameState.getCategory()), 1) == roundUp(answer, 1)
            || roundDown(gameState.getShapeFourCorners().calculateArea(gameState.getXScale(),gameState.getYScale(),gameState.getLevel(),gameState.getCategory()), 1) == roundDown(answer, 1)) {
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

    /**
     * Checks if the answer contains "×" and if it does splits the answer
      */
    private void containsX(){
        String[] parts = answerText.split("×");
        double firstParameter = Double.parseDouble(parts[0]);
        double secondParamter = Double.parseDouble(parts[1]);
        answer = firstParameter*secondParamter;
    }

    /**
     * Checks if the answer contains "/" and if it does splits the answer
     */
    private void containsDivision(){
        String[] parts = answerText.split("/");
        double firstParameter = Double.parseDouble(parts[0]);
        double secondParamter = Double.parseDouble(parts[1]);
        answer = firstParameter / secondParamter;

    }
}
