package com.example.geometryapp.LevelAnswers.AreaAnswers;

import android.util.Log;

import com.example.geometryapp.GameState;
import com.example.geometryapp.Interface.LevelAnswer;
import com.example.geometryapp.ValidatedAnswer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

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

            answerText = gameState.getTypedValueAnswer();
            if (answerText.contains("\uD835\uDF0B")) {

                count = answerText.length() - answerText.replace("\uD835\uDF0B", "").length();
                answerText = answerText.replace("\uD835\uDF0B", "");
                if (answerText.equals("")){
                    answerText = "1";
                }
            }
            if (answerText.contains("×")){
                containsX();
            }
            if (answerText.contains("/")){
                containsDivision();
            }

            if (answer == 0){
                answer = Double.parseDouble(answerText);
            }
            if (count > 0) {
                for (int i = 0; i < count; i += 2) {
                    answer *= 3.14159;
                }
            }
        } catch (NumberFormatException e){
            return validatedAnswer;
        }
        if (gameState.getRectangle() != null) {
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

        if (gameState.getCircle() != null) {
            Log.i("Circle ", "isAnswerCorrect: . " + roundUp(gameState.getCircle().calculateArea(gameState.getXScale()),1));
            if (roundUp(gameState.getCircle().calculateArea(gameState.getXScale()), 1) == roundUp(answer, 1)
            || roundDown(gameState.getCircle().calculateArea(gameState.getXScale()), 1) == roundDown(answer, 1)) {
                gameState.setAnsweredCorrectly(true);
                return new ValidatedAnswer(true, true, true);
            }
        }
        if (gameState.getTriangle() != null) {

            if (roundUp(gameState.getTriangle().calculateArea(gameState.getXScale(),gameState.getYScale()), 1) == roundUp(answer, 1)
            || roundDown(gameState.getTriangle().calculateArea(gameState.getXScale(),gameState.getYScale()), 1) == roundDown(answer, 1)) {
                gameState.setAnsweredCorrectly(true);
                return new ValidatedAnswer(true, true, true);
            }
        }
        if (gameState.getShapeFourCorners() != null) {
            if (roundUp(gameState.getShapeFourCorners().calculateArea(gameState.getXScale(),gameState.getYScale()), 1) == roundUp(answer, 1)
            || roundDown(gameState.getShapeFourCorners().calculateArea(gameState.getXScale(),gameState.getYScale()), 1) == roundDown(answer, 1)) {
                gameState.setAnsweredCorrectly(true);
                return new ValidatedAnswer(true, true, true);
            }
        }
        return validatedAnswer;
    }

    public static double roundUp(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.UP);
        return bd.doubleValue();
    }

    public static double roundDown(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.DOWN);
        return bd.doubleValue();
    }

    private void containsX(){
        String[] parts = answerText.split("×");
        double firstParameter = Double.parseDouble(parts[0]);
        double secondParamter = Double.parseDouble(parts[1]);
        answer = firstParameter*secondParamter;
    }

    private void containsDivision(){
        String[] parts = answerText.split("/");
        double firstParameter = Double.parseDouble(parts[0]);
        double secondParamter = Double.parseDouble(parts[1]);
        answer = firstParameter / secondParamter;

    }
}
